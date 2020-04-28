package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

/**
 * Camera class represents Camera in 3d coordinate system
 */
public class Camera {
    Point3D _p0;
    Vector _vTo;
    Vector _vUp;
    Vector _vRight;

    /**
     * constructor
     * @param _p0 location of the camera
     * @param _vTo Front camera orientation
     * @param _vUp Top camera orientation
     *
     * the constructor calculating the _vRight parameter
     */
    public Camera(Point3D _p0, Vector _vTo, Vector _vUp) {
        if(!Util.isZero(_vTo.dotProduct(_vUp))){
            throw new IllegalArgumentException("not orthogonal axis");
        }
        this._p0 = _p0;
        this._vTo = _vTo.normalize();
        this._vUp = _vUp.normalize();
        this._vRight = _vTo.crossProduct(_vUp);
    }

    /**
     * The function calculates the ray that exits given the photography data
     * @param nX num of Pixels in x axis
     * @param nY num of Pixels in y axis
     * @param j num of column pixel
     * @param i num of row pixel
     * @param screenDistance screen Distance rom the camera
     * @param screenWidth screen Width of the camera
     * @param screenHeight screen Height of the camera
     * @return The Ray coming out towards the pixel requested
     */
    public Ray constructRayThroughPixel (int nX, int nY,
                                         int j, int i, double screenDistance,
                                         double screenWidth, double screenHeight){
        double Rx = screenWidth/nX;
        double Ry = screenHeight/nY;
        double Xj = (j-nX/2d)*Rx + Rx/2d;
        double Yi = (i-nY/2d)*Ry + Ry/2d;
        if(Util.isZero(screenDistance)){
            throw new IllegalArgumentException("distance must be different from 0");
        }
        Point3D Pc = _p0.add(_vTo.scale(screenDistance));
        Point3D P_ij = Pc;
        if(!Util.isZero(Xj))
            P_ij = P_ij.add(_vRight.scale(Xj));
        if(!Util.isZero(Yi))
            P_ij = P_ij.add(_vUp.scale(-Yi));
        return new Ray(P_ij.subtract(_p0),_p0);
    }


    /**
     *
     * @return new p0
     */
    public Point3D get_p0() {
        return new Point3D(_p0) ;
    }

    /**
     *
     * @return new _vTo
     */
    public Vector get_vTo() {
        return new Vector(_vTo) ;
    }

    /**
     *
     * @return new _vUp
     */
    public Vector get_vUp() {
        return new Vector( _vUp);
    }

    /**
     *
     * @return new _vRight
     */
    public Vector get_vRight() {
        return new Vector( _vRight);
    }
}
