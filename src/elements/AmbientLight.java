package elements;

import primitives.Color;


/**
 * the class represent Ambient Light of objects in scene
 */
public class AmbientLight {
    Color _IA;
    double _kA;
    Color _intensity;

    /**
     *constructor
     * @param IA Ambient Light Color
     * @param kA _intensity factor
     */
    public AmbientLight(Color IA, double kA) {
        _IA = IA;
        _kA = kA;
        _intensity = _IA.scale(_kA);
    }

    /**
     *
     * @return Color intensity
     */
    public Color getIntensity() {
        return _intensity;
    }
}
