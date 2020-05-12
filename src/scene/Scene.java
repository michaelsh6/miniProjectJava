package scene;

import elements.AmbientLight;
import elements.Camera;
import geometries.Geometries;
import geometries.Geometry;
import geometries.Intersectable;
import geometries.Sphere;
import primitives.Color;

import java.util.ArrayList;

//TODO javadoc

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

    public String getName() {
        return _name;
    }

    public Color getBackground() {
        return _background;
    }

    public AmbientLight getAmbientLight() {
        return _ambientLight;
    }

    public Geometries getGeometries() {
        return _geometries;
    }

    public Camera getCamera() {
        return _camera;
    }

    public double getDistance() {
        return _distance;
    }

    public void setBackground(Color background) {
        _background = background;
    }

    public void setAmbientLight(AmbientLight ambientLight) {
        _ambientLight = ambientLight;
    }

    public void setCamera(Camera camera) {
        _camera = camera;
    }

    public void setDistance(double distance) {
        _distance = distance;
    }


    public void addGeometries(Geometry... geometries) {
        _geometries.add(geometries);
    }
}
