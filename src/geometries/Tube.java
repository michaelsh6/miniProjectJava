package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

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
        return null;
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
}
