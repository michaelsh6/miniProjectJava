
package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 *  Sphere class represents Sphere in 3d coordinate system
 */
public class Sphere extends RadialGeometry{
    Point3D _center;

    /**
     * comstractor
     * @param _radius the radius of the Sphere
     * @param _center the center of the Sphere in 3d coordinate system
     */
    public Sphere(double _radius,Point3D _center) {
        super(_radius);
        this._center = new Point3D(_center);
    }

    /**
     * get function
     * @return center
     */
    public Point3D get_center() {
        return new Point3D(_center);
    }

    /**
     *implements of Geometry interface
     * @param point 3d point to calculate the normal
     * @return normal
     */
    @Override
    public Vector getNormal(Point3D point) {
        return point.subtract(this._center).normalize();
    }

    /**
     *
     * @return string describe the Cylinder
     */
    @Override
    public String toString() {
        return "Sphere{" +
                "_center=" + _center +
                ", _radius=" + _radius +
                '}';
    }
}
