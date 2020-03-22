package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Sphere extends RadialGeometry{
    Point3D _center;

    public Sphere(double _radius,Point3D _center) {
        super(_radius);
        this._center = new Point3D(_center);
    }

    public Point3D get_center() {
        return new Point3D(_center);
    }

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "_center=" + _center +
                ", _radius=" + _radius +
                '}';
    }
}
