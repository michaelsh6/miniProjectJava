package geometries;

/**
 * RadialGeometry class represents all Radial Geometry objects
 */
public abstract class RadialGeometry implements Geometry{
    double _radius;

    /**
     * constractor
     * @param _radius get the radius of the object
     */
    public RadialGeometry(double _radius) {
        this._radius = _radius;
    }

    /**
     * copy constractor
     * @param radialGeometry object ot copy
     */
    public RadialGeometry(RadialGeometry radialGeometry) {
        this._radius = radialGeometry._radius;
    }

    /**
     * get function for the radius
     * @return radius
     */
    public double get_radius() {
        return _radius;
    }

    /**
     *
     * @return string describe the Radial object
     */
    @Override
    public String toString() {
        return "RadialGeometry{" +
                "_radius=" + _radius +
                '}';
    }
}
