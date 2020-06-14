package renderer;

import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import primitives.Color;
import scene.Scene;
import elements.*;
import primitives.*;
import primitives.Point3D;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * the class represent the Render engine to get image from scene
 */
public class Render {

    private final Scene _scene;
    private final ImageWriter _imageWriter;
    private int _NumOfSampel;

    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.01;

    private int _threads = 1;
    private final int SPARE_THREADS = 2;
    private boolean _print = false;

    /**
     * Pixel is an internal helper class whose objects are associated with a Render object that
     * they are generated in scope of. It is used for multithreading in the Renderer and for follow up
     * its progress.<br/>
     * There is a main follow up object and several secondary objects - one in each thread.
     *
     * @author Dan
     */
    private class Pixel {
        private long _maxRows = 0;
        private long _maxCols = 0;
        private long _pixels = 0;
        public volatile int row = 0;
        public volatile int col = -1;
        private long _counter = 0;
        private int _percents = 0;
        private long _nextCounter = 0;

        /**
         * The constructor for initializing the main follow up Pixel object
         *
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            _maxRows = maxRows;
            _maxCols = maxCols;
            _pixels = maxRows * maxCols;
            _nextCounter = _pixels / 100;
            if (Render.this._print) System.out.printf("\r %02d%%", _percents);
        }

        /**
         * Default constructor for secondary Pixel objects
         */
        public Pixel() {
        }

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object - this function is
         * critical section for all the threads, and main Pixel object data is the shared data of this critical
         * section.<br/>
         * The function provides next pixel number each call.
         *
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print, if it is -1 - the task is
         * finished, any other value - the progress percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col;
            ++_counter;
            if (col < _maxCols) {
                target.row = this.row;
                target.col = this.col;
                if (_counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    if (_print) System.out.println(_percents);
                    return _percents;
                }
                return 0;
            }
            ++row;
            if (row < _maxRows) {
                col = 0;
                if (_counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            return -1;
        }

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         *
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percents = nextP(target);
            if (percents > 0)
                if (Render.this._print) System.out.printf("\r %02d%%", percents);
            if (percents >= 0)
                return true;
            if (Render.this._print) System.out.printf("\r %02d%%", 100);
            return false;
        }
    }

    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object
     */
    public void renderImage() {
        final int nX = _imageWriter.getNx();
        final int nY = _imageWriter.getNy();
        final double dist = _scene.getDistance();
        final double width = _imageWriter.getWidth();
        final double height = _imageWriter.getHeight();
        final Camera camera = _scene.getCamera();

        final Pixel thePixel = new Pixel(nY, nX);

        // Generate threads
        Thread[] threads = new Thread[_threads];
        for (int i = _threads - 1; i >= 0; --i) {
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel();
                while (thePixel.nextPixel(pixel)) {
                    Ray ray = camera.constructRayThroughPixel(nX, nY, pixel.col, pixel.row, //
                            dist, width, height);
                    Color color = calcColor(ray);
                    _imageWriter.writePixel(pixel.col, pixel.row,color.getColor());
                }
            });
        }

        // Start threads
        for (Thread thread : threads) thread.start();

        // Wait for all threads to finish
        for (Thread thread : threads)
            try {
                thread.join();
            } catch (Exception e) {
            }
        if (_print) System.out.printf("\r100%%\n");
    }

    /**
     * Set multithreading <br>
     * - if the parameter is 0 - number of coress less 2 is taken
     *
     * @param threads number of threads
     * @return the Render object itself
     */
    public Render setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading patameter must be 0 or higher");
        if (threads != 0)
            _threads = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            if (cores <= 2)
                _threads = 1;
            else
                _threads = cores;
        }
        return this;
    }

    /**
     * Set debug printing on
     *
     * @return the Render object itself
     */
    public Render setDebugPrint() {
        _print = true;
        return this;
    }

    /**
     * TODO javaDoc
     *
     * @param numOfsampel
     * @return
     */
    public Render setSoftSadow(int numOfsampel) {
        this._NumOfSampel = numOfsampel;
        return this;
    }

    /**
     * constructor
     *
     * @param imageWriter contain the view plane detail and path for save image
     * @param scene       detail on the scene
     */
    public Render(ImageWriter imageWriter, Scene scene) {
        this._scene = scene;
        this._imageWriter = imageWriter;
    }


    /**
     * calculate the scene data to create image
     */
    public void renderImage1() {
        Camera camera = _scene.getCamera();
        //java.awt.Color background = _scene.getBackground().getColor();
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        for (int row = 0; row < nY; row++) {
            for (int column = 0; column < nX; column++) {
                Ray ray = camera.constructRayThroughPixel(nX, nY, column, row, _scene.getDistance(), _imageWriter.getWidth(), _imageWriter.getHeight());
//                GeoPoint closesPoint = findClosestIntersection(ray);
//                if (closesPoint == null) {
//                    _imageWriter.writePixel(column, row, background);
//                } else {
                    java.awt.Color pixelColor = calcColor(ray).getColor();
                    _imageWriter.writePixel(column, row, pixelColor);
              //  }
            }
        }

    }


    /**
     * calculate color for single pixel
     *
     * @param geoPoint         the point to consider     * @param ray
     * @param RecursionLevel   double value for stop recursion if its arrive to MAX_CALC_COLOR_LEVEL
     * @param ResolutionEffect double value for stop recursion if  Resolution arrive to MIN_CALC_COLOR_K [no really effect]
     * @return pixel color
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray, double RecursionLevel, double ResolutionEffect) {

        Vector v = geoPoint.getPoint().subtract(_scene.getCamera().get_p0()).normalize();
        Vector normalVector = geoPoint.getGeometry().getNormal(geoPoint.getPoint());


        Material material = geoPoint.getGeometry().get_material();
        int nShininess = material.getnShininess();
        double kd = material.getkD();
        double ks = material.getkS();
        double kT = material.getkT();
        double kR = material.getkR();
        double newkT = ResolutionEffect * kT;
        double newkR = ResolutionEffect * kR;
        Color result = geoPoint.getGeometry().get_emission().scale(1-kT);
        result = result.add(getLightsColor(geoPoint, normalVector, v, nShininess, kd, ks, ResolutionEffect));

        if (RecursionLevel > 0 & newkT > MIN_CALC_COLOR_K) {
            Ray newRayTransparent = constructTransparentRay(geoPoint, ray);
            GeoPoint newPointTransparent = findClosestIntersection(newRayTransparent);
            if (newPointTransparent != null)
                result = result.add(calcColor(newPointTransparent, newRayTransparent, RecursionLevel - 1, newkT).scale(kT));
            else
                result = result.add(_scene.getBackground()).scale(kT);
        }
        if (RecursionLevel > 0 & newkR > MIN_CALC_COLOR_K) {
            Ray newRayReflected = constructReflectedRay(geoPoint, ray);
            GeoPoint newPointReflected = findClosestIntersection(newRayReflected);
            if (newPointReflected != null)
                result = result.add(calcColor(newPointReflected, newRayReflected, RecursionLevel - 1, newkR).scale(kR));
            else
                result = result.add(_scene.getBackground()).scale(kR);
        }
        return result;
    }



//    private Color calcColor(List<Ray> rays) {
//        Color result = _scene.getAmbientLight().getIntensity();
//        for (Ray ray : rays) {
//            GeoPoint closesPoint = findClosestIntersection(ray);
//            if (closesPoint == null)
//                result = result.add(_scene.getBackground().reduce(rays.size()));
//            else
//                result = result.add(calcColor(closesPoint, ray, MAX_CALC_COLOR_LEVEL, 1).reduce(rays.size()));
//        }
//        return result;
//    }


    /**
     * calculate global effects color for single pixel
     *
     * @param ray for
     * @return pixel color
     */
    private Color calcColor(Ray ray) {
        GeoPoint closesPoint = findClosestIntersection(ray);
        if (closesPoint == null)
            return _scene.getBackground();
        Color result = _scene.getAmbientLight().getIntensity();
        return result.add(calcColor(closesPoint, ray, MAX_CALC_COLOR_LEVEL, 1));
    }


    /**
     * @param geoPoint   Point to calculate color
     * @param normal     normal vector from point
     * @param v          direction from point to next geometry point
     * @param nShininess Shininess coefficient
     * @param kD         Diffuse coefficient
     * @param kS         Specular coefficient
     * @param kT         transparency coefficient
     * @return calculate light at point without recursion.
     */
    private Color getLightsColor(GeoPoint geoPoint, Vector normal, Vector v, int nShininess, double kD, double kS, double kT) {

        Color result = Color.BLACK;
        List<LightSource> lights = _scene.get_lights();
        if (lights != null) {

            for (LightSource lightSource : lights) {
                Vector l = lightSource.getL(geoPoint.getPoint());
                double nl = alignZero(normal.dotProduct(l));
                double nv = alignZero(normal.dotProduct(v));
                if (sign(nl) == sign(nv)) {
                    double ktr = transparency(l, geoPoint, normal, lightSource);
                    if (ktr * kT > MIN_CALC_COLOR_K) {
                        Color ip = lightSource.getIntensity(geoPoint.getPoint()).scale(ktr);
                        result = result.add(ip.scale(kD * negative(nl)),
                                calcSpecular(kS, l, normal, nl, v, nShininess, ip)
                        );
                    }
                }
            }
        }
        return result;
    }

    /**
     * @param signNumber
     * @return true if signNumber is positive or false if negative
     */
    private boolean sign(double signNumber) {
        return (signNumber >= 0d);
    }

    private double negative(double num) {
        if (num < 0)
            num = -num;
        return num;
    }

    /**
     * Calculate Specular component of light reflection.
     *
     * @param ks         specular component coef
     * @param l          direction from light to point
     * @param n          normal to surface at the point
     * @param nl         dot-product n*l
     * @param v          direction from point of view to point
     * @param nShininess shininess level
     * @param ip         light intensity at the point
     * @return specular component light effect at the point
     * @author Dan Zilberstein
     * <p>
     * Finally, the Phong model has a provision for a highlight, or specular, component, which reflects light in a
     * shiny way. This is defined by [rs,gs,bs](-V.R)^p, where R is the mirror reflection direction vector we discussed
     * in class (and also used for ray tracing), and where p is a specular power. The higher the value of p, the shinier
     * the surface.
     */
    private Color calcSpecular(double ks, Vector l, Vector n, double nl, Vector v, int nShininess, Color ip) {
        double p = nShininess;

        Vector R = l.add(n.scale(-2 * nl)); // nl must not be zero!
        double minusVR = -alignZero(R.dotProduct(v));
        if (minusVR <= 0) {
            return Color.BLACK; // view from direction opposite to r vector
        }
        return ip.scale(ks * Math.pow(minusVR, p));
    }

    /**
     * @param l           Vector from light source to GeoPoint
     * @param gp          GeoPoint that lighted by L
     * @param n           normal to GeoPoint
     * @param lightSource source of light
     * @return true if there isn't geometry between light source and GeoPoint that blocked the light (shade)
     * or false if there is
     */
    private boolean unshaded(Vector l, GeoPoint gp, Vector n, LightSource lightSource) {
        Ray rayTowardLight = new Ray(l.scale(-1), gp.getPoint(), n);
        double lightDistance = lightSource.getDistance(gp.getPoint());
        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(rayTowardLight, lightDistance);
        if (intersections == null)
            return true;
        for (GeoPoint geoPoint : intersections) {
            if (geoPoint.getGeometry().get_material().getkT() == 0)
                return false;
        }
        return true;
    }


    /**
     * @param l           Vector from light source to GeoPoint
     * @param gp          GeoPoint that lighted by L
     * @param n           normal to GeoPoint
     * @param lightSource source of light
     * @return double value that describe the shade level of geometries
     * between light source and GeoPoint.
     */
    private double transparency(Vector l, GeoPoint gp, Vector n, LightSource lightSource) {
        Ray rayTowardLight = new Ray(l.scale(-1), gp.getPoint(), n);
        double lightDistance = lightSource.getDistance(gp.getPoint());
        List<Ray> BeamOfRay = rayTowardLight.BeamOfRay(lightSource.getRadius() / lightDistance, _NumOfSampel);
        Intersectable Geometries = _scene.getGeometries();
        double ktr = 0;
        for (Ray ray : BeamOfRay) {
            ktr += getKtr(Geometries.findIntersections(ray, lightDistance));
        }
        return ktr / BeamOfRay.size();
    }

    /**
     * function calculate coefficient shadow of a ray
     *
     * @param intersections list intersection of ray
     * @return ktr
     */
    private double getKtr(List<GeoPoint> intersections) {
        if (intersections == null) return 1;
        double ktr = 1;
        for (GeoPoint geoPoint : intersections) {
            ktr *= geoPoint.getGeometry().get_material().getkT();
            if (ktr < MIN_CALC_COLOR_K) return 0;
        }
        return ktr;
    }

    /**
     * calculate the Closes Point to the camera
     *
     * @param intersectionPoints list of point to consider
     * @return the Closess Point to the camera
     */
    private GeoPoint getClosessPoint(Point3D p, List<GeoPoint> intersectionPoints) {
        if (intersectionPoints == null)
            return null;
        double distance;
        double minDistance = Double.MAX_VALUE;
        GeoPoint minPoint = null;
        //Point3D centerOfCamera = _scene.getCamera().get_p0();
        for (GeoPoint point : intersectionPoints) {
            distance = p.distance(point.getPoint());
            if (distance < minDistance) {
                minDistance = distance;
                minPoint = point;
            }
        }
        return minPoint;
    }

    /**
     * @param ray a Ray to find intersection points between it and scene geometries
     * @return closest intersection point from all intersections points
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        GeoPoint ClosestPoint = getClosessPoint(ray.get_tail(), _scene.getGeometries().findIntersections(ray));
        return ClosestPoint;
    }

    /**
     * @param p   GeoPoint to consider it's color
     * @param ray Ray of light
     * @return a Ray at same direction of light after p Point.
     */
    private Ray constructTransparentRay(GeoPoint p, Ray ray) {
        Vector normal = p.getGeometry().getNormal(p.getPoint());
        return new Ray(ray.get_direction(), p.getPoint(), normal.scale(-1));
    }

    /**
     * @param p   GeoPoint to consider it's color
     * @param ray Ray of light
     * @return a Ray at reflection direction from p Point.
     */
    private Ray constructReflectedRay(GeoPoint p, Ray ray) {
        Vector normal = p.getGeometry().getNormal(p.getPoint());
        Vector r = ray.get_direction().add(normal.scale(-2 * normal.dotProduct(ray.get_direction())));
        return new Ray(r, p.getPoint(), normal);
    }

    /**
     * write grid on the image for testing
     *
     * @param interval  the density of the grid
     * @param lineColor the color of the grid
     */
    public void printGrid(int interval, java.awt.Color lineColor) {
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        for (int row = 0; row < nY; row++) {
            for (int column = 0; column < nX; column++) {
                if (row % interval == 0 || column % interval == 0) {
                    _imageWriter.writePixel(column, row, lineColor);
                }
            }
        }
    }

    /**
     * write the image on the disk
     */
    public void writeToImage() {
        _imageWriter.writeToImage();
    }
}
