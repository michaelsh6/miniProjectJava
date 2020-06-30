
package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

/**
 *  Sphere class represents Sphere in 3d coordinate system
 */
public class Sphere extends RadialGeometry{
    final Point3D _center;

    /**
     * constructor
     * @param emission emission  color
     * @param material material object
     * @param _radius the radius of the Sphere
     * @param _center the center of the Sphere in 3d coordinate system
     */
    public Sphere(Color emission,Material material, double _radius, Point3D _center) {
        super(emission,material,_radius);
        this._center = _center;
        //bounding box
        boundingBoxMinX = this._center.get_x().get() -_radius;
        boundingBoxMaxX= this._center.get_x().get() +_radius;
        boundingBoxMinY= this._center.get_y().get() -_radius;
        boundingBoxMaxY= this._center.get_y().get() +_radius;
        boundingBoxMinZ= this._center.get_z().get() -_radius;
        boundingBoxMaxZ= this._center.get_z().get() +_radius;
    }

    /**
     * constructor
     * @param emission emission  color
     * @param _radius the radius of the Sphere
     * @param _center the center of the Sphere in 3d coordinate system
     */
    public Sphere(Color emission, double _radius, Point3D _center) {
        this(emission,new Material(0,0,0),_radius,_center);
    }

    /**
     * comstractor
     * @param _radius the radius of the Sphere
     * @param _center the center of the Sphere in 3d coordinate system
     */
    public Sphere(double _radius, Point3D _center) {
        this(Color.BLACK,_radius,_center);
    }

    /**
     * get function
     * @return center
     */
    public Point3D get_center() {
        return _center;
    }

    /**
     *implements of Geometry interface
     * @param point 3d point to calculate the normal
     * @return normal
     */
    @Override
    public Vector getNormal(Point3D point) {
        return point.subtract(this._center).normalize();
    }

    /**
     *
     * @return string describe the Cylinder
     */
    @Override
    public String toString() {
        return "Sphere{" +
                "_center=" + _center +
                ", _radius=" + _radius +
                '}';
    }

    /**
     * implements of Intersectable interface
     * @param ray a ray to calculate the intersections
     * @param max max distance to calculate
     * @return list of intersections
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray,double max) {
        Point3D p0 = ray.get_tail();
        Vector V = ray.get_direction();
        double t1 = -1;
        double t2 = -1;
        if(_center.equals(p0)){
            t1 = _radius;
        }
        else {
            Vector U = _center.subtract(p0);
            double tm = V.dotProduct(U);
            double d = Math.sqrt(U.length()*U.length()-tm*tm);

            if(Util.alignZero(d)>=_radius)
                return null;
            double th = Math.sqrt(_radius*_radius-d*d);
            t1 = tm+th;
            t2 = tm-th;

        }
        List<GeoPoint> Intersections = null;
        if(t1>0 | t2>0) {
            Intersections = new ArrayList<>();

            if (t1 > 0 ) {
                Point3D p = ray.getPoint(t1);
                double t = p.distance(ray.get_tail());
                if(Util.alignZero( max-t) >= 0)
                    Intersections.add(new GeoPoint(this, p));
            }
            if (t2 > 0){
                Point3D p = ray.getPoint(t2);
                double t = p.distance(ray.get_tail());
                if(Util.alignZero( max-t) >= 0)
                    Intersections.add(new GeoPoint(this, p));
            }
        }

        return Intersections;
    }
}
