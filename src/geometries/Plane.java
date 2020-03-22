package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Plane implements Geometry{

    Vector _normal;
    Point3D _p;
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        _normal = null;
        _p = p1;
    }

    @Override
    public Vector getNormal(Point3D point) {
        return new Vector(_normal);
    }

    public Vector getNormal() {
        return this.getNormal(null);
    }
}
