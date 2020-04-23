package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * /TODO javadoc
 */
public class Camera {
    Point3D _p0;
    Vector _vTo;
    Vector _vUp;
    Vector _vRight;

    /**
     * TODO javadoc
     * @param _p0
     * @param _vTo
     * @param _vUp
     */
    public Camera(Point3D _p0, Vector _vTo, Vector _vUp) {
        //TODO implement
        this._p0 = _p0;
        this._vTo = _vTo;
        this._vUp = _vUp;
    }

    /**
     * TODO javadoc
     * @param nX
     * @param nY
     * @param j
     * @param i
     * @param screenDistance
     * @param screenWidth
     * @param screenHeight
     * @return
     */
    public Ray constructRayThroughPixel (int nX, int nY,
                                         int j, int i, double screenDistance,
                                         double screenWidth, double screenHeight){
        //TODO implement
        return null;
    }


    //TODO javadoc

    public Point3D get_p0() {
        return _p0;
    }

    public Vector get_vTo() {
        return _vTo;
    }

    public Vector get_vUp() {
        return _vUp;
    }

    public Vector get_vRight() {
        return _vRight;
    }
}
