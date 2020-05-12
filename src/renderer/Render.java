package renderer;

import geometries.Intersectable;
import primitives.Color;
import scene.Scene;
import elements. *;
import primitives.*;
import primitives.Point3D;
import java.util.List;
//TODO javadoc

public class Render {

    private final Scene _scene;
    private final ImageWriter _imageWriter;

    public Render(ImageWriter imageWriter, Scene scene) {
        this._scene = scene;
        this._imageWriter = imageWriter;
    }

    public void renderImage() {
        Camera camera = _scene.getCamera();
        Intersectable geometries = _scene.getGeometries();
        java.awt.Color background = _scene.getBackground().getColor();
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        for (int row = 0; row < nY; row++) {
            for (int column = 0; column < nX; column++) {
                Ray ray = camera.constructRayThroughPixel(nX, nY, column, row, _scene.getDistance(), _imageWriter.getWidth(), _imageWriter.getHeight());
                List<Point3D> intersectionPoints = geometries.findIntersections(ray);
                if (intersectionPoints == null)
                {
                    _imageWriter.writePixel(column, row, background);
                }
                else {
                    Point3D closesPoint = getClosessPoint (intersectionPoints);
                    java.awt.Color pixelColor = calcColator(closesPoint).getColor();
                    _imageWriter.writePixel(column, row, pixelColor);
                }
                }
            }

        }

    private Color calcColator(Point3D closesPoint) {
        return _scene.getAmbientLight().getIntensity();
    }

    private Point3D getClosessPoint(List<Point3D> intersectionPoints) {
        double distance;
        double minDistance = Double.MAX_VALUE ;
        Point3D minPoint = null;
        Point3D centerOfCamera = _scene.getCamera().get_p0();
        for (Point3D point:intersectionPoints){
            distance = centerOfCamera.distance(point);
            if (distance < minDistance) {
                minDistance = distance;
                minPoint = point;
            }
        }
        return minPoint;
    }


    public void printGrid(int interval, java.awt.Color lineColor) {
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        for (int row = 0; row < nY; row++) {
            for (int column = 0; column < nX; column++){
                if (row % interval == 0 || column % interval == 0) {
                    _imageWriter.writePixel(column, row, lineColor);
                }
            }
        }
    }

    public void writeToImage() {
        _imageWriter.writeToImage();
    }
}
