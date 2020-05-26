package primitives;

/**
 * Material class represent material properties for lighting calculations
 */
public class Material {
    private double kD,kS;
    private int nShininess;

    /**
     * constructor
     * @param kD Diffuse coefficient
     * @param kS Specular coefficient
     * @param nShininess Shininess coefficient
     */
    public Material(double kD, double kS, int nShininess) {
        this.kD = kD;
        this.kS = kS;
        this.nShininess = nShininess;
    }

    /**
     * get function
     * @return kD
     */
    public double getkD() {
        return kD;
    }


    /**
     * get function
     * @return kS
     */
    public double getkS() {
        return kS;
    }


    /**
     * get function
     * @return nShininess
     */
    public int getnShininess() {
        return nShininess;
    }
}
