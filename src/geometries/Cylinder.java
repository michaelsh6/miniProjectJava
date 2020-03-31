package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Cylinder is finite Tube in 3d coordinate system
 */
public class Cylinder extends Tube {

    double _height;

    /**
     *constructor
     * @param _radius the radius of the Cylinder
     * @param _axisRay the direction and base of the Cylinder (ray object)
     * @param _height  height to calculate ceiling of the Cylinder
     */
    public Cylinder(double _radius, Ray _axisRay, double _height) {
        super(_radius, _axisRay);
        this._height = _height;
    }

    /**
     *get function
     * @return height
     */
    public double get_height() {
        return _height;
    }

    /**
     *implements of Geometry interface
     * @param point 3d point to calculate the normal
     * @return normal
     */
    public Vector getNormal(Point3D point) {
        return super.getNormal(null);
    }

    /**
     *
     * @return string describe the Cylinder
     */
    @Override
    public String toString() {
        return "Cylinder{" +
                "_height=" + _height +
                ", _axisRay=" + _axisRay +
                ", _radius=" + _radius +
                '}';
    }
}
