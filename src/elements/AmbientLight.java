package elements;

import primitives.Color;


/**
 * the class represent Ambient Light of objects in scene
 */
public class AmbientLight extends Light{

    /**
     *constructor
     * @param IA Ambient Light Color
     * @param kA _intensity factor
     */
    public AmbientLight(Color IA, double kA) {
         super(IA.scale(kA));
    }

}
