package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Tube extends RadialGeometry {

    Ray _axisRay;

    public Tube(double _radius, Ray _axisRay) {
        super(_radius);
        this._axisRay = new Ray(_axisRay);
    }

    public Ray get_axisRay() {
        return new Ray(_axisRay);
    }


    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "_axisRay=" + _axisRay +
                ", _radius=" + _radius +
                '}';
    }
}
