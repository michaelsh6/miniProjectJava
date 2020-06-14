package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * LightSource interface represent Light property
 */
public interface LightSource {
    /**
     * get color Intensity in the lighting point
     * @param p point of hit
     * @return Color Intensity
     */
    public Color getIntensity(Point3D p);

    /**
     * get direction of light
     * @param p point of hit
     * @return direction vector
     */
    public Vector getL(Point3D p);

    /**
     *
     * @param point
     * @return the distance between Point3D and the light source
     */
    public double getDistance(Point3D point);

    /**
     * light source has volume described by radius
     * @return Radius
     */
    public double getRadius();
}
