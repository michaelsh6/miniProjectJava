package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * PointLight class Represents light with a single illumination point
 * as well as the same illumination intensity in each direction
 */
public class PointLight extends Light implements LightSource {

    protected Point3D _position;
    protected double _kC, _kL, _kQ;

    /**
     * constructor
     * @param _intensity Color intensity
     * @param _poaition light poaition
     * @param kC Constant coefficient
     * @param kL Linear coefficient
     * @param kQ Square coefficient
     */
    public PointLight(Color _intensity, Point3D _poaition, double kC, double kL, double kQ) {
        super(_intensity);
        this._position = _poaition;
        this._kC = kC;
        this._kL = kL;
        this._kQ = kQ;
    }

    @Override
    public Color getIntensity(Point3D p) {
        double distance = _position.distance(p);
        double denominator = _kC + _kL *distance + _kQ *distance*distance;
        return _intensity.reduce(denominator);
    }

    @Override
    public Vector getL(Point3D p) {
        return p.subtract(_position).normalize();
    }


    @Override
    public double getDistance(Point3D point) {
        return _position.distance(point);
    }
}
