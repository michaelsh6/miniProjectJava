package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;

/**
 * Triangle class represents Triangle in 3d coordinate system
 */
public class Triangle extends Polygon {

    /**
     * constructor
     * @param emission emission  color
     * @param material material object
     * @param p1 point 1
     * @param p2 point 2
     * @param p3 point 3
     */
    public Triangle(Color emission, Material material, Point3D p1, Point3D p2, Point3D p3) {
        super(emission,material,p1,p2,p3);
    }

    /**
     * constructor
     * @param emission emission  color
     * @param p1 point 1
     * @param p2 point 2
     * @param p3 point 3
     */
    public Triangle(Color emission, Point3D p1, Point3D p2, Point3D p3) {
        super(emission,p1,p2,p3);
    }

    /**
     * constractor get 3 point of the Triangle
     * @param p1 point 1
     * @param p2 point 2
     * @param p3 point 3
     */
    public Triangle(Point3D p1,Point3D p2, Point3D p3) {
        super(p1,p2,p3);
    }
}
