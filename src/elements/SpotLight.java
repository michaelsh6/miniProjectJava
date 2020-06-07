package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * SpotLight class Light display with single point of illumination
 * as well as attenuated intensity of light that increases the angle of impact
 */
public class SpotLight extends PointLight {
    Vector _direction;
    double _opening;

    /**
     * constructor
     * @param _intensity Color intensity
     * @param _poaition light poaition
     * @param _direction light direction
     * @param kC Constant coefficient
     * @param kL Linear coefficient
     * @param kQ Square coefficient
     * @param opening Opening angle coefficient
     */
    public SpotLight(Color _intensity, Point3D _poaition,Vector _direction, double kC, double kL, double kQ,double opening) {
        this(_intensity,_poaition,_direction, kC,  kL, kQ,opening,0);
    }


    /**
     * constructor
     * @param _intensity Color intensity
     * @param _poaition light poaition
     * @param _direction light direction
     * @param kC Constant coefficient
     * @param kL Linear coefficient
     * @param kQ Square coefficient
     * @param opening Opening angle coefficient
     * @param radius radius of the light (for soft shadow)
     */
    public SpotLight(Color _intensity, Point3D _poaition,Vector _direction, double kC, double kL, double kQ,double opening,double radius) {
        super(_intensity, _poaition, kC, kL, kQ,radius);
        this._direction = _direction.normalize();
        this._opening = opening;

    }


    /**
     * constructor
     * @param _intensity Color intensity
     * @param _poaition light poaition
     * @param _direction light direction
     * @param kC Constant coefficient
     * @param kL Linear coefficient
     * @param kQ Square coefficient
     */
    public SpotLight(Color _intensity, Point3D _poaition,Vector _direction, double kC, double kL, double kQ) {
        this(_intensity,_poaition,_direction, kC,  kL, kQ,1);
    }

    @Override
    public Color getIntensity(Point3D p) {
        double factor  = Math.pow(Math.max(0,_direction.dotProduct(getL(p))),_opening);
        return super.getIntensity(p).scale(factor);
    }

}
