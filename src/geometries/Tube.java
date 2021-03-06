package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Tube class is Infinite Tube in 3d coordinate system
 */
public class Tube extends RadialGeometry {

    Ray _axisRay;

    /**
     * constractor
     * @param _radius the radius of the Tube
     * @param _axisRay the direction of the Tube (ray object)
     */
    public Tube(double _radius, Ray _axisRay) {
        super(_radius);
        this._axisRay = new Ray(_axisRay);
    }

    /**
     * get fanction
     * @return axis Ray
     */
    public Ray get_axisRay() {
        return new Ray(_axisRay);
    }

    /**
     *implements of Geometry interface
     * @param point 3d point to calculate the normal
     * @return normal
     */
    @Override
    public Vector getNormal(Point3D point) {
        Vector rayDirection = this._axisRay.get_direction();
        Vector v1 = point.subtract(this._axisRay.get_tail());
        double dotProduct = v1.dotProduct(rayDirection);
        Vector v2 = rayDirection.scale(dotProduct);
        return v1.subtract(v2).normalize();
    }


    /**
     *
     * @return string describe the Tube
     */
    @Override
    public String toString() {
        return "Tube{" +
                "_axisRay=" + _axisRay +
                ", _radius=" + _radius +
                '}';
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray,double max) {
        ///TODO implement
        return null;
    }
}
