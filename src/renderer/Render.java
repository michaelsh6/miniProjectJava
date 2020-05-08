package renderer;

import geometries.Intersectable;
import primitives.Color;
import scene.Scene;
import elements. *;
import primitives.*;

import java.awt.*;

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
                List <Point3D> intersectionPoints = geometries.findIntersections(ray);
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
    //TODO
    private java.awt.Color calcColator(Point3D closesPoint) {
        return null;
    }
    //TODO
    private Point3D getClosessPoint(List<Point3D> intersectionPoints) {
        return null;
    }


    //TODO
    public void printGrid(int interval, Color lineColor) {
    }
    //TODO
    public void writeToImage() {
    }
}
