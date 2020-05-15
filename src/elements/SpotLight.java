package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * TODO javaDoc
 */
public class SpotLight extends PointLight {
    Vector _direction;

    /**
     * TODO javaDoc
     * @param _intensity
     * @param _poaition
     * @param _direction
     * @param _kC
     * @param kL
     * @param kQ
     */
    public SpotLight(Color _intensity, Point3D _poaition,Vector _direction, double _kC, double kL, double kQ) {
        super(_intensity, _poaition, _kC, kL, kQ);
        this._direction = _direction;
    }

    //TODO ???????
}
