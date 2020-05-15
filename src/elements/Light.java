package elements;

import primitives.Color;

/**
 * TODO javaDoc
 */
public class Light {
    protected Color _intensity;

    /**
     * TODO javaDoc
     * @param _intensity
     */
    public Light(Color _intensity) {
        this._intensity = _intensity;
    }

    /**
     * TODO javaDoc
     * @return
     */
    public Color getIntensity() {
        return _intensity;
    }
}
