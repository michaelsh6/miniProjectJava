package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 *  Geometry interface represents all Geometrys 3d object
 */
public interface Geometry extends Intersectable{
    /**
     *functoin to implement for calculating the normal for 3d object In reference to 3d point
     * @param point  3d point
     * @return normalize vector
     */
    Vector getNormal(Point3D point);
}
