package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * TODO javaDoc
 */
public interface LightSource {
    /**
     * TODO javaDoc
     * @param p
     * @return
     */
    public Color getIntensity(Point3D p);

    /**
     * TODO javaDoc
     * @param p
     * @return
     */
    public Vector getL(Point3D p);

}
