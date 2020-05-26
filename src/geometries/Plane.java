package geometries;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Plane class represents Plane in 3d coordinate system
 */
public class Plane extends Geometry{

    Vector _normal;
    Point3D _p;

    /**
     * constructor
     * @param emission emission color
     * @param p1 point 1
     * @param p2 point 2
     * @param p3 point 3
     */
    public Plane(Color emission , Point3D p1, Point3D p2, Point3D p3){
        this(emission,new Material(0,0,0),p1, p2, p3);

    }

    /**
     * constructor
     * @param emission emission color
     * @param material material object
     * @param p1 point 1
     * @param p2 point 2
     * @param p3 point 3
     */
    public Plane(Color emission, Material material, Point3D p1, Point3D p2, Point3D p3) {
        super(emission, material);
        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);

        _normal = v1.crossProduct(v2).normalize();
        _p = p1;
    }

    /**
     * constructor
     * get 3 point in 3d coordinate system and calculate the normal and 1 point in the Plane
     * @param p1 point 1
     * @param p2 point 2
     * @param p3 point 3
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        this(Color.BLACK,new Material(0,0,0),p1, p2, p3);
    }

    /**
     * implements of Geometry interface
     * @param point  3d point(not relevant in Plane)
     * @return the notmal
     */
    @Override
    public Vector getNormal(Point3D point) {
        return new Vector(_normal);
    }

    /**
     * get function
     * @return the normal
     */
    public Vector getNormal() {
        return this.getNormal(null);
    }

    /**
     * implements of Intersectable interface
     * @param ray a ray to calculate the intersections
     * @return list of intersections
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray) {

        double nv = _normal.dotProduct(ray.get_direction());
        if(Util.isZero(nv))
            return null;
        double size_scale = Util.alignZero(_normal.dotProduct(_p.subtract(ray.get_tail()))/nv);
        if(Util.alignZero(size_scale)<=0)
            return null;
        GeoPoint p = new GeoPoint(this,ray.getPoint(size_scale));
        return new LinkedList<>(){{add(p);}};

    }
}
