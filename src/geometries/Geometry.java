package geometries;

import primitives.Point3D;
import primitives.Vector;
import primitives.Color;
import primitives.Material;
/**
 *  Geometry abstract class represents all Geometrys 3d object
 */
public abstract class Geometry implements Intersectable{

    protected Color _emission;
    protected Material _material;


    /**
     * @return emission color
     */
    public Color get_emission() {
        return _emission;
    }

    /**
     *
     * @return material object
     */
    public Material get_material() {
        return _material;
    }

    /**
     * constructor
     * @param _emmission emission color
     */
    public Geometry(Color _emmission) {
        this(_emmission,new Material(0,0,0));

    }

    /**
     * constructor
     * @param _emission emission color
     * @param _material material object
     */
    public Geometry(Color _emission, Material _material) {
        this._emission = _emission;
        this._material = _material;
    }

    /**
     *default constructor
     */
    public Geometry() {
        this(Color.BLACK,new Material(0,0,0));
    }

    /**
     *functoin to implement for calculating the normal for 3d object In reference to 3d point
     * @param point  3d point
     * @return normalize vector
     */
    public abstract Vector getNormal(Point3D point);


}
