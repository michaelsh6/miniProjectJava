package geometries;

import primitives.Color;
import primitives.Material;

/**
 * RadialGeometry class represents all Radial Geometry objects
 */
public abstract class RadialGeometry extends Geometry{
    double _radius;

    /**
     * TODO javaDoc
     * @param emission
     * @param material
     * @param _radius
     */
    public RadialGeometry(Color emission, Material material, double _radius) {
        super(emission,material);
        this._radius = _radius;
    }

    /**
     * TODO javaDoc
     * @param _emission
     * @param _radius
     */
    public RadialGeometry(Color _emission, double _radius) {
        this(_emission,new Material(0,0,0),_radius);
    }

    /**
     * constractor
     * @param _radius get the radius of the object
     */
    public RadialGeometry( double _radius) {
        this(Color.BLACK,new Material(0,0,0),_radius);
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
