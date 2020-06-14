package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 *DirectionalLight class Represents a distant light of constant illumination regardless of distance from the object
 */
public class DirectionalLight extends Light implements LightSource {
    Vector _direction;

    /**
     * constructor
     * @param _intensity Color intensity
     * @param _direction light direction
     */
    public DirectionalLight(Color _intensity, Vector _direction) {
        super(_intensity);
        this._direction = _direction.normalize();
    }

    @Override
    public Color getIntensity(Point3D p) {

        return _intensity;
    }

    @Override
    public Vector getL(Point3D p) {
        return _direction;
    }

    @Override
    public double getDistance(Point3D point) {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public double getRadius(){
        return 0;
    }
}
