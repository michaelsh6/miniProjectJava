package primitives;

import java.util.Objects;

/**
 * Point3D class represents point in 3D coordinate system
 */
public class Point3D {

    /**
     *  Coordinate for 3d point
     */
    Coordinate _x;
    Coordinate _y;
    Coordinate _z;


    public static final Point3D ZERO = new Point3D(0.0,0.0,0.0);

    /**
     * constructor to get Coordinate objects
     * @param _x x axis
     * @param _y y axis
     * @param _z z axis
     */
    public Point3D(Coordinate _x, Coordinate _y, Coordinate _z) {
        this._x = new Coordinate(_x);
        this._y = new Coordinate(_y);
        this._z = new Coordinate(_z);
    }

    /**
     *copy constructor
     * @param other  vector to copy
     */
    public Point3D(Point3D other) {
        this._x = new Coordinate(other._x);
        this._y = new Coordinate(other._y);
        this._z = new Coordinate(other._z);
    }

    /**
     * constructor to get double type
     * @param _x x double
     * @param _y y double
     * @param _z z double
     */
    public Point3D(double _x, double _y, double _z) {
        this._x= new Coordinate(_x);
        this._y= new Coordinate(_y);
        this._z= new Coordinate(_z);

    }

    /**
     *
     * @return Coordinate object for x axis
     */
    public Coordinate get_x() {
        return new Coordinate(_x);
    }

    /**
     *
     * @return Coordinate object for y axis
     */
    public Coordinate get_y() {
        return new Coordinate(_y);
    }

    /**
     *
     * @return Coordinate object for z axis
     */
    public Coordinate get_z() {
        return new Coordinate(_z);
    }

    /**
     * subtract 2 point and get vector
     * @param p Second point to subtract
     * @return new vector
     */
    public Vector subtract(Point3D p) {
        return new Vector(new
                Point3D(this._x._coord - p._x._coord,
                        this._y._coord - p._y._coord,
                        this._z._coord - p._z._coord));
    }

    /**
     * add point to vector
     * @param p vector to add
     * @return new point after adding
     */
    public Point3D add(Vector p) {
        return new Point3D(this._x._coord + p.get_head()._x._coord,
                           this._y._coord + p.get_head()._y._coord,
                           this._z._coord + p.get_head()._z._coord);
    }

    /**
     * get the distance Squared between 2 point
     * @param p Second point
     * @return double the distance Squared between 2 point
     */
    public double distanceSquared(Point3D p) {
        double x = this._x._coord - p._x._coord;
        double y = this._y._coord - p._y._coord;
        double z = this._z._coord - p._z._coord;
        return x*x+y*y+z*z;
    }

    /**
     * get the distance between 2 point
     * @param p  Second point
     * @return double the distance between 2 point
     */
    public double distance(Point3D p){
        return Math.sqrt(distanceSquared(p));
    }


    /**
     * if 2 point3D is equals
     * @param o Second point
     * @return boolean if equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return _x.equals(point3D._x) &&
               _y.equals(point3D._y) &&
               _z.equals(point3D._z);
    }

    /**
     *
     * @return string describe the point3D
     */
    @Override
    public String toString() {
        return "(" +
                "x: " + _x +
                ",y: " + _y +
                ",z: " + _z +
                ')';
    }
}
