package renderer;
import geometries.Intersectable.GeoPoint;
import geometries.Intersectable;
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

    /**
     * constructor
     *
     * @param imageWriter contain the view plane detail and path for save image
     * @param scene detail on the scene
     */
    public Render(ImageWriter imageWriter, Scene scene) {
        this._scene = scene;
        this._imageWriter = imageWriter;
    }

    /**
     * calculate the scene data to create image
     */
    public void renderImage() {
        Camera camera = _scene.getCamera();
        Intersectable geometries = _scene.getGeometries();
        java.awt.Color background = _scene.getBackground().getColor();
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        for (int row = 0; row < nY; row++) {
            for (int column = 0; column < nX; column++) {
                Ray ray = camera.constructRayThroughPixel(nX, nY, column, row, _scene.getDistance(), _imageWriter.getWidth(), _imageWriter.getHeight());
                List<GeoPoint> intersectionPoints = geometries.findIntersections(ray);
                if (intersectionPoints == null) {
                    _imageWriter.writePixel(column, row, background);
                } else {
                    GeoPoint closesPoint = getClosessPoint(intersectionPoints);
                    java.awt.Color pixelColor = calcColator(closesPoint).getColor();
                    _imageWriter.writePixel(column, row, pixelColor);
                }
            }
        }

    }

    /**
     * calculate color for single pixel
     * @param geoPoint the point to consider
     * @return pixel color
     */
    private Color calcColator(GeoPoint geoPoint) {
        Color result = _scene.getAmbientLight().getIntensity();
        result = result.add(geoPoint.getGeometry().get_emission());
        List<LightSource> lights = _scene.get_lights();

        Vector v = geoPoint.getPoint().subtract(_scene.getCamera().get_p0()).normalize();
        Vector normalVector = geoPoint.getGeometry().getNormal(geoPoint.getPoint());

        Material material = geoPoint.getGeometry().get_material();
        int nShininess = material.getnShininess();
        double kd = material.getkD();
        double ks = material.getkS();
        if (_scene.get_lights() != null) {
            for (LightSource lightSource : lights) {

                Vector l = lightSource.getL(geoPoint.getPoint());
                double nl = alignZero(normalVector.dotProduct(l));
                double nv = alignZero(normalVector.dotProduct(v));

                if (sign(nl) == sign(nv)) {
                    Color ip = lightSource.getIntensity(geoPoint.getPoint());
                    result = result.add(ip.scale(kd * negative(nl)),
                            calcSpecular(ks, l, normalVector, nl, v, nShininess, ip)
                    );
                }
            }
        }

        return result;
    }

    /**
     *
     * @param signNumber
     * @return true if signNumber is positive or false if negative
     */
    private boolean sign (double signNumber)
    {
        return (signNumber >= 0d);
    }

    private double negative (double num) {
        if (num > 0)
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
     * calculate the Closes Point to the camera
     * @param intersectionPoints list of point to consider
     * @return the Closess Point to the camera
     */
    private GeoPoint getClosessPoint(List<GeoPoint> intersectionPoints) {
        double distance;
        double minDistance = Double.MAX_VALUE;
        GeoPoint minPoint = null;
        Point3D centerOfCamera = _scene.getCamera().get_p0();
        for (GeoPoint point : intersectionPoints) {
            distance = centerOfCamera.distance(point.getPoint());
            if (distance < minDistance) {
                minDistance = distance;
                minPoint = point;
            }
        }
        return minPoint;
    }

    /**
     * write grid on the image for testing
     * @param interval the density of the grid
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
