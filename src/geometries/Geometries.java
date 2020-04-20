package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
//TODO javaDoc
public class Geometries implements Intersectable{
    List<Intersectable> geometries_list;

    public List<Intersectable> getGeometries_list() {
        return geometries_list;
    }

    public Geometries() {
        ///TODO implement
        geometries_list= new ArrayList<Intersectable>();
    }

    public Geometries(Intersectable... geometries) {
        ///TODO implement
        geometries_list= new ArrayList<Intersectable>(Arrays.asList(geometries));
    }

    public void add(Intersectable... geometries){
        ///TODO implement
        List<Intersectable> new_geometries_list= new ArrayList<Intersectable>(Arrays.asList(geometries));
        for (Intersectable intersect:new_geometries_list) {
            geometries_list.add(intersect);
        }
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        ///TODO implement
        ArrayList<Point3D> intersections = null ;
        for (Intersectable intersect:geometries_list) {
            for (Point3D point3D:intersect.findIntersections(ray)) {
                intersections.add(point3D);
            }
        }
        return intersections;
    }
}
