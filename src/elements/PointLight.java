package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class PointLight extends Light implements LightSource {

    protected Point3D _position;
    protected double _kC,kL,kQ;

    public PointLight(Color _intensity, Point3D _poaition, double _kC, double kL, double kQ) {
        super(_intensity);
        this._position = _poaition;
        this._kC = _kC;
        this.kL = kL;
        this.kQ = kQ;
    }

    @Override
    public Color getIntensity(Point3D p) {
        //TODO implement
        double distance = _position.distance(p);
        double denominator = _kC + kL*distance + kQ*distance*distance;
        return _intensity.reduce(denominator);
    }

    @Override
    public Vector getL(Point3D p) {
        return p.subtract(_position).normalize();
    }
}
