package primitives;

/**
 * TODO javaDoc
 */
public class Material {
    private double kD,kS;
    private int nShininess;

    /**
     * TODO javaDoc
     * @param kD
     * @param kS
     * @param nShininess
     */
    public Material(double kD, double kS, int nShininess) {
        this.kD = kD;
        this.kS = kS;
        this.nShininess = nShininess;
    }

    /**
     * TODO javaDoc
     * @return
     */
    public double getkD() {
        return kD;
    }


    /**
     * TODO javaDoc
     * @return
     */
    public double getkS() {
        return kS;
    }


    /**
     * TODO javaDoc
     * @return
     */
    public int getnShininess() {
        return nShininess;
    }
}
