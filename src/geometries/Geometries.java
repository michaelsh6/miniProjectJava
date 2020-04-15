package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;
//TODO javaDoc
public class Geometries implements Intersectable{
    List<Intersectable> geometries;

    public Geometries() {
        ///TODO implement
        ///Geometries_list = geometries_list;
    }

    public Geometries(Intersectable... geometries) {
        ///TODO implement
        ///Geometries_list = geometries_list;
    }

    public void add(Intersectable... geometries){
        ///TODO implement
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        ///TODO implement
        return null;
    }
}
