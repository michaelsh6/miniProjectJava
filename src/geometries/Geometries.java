package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Geometries class represents Collection of Intersectable objects
 */
public class Geometries extends Intersectable {
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
        geometries_list = new ArrayList<Intersectable>();
    }


    /**
     * constructor
     *
     * @param geometries list of Intersectable objects
     */
    public Geometries(Intersectable... geometries) {
        geometries_list = new ArrayList<Intersectable>(Arrays.asList(geometries));
        setBoundingBox(geometries);
    }


    /**
     * Add Intersectable objects to the collection
     *
     * @param geometries list of Intersectable objects
     */
    public void add(Intersectable... geometries) {
        // List<Intersectable> new_geometries_list= new ArrayList<Intersectable>(Arrays.asList(geometries));
        if (geometries == null) return;
        for (Intersectable intersect : geometries) {
            geometries_list.add(intersect);
        }
        setBoundingBox(geometries);
    }

    /**
     * implements of Intersectable interface
     *
     * @param ray a ray to calculate the intersections
     * @param max max distance to calculate
     * @return list of intersections
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray, double max) {
        ArrayList<GeoPoint> intersections = null;
        for (Intersectable intersect : geometries_list) {
            if (!isBoundingBoxIntersect(ray))
                continue;
            List<GeoPoint> object_intersection = intersect.findIntersections(ray, max);
            if (object_intersection == null)
                continue;
            if (intersections == null) {
                intersections = new ArrayList<>();
            }
            for (GeoPoint geoPoint : object_intersection) {
                intersections.add(geoPoint);
            }
        }
        return intersections;
    }

    /**
     * set Bounding Box values
     *
     * @param geometries geometries list
     */
    public void setBoundingBox(Intersectable[] geometries) {
        for (Intersectable geometry : geometries) {
            this.boundingBoxMaxX = Math.max(this.boundingBoxMaxX, geometry.boundingBoxMaxX);
            this.boundingBoxMaxY = Math.max(this.boundingBoxMaxY, geometry.boundingBoxMaxY);
            this.boundingBoxMaxZ = Math.max(this.boundingBoxMaxZ, geometry.boundingBoxMaxZ);
            this.boundingBoxMinX = Math.min(this.boundingBoxMinX, geometry.boundingBoxMinX);
            this.boundingBoxMinY = Math.min(this.boundingBoxMinY, geometry.boundingBoxMinY);
            this.boundingBoxMinZ = Math.min(this.boundingBoxMinZ, geometry.boundingBoxMinZ);
        }
    }

    /**
     * flatten the geometries list
     */
    public void flatten() {
        Geometries new_geometries = new Geometries(geometries_list.toArray(new Intersectable[geometries_list.size()]));
        geometries_list.clear();
        flatten(new_geometries);

    }

    /**
     * recursive func to flatten the geometries list
     * @param new_geometries current geometries
     */
    private void flatten(Geometries new_geometries) {
        for (Intersectable intersectable : new_geometries.geometries_list) {
            if (intersectable instanceof Geometry)
                geometries_list.add(intersectable);
            else
                flatten((Geometries) intersectable);
        }
    }

    /**
     * build bvh tree
     */
    public void BuildTree(){
        this.flatten();
        double distance;
        Intersectable bestGeomtry1 = null,bestGeomtry2 = null;
        while (geometries_list.size() > 1 ){
            double best = Double.MAX_VALUE;
            for(Intersectable geomtry1 :geometries_list){
                for(Intersectable geomtry2 :geometries_list){
                    distance = geomtry1.BoundingBoxDistance(geomtry1);
                    if(!geomtry1.equals(geomtry2) && distance<best){
                        best = distance;
                        bestGeomtry1 = geomtry1;
                        bestGeomtry2 = geomtry2;
                    }
                }
            }
            geometries_list.add(new Geometries(bestGeomtry1,bestGeomtry2));
            geometries_list.remove(bestGeomtry1);
            geometries_list.remove(bestGeomtry2);
        }
    }

}