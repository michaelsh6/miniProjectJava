package renderer;
import geometries.Intersectable.GeoPoint;
import geometries.Intersectable;
import primitives.Color;
import scene.Scene;
import elements.*;
import primitives.*;
import primitives.Point3D;

import java.util.List;

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
     * @param closesPoint the point to consider
     * @return pixel color
     */
    private Color calcColator(GeoPoint closesPoint) {
        //TODO implement light
        Color pointColor = closesPoint.getGeometry().get_emission();
        return _scene.getAmbientLight().getIntensity().add(pointColor);
    }

    /**
     * calculate the Closess Point to the camera
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
