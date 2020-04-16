package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Plane class represents Plane in 3d coordinate system
 */
public class Plane implements Geometry{

    Vector _normal;
    Point3D _p;

    /**
     * constructor
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
     * @return the normal
     */
    public Vector getNormal() {
        return this.getNormal(null);
    }

    /**
     * implements of Intersectable interface
     * @param ray a ray to calculate the intersections
     * @return list of intersections
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {

        double nv = _normal.dotProduct(ray.get_direction());
        if(Util.isZero(nv))
            return null;
        double size_scale = Util.alignZero(_normal.dotProduct(_p.subtract(ray.get_tail()))/nv);
        if(Util.alignZero(size_scale)<=0)
            return null;
        Point3D p = ray.getPoint(size_scale);
        return new ArrayList<>(){{add(p);}};

    }
}
