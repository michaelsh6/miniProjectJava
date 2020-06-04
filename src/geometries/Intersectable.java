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
    default List<GeoPoint> findIntersections(Ray ray) {
        return findIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * functoin to implement for calculating the Intersections of ray with Intersectable object until the max distance
     * @param ray  a ray
     * @param max max distance
     * @return list of Intersections
     */
    List<GeoPoint> findIntersections(Ray ray, double max);




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


}

