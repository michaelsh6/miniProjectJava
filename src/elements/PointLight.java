package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class PointLight extends Light implements LightSource {

    protected Point3D _poaition;
    protected double _kC,kL,kQ;

    public PointLight(Color _intensity, Point3D _poaition, double _kC, double kL, double kQ) {
        super(_intensity);
        this._poaition = _poaition;
        this._kC = _kC;
        this.kL = kL;
        this.kQ = kQ;
    }

    @Override
    public Color getIntensity(Point3D p) {
        //TODO implement
        return null;
    }

    @Override
    public Vector getL(Point3D p) {
        //TODO implement
        return null;
    }
}
