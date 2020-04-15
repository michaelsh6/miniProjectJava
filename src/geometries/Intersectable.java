package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

public interface Intersectable {
    //TODO javaDoc
    List<Point3D> findIntersections(Ray ray);
}
