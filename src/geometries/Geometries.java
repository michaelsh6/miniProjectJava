package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Geometries class represents Collection of Intersectable objects
 */
public class Geometries extends Intersectable{
    List<Intersectable> geometries_list;
    /**
     * @return Geometries_list
     */
    public List<Intersectable> getGeometries_list() {
        return geometries_list;
    }

    /**
     * default constructor
     */
    public Geometries() {
        geometries_list= new ArrayList<Intersectable>();
    }

    /**
     * constructor
     * @param geometries list of Intersectable objects
     */
    public Geometries(Intersectable... geometries) {
        geometries_list= new ArrayList<Intersectable>(Arrays.asList(geometries));
        setBoundingBox(geometries);
    }

    /**
     * Add Intersectable objects to the collection
     * @param geometries list of Intersectable objects
     */
    public void add(Intersectable... geometries){
       // List<Intersectable> new_geometries_list= new ArrayList<Intersectable>(Arrays.asList(geometries));
        if(geometries ==null) return;
        for (Intersectable intersect:geometries) {
            geometries_list.add(intersect);
        }
        setBoundingBox(geometries);
    }

    /**
     * implements of Intersectable interface
     * @param ray a ray to calculate the intersections
     *  @param max max distance to calculate
     * @return list of intersections
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray, double max) {
        ArrayList<GeoPoint> intersections = null ;
        for (Intersectable intersect:geometries_list) {
            if(!isBoundingBoxIntersect(ray))
                continue;
            List<GeoPoint> object_intersection = intersect.findIntersections(ray,max);
            if(object_intersection == null)
                continue;
            if(intersections == null){
                intersections = new ArrayList<>();
            }
            for (GeoPoint geoPoint:object_intersection) {
                intersections.add(geoPoint);
            }
        }
        return intersections;
    }

    /**
     * set Bounding Box values
     * @param geometries geometries list
     */
    public void setBoundingBox(Intersectable[] geometries){
        for (Intersectable geometry : geometries) {
            this.boundingBoxMaxX = Math.max(this.boundingBoxMaxX, geometry.boundingBoxMaxX);
            this.boundingBoxMaxY = Math.max(this.boundingBoxMaxY, geometry.boundingBoxMaxY);
            this.boundingBoxMaxZ = Math.max(this.boundingBoxMaxZ, geometry.boundingBoxMaxZ);
            this.boundingBoxMinX = Math.min(this.boundingBoxMaxX, geometry.boundingBoxMinX);
            this.boundingBoxMinY = Math.min(this.boundingBoxMaxY, geometry.boundingBoxMinY);
            this.boundingBoxMinZ = Math.min(this.boundingBoxMaxZ, geometry.boundingBoxMinZ);
        }
    }
}
