package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

/**
 *  Geometry  abstract class represents all object That can be intersect by a ray
 */
public abstract class Intersectable {

    protected double
            boundingBoxMinX = Double.POSITIVE_INFINITY
            ,boundingBoxMaxX = Double.NEGATIVE_INFINITY
            ,boundingBoxMinY= Double.POSITIVE_INFINITY
            ,boundingBoxMaxY = Double.NEGATIVE_INFINITY
            ,boundingBoxMinZ= Double.POSITIVE_INFINITY
            ,boundingBoxMaxZ = Double.NEGATIVE_INFINITY;

    /**
     * *function to implement for calculating the Intersections of ray with Intersectable object
     * @param ray a ray
     * @return list of Intersections
     */
   public  List<GeoPoint> findIntersections(Ray ray) {
        return findIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * functoin to implement for calculating the Intersections of ray with Intersectable object until the max distance
     * @param ray  a ray
     * @param max max distance
     * @return list of Intersections
     */
    public abstract List<GeoPoint>  findIntersections(Ray ray, double max);



    /**
     * BoundingBoxIntersect
     * @param ray rey to check
     * @return true if Bounding Box Intersect with ray else false
     */

    public boolean isBoundingBoxIntersect(Ray ray){
//        Point3D head = ray.get_tail();
//        Point3D dir = ray.get_direction().get_head();
//        double[] dirRay = {dir.get_x().get(), dir.get_y().get(), dir.get_z().get()},
//                tailRay = {head.get_x().get(), head.get_y().get(), head.get_z().get()};
//        double[] minBoundingBox = {boundingBoxMinX, boundingBoxMinY, boundingBoxMinZ},
//                 maxBoundingBox = {boundingBoxMaxX, boundingBoxMaxY, boundingBoxMaxZ};
//
//        double tmin = Double.NEGATIVE_INFINITY, tmax = Double.POSITIVE_INFINITY;
//        for (int i = 0; i < 3; i++) {
//            if (dirRay[i] != 0.0) {
//                double t1 = (minBoundingBox[i] - tailRay[i]) / dirRay[i];
//                double t2 = (maxBoundingBox[i] - tailRay[i]) / dirRay[i];
//
//                tmin = Math.max(tmin, Math.min(t1, t2));
//                tmax = Math.min(tmax, Math.max(t1, t2));
//            }
//            else if (tailRay[i] <= minBoundingBox[i] || tailRay[i] >= maxBoundingBox[i] || tmax <= 0.0)
//                return false;
//        }
//
//        return (tmax >= tmin ) && (tmax > 0.0);



        Point3D start = ray.get_tail();

        double start_X = start.get_x().get();
        double start_Y = start.get_y().get();
        double start_Z = start.get_z().get();

        Point3D direction = ray.get_direction().get_head();

        double direction_X = direction.get_x().get();
        double direction_Y = direction.get_y().get();
        double direction_Z = direction.get_z().get();

        double max_t_for_X;
        double min_t_for_X;

        //If the direction_X is negative then the _min_X give the maximal value
        if (direction_X < 0) {
            max_t_for_X = (boundingBoxMinX - start_X) / direction_X;
            // Check if the Intersectble is behind the camera
            if (max_t_for_X <= 0) return false;
            min_t_for_X = (boundingBoxMaxX - start_X) / direction_X;
        }
        else if (direction_X > 0) {
            max_t_for_X = (boundingBoxMaxX - start_X) / direction_X;
            if (max_t_for_X <= 0) return false;
            min_t_for_X = (boundingBoxMinX - start_X) / direction_X;
        }
        else {
            if (start_X >=boundingBoxMaxX || start_X <= boundingBoxMinX)
                return false;
            else{
                max_t_for_X = Double.POSITIVE_INFINITY;
                min_t_for_X = Double.NEGATIVE_INFINITY;
            }
        }

        double max_t_for_Y;
        double min_t_for_Y;

        if (direction_Y < 0) {
            max_t_for_Y = (boundingBoxMinY - start_Y) / direction_Y;
            if (max_t_for_Y <= 0) return false;
            min_t_for_Y = (boundingBoxMaxY - start_Y) / direction_Y;
        }
        else if (direction_Y > 0) {
            max_t_for_Y = (boundingBoxMaxY - start_Y) / direction_Y;
            if (max_t_for_Y <= 0) return false;
            min_t_for_Y = (boundingBoxMinY - start_Y) / direction_Y;
        }
        else {
            if (start_Y >= boundingBoxMaxY || start_Y <= boundingBoxMinY)
                return false;
            else{
                max_t_for_Y = Double.POSITIVE_INFINITY;
                min_t_for_Y = Double.NEGATIVE_INFINITY;
            }
        }

        //Check the maximal and the minimal value for t
        double temp_max = Math.min(max_t_for_Y,max_t_for_X);
        double temp_min = Math.max(min_t_for_Y,min_t_for_X);
        temp_min = Math.max(temp_min,0);

        if (temp_max < temp_min) return false;

        double max_t_for_Z;
        double min_t_for_Z;

        if (direction_Z < 0) {
            max_t_for_Z = (boundingBoxMinZ - start_Z) / direction_Z;
            if (max_t_for_Z <= 0) return false;
            min_t_for_Z = (boundingBoxMaxZ - start_Z) / direction_Z;
        }
        else if (direction_Z > 0) {
            max_t_for_Z = (boundingBoxMaxZ - start_Z) / direction_Z;
            if (max_t_for_Z <= 0) return false;
            min_t_for_Z = (boundingBoxMinZ - start_Z) / direction_Z;
        }
        else {
            if (start_Z >= boundingBoxMaxZ || start_Z <= boundingBoxMinZ)
                return false;
            else{
                max_t_for_Z = Double.POSITIVE_INFINITY;
                min_t_for_Z = Double.NEGATIVE_INFINITY;
            }
        }

        temp_max = Math.min(max_t_for_Z,temp_max);
        temp_min = Math.max(min_t_for_Z,temp_min);

        if (temp_max < temp_min) return false;

        return true;


    }

    /**
     * GeoPoint class represent A point that is linked to its geometry object
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point;

        /**
         * constructor
         * @param geometry  linked geometry
         * @param point point location
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }

        /**
         * @return Geometry
         */
        public Geometry getGeometry() {
            return geometry;
        }

        /**
         * @return tPoint
         */
        public Point3D getPoint() {
            return point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geometry == geoPoint.geometry &&
                    point.equals( geoPoint.point);
        }

    }

    public Point3D getBoudingBoxCenter(){
        return new Point3D(
                (boundingBoxMaxX+boundingBoxMinX)/2,
                (boundingBoxMaxY+boundingBoxMinY)/2,
                (boundingBoxMaxZ+boundingBoxMinZ)/2);
    }

    public double BoundingBoxDistance(Intersectable boundingBox){
        return this.getBoudingBoxCenter().distance(boundingBox.getBoudingBoxCenter());
    }

}



//        double d = dir.get_x().get();
//        d= d==0?0.0000001:d;
//        double minT  = (boundingBoxMinX-head.get_x().get())/d;
//        double maxT  = (boundingBoxMaxX-head.get_x().get())/d;
//        if (minT>maxT){
//            double temp = minT;
//            minT = maxT;
//            maxT = temp;
//        }
//        d = dir.get_y().get();
//        d= d==0?0.0000001:d;
//        double minT1 =  (boundingBoxMinY-head.get_y().get())/d;
//        double maxT1=   (boundingBoxMaxY-head.get_y().get())/d;
//        if (minT1>maxT1){
//            double temp = minT1;
//            minT1 = maxT1;
//            maxT1 = temp;
//        }
//        minT  = Math.max( minT,minT1);
//        maxT  = Math.min( maxT,maxT1);
//        d = dir.get_z().get();
//        d= d==0?0.0000001:d;
//        minT1 =  (boundingBoxMinZ-head.get_z().get())/d;
//        maxT1=   (boundingBoxMaxZ-head.get_z().get())/d;
//        if (minT1>maxT1){
//            double temp = minT1;
//            minT1 = maxT1;
//            maxT1 = temp;
//        }
//        minT  = Math.max( minT,minT1);
//        maxT  = Math.min( maxT,maxT1);
//        return  (minT<=maxT) && (maxT>0);

