package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * Plane class represents Plane in 3d coordinate system
 */
public class Plane implements Geometry{

    Vector _normal;
    Point3D _p;

    /**
     * constractor
     * get 3 point in 3d coordinate system and calculate the normal and 1 point in the Plane
     * @param p1 point 1
     * @param p2 point 2
     * @param p3 point 3
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);

        _normal = v1.crossProduct(v2).normalize();
        _p = p1;
    }

    /**
     * implements of Geometry interface
     * @param point  3d point(not relevant in Plane)
     * @return the notmal
     */
    @Override
    public Vector getNormal(Point3D point) {
        return new Vector(_normal);
    }

    /**
     * get function
     * @return the notmal
     */
    public Vector getNormal() {
        return this.getNormal(null);
    }
}
