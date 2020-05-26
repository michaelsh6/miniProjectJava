package elements;

import primitives.Color;

/**
 * Light abstract class represents light object with intensity color property
 */
public abstract class Light {
    protected Color _intensity;

    /**
     * constructor
     * @param _intensity intensity color
     */
    public Light(Color _intensity) {
        this._intensity = _intensity;
    }

    /**
     * get function
     * @return intensity color
     */
    public Color getIntensity() {
        return _intensity;
    }
}
