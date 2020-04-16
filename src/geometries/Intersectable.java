package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

/**
 *  Geometry interface represents all object That can be intersect by a ray
 */
public interface Intersectable {
    /**
     * *functoin to implement for calculating the Intersections of ray with Intersectable object
     * @param ray a ray
     * @return list of Intersections
     */
    List<Point3D> findIntersections(Ray ray);
}
