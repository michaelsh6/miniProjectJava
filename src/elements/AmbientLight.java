package elements;

import primitives.Color;

public class AmbientLight {
    Color _IA;
    double _kA;
    Color _intensity;

    //constructor
    public AmbientLight(Color IA, double kA) {
        _IA = IA;
        _kA = kA;
        _intensity = _IA.scale(_kA);
    }

    public Color getIntensity() {
        return _intensity;
    }
}
