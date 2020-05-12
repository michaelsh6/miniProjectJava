package scene;

import elements.AmbientLight;
import elements.Camera;
import geometries.Geometries;
import geometries.Geometry;
import geometries.Intersectable;
import geometries.Sphere;
import primitives.Color;

import java.util.ArrayList;

/**
 * the class represent scene contain geometries lights and properties for rendering
 */
public class Scene {

    String _name;
    Color _background;
    AmbientLight _ambientLight;
    Geometries _geometries;
    Camera _camera;
    double _distance;

    public Scene(String name) {
        _name = name;
       _geometries = new Geometries();
    }

    /**
     * @return the file path
     */
    public String getName() {
        return _name;
    }

    /**
     * @return background of the image
     */
    public Color getBackground() {
        return _background;
    }
    /**
     * @return the Ambient Light of the scene
     */
    public AmbientLight getAmbientLight() {
        return _ambientLight;
    }

    /**
     * @return list of Geometries in the scene
     */
    public Geometries getGeometries() {
        return _geometries;
    }

    /**
     * @return camera in the scene
     */
    public Camera getCamera() {
        return _camera;
    }
    /**
     * @return Distance between the camera and the view plane
     */
    public double getDistance() {
        return _distance;
    }
    /**
     *set background of the image
     */
    public void setBackground(Color background) {
        _background = background;
    }
    /**
     * set the Ambient Light of the scene
     */
    public void setAmbientLight(AmbientLight ambientLight) {
        _ambientLight = ambientLight;
    }
    /**
     * set camera in the scene
     */
    public void setCamera(Camera camera) {
        _camera = camera;
    }
    /**
     * set Distance between the camera and the view plane
     */
    public void setDistance(double distance) {
        _distance = distance;
    }

    /**
     * add Geometries to the scene
     */
    public void addGeometries(Geometry... geometries) {
        _geometries.add(geometries);
    }
}
