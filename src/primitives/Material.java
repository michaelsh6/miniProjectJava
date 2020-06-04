package primitives;

/**
 * Material class represent material properties for lighting calculations
 */
public class Material {
    private double _kD, _kS,_kT,_kR;
    private int nShininess;

    /**
     * constructor
     * @param kD Diffuse coefficient
     * @param kS Specular coefficient
     * @param nShininess Shininess coefficient
     * @param kT transparency coefficient
     * @param kR reflection coefficient
     */
    public Material(double kD, double kS, int nShininess,double kT,double kR) {
        this._kD = kD;
        this._kS = kS;
        this._kR = kR;
        this._kT = kT;
        this.nShininess = nShininess;
    }

    /**
     * constructor
     * @param kD Diffuse coefficient
     * @param kS Specular coefficient
     * @param nShininess Shininess coefficient
     */
    public Material(double kD, double kS, int nShininess){
        this(kD,kS,nShininess,0,0);
    }

    /**
     * get function
     * @return kD
     */
    public double getkD() {
        return _kD;
    }


    /**
     * get function
     * @return kS
     */
    public double getkS() {
        return _kS;
    }


    /**
     * get function
     * @return nShininess
     */
    public int getnShininess() {
        return nShininess;
    }

    /**
     * get function
     * @return transparency coefficient
     */
    public double getkT() {
        return _kT;
    }
    /**
     * get function
     * @return reflection coefficient
     */
    public double getkR() {
        return _kR;
    }
}
