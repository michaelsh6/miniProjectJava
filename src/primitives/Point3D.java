package primitives;

import java.util.Objects;

public class Point3D {
    Coordinate _x;
    Coordinate _y;
    Coordinate _z;

    public static final Point3D ZERO = new Point3D(0.0,0.0,0.0);

    public Point3D(Coordinate _x, Coordinate _y, Coordinate _z) {
        this._x = new Coordinate(_x);
        this._y = new Coordinate(_y);
        this._z = new Coordinate(_z);
    }

    public Point3D(Point3D other) {
        this._x = new Coordinate(other._x);
        this._y = new Coordinate(other._y);
        this._z = new Coordinate(other._z);
    }

    public Point3D(double _x, double _y, double _z) {
        this._x= new Coordinate(_x);
        this._y= new Coordinate(_y);
        this._z= new Coordinate(_z);

    }

    public Coordinate get_x() {
        return new Coordinate(_x);
    }

    public Coordinate get_y() {
        return new Coordinate(_y);
    }

    public Coordinate get_z() {
        return new Coordinate(_z);
    }

    public Vector subtract(Point3D p) {
        return new Vector(new
                Point3D(this._x._coord - p._x._coord,
                        this._y._coord - p._y._coord,
                        this._z._coord - p._z._coord));
    }


    public Point3D add(Vector p) {
        return new Point3D(this._x._coord + p.get_head()._x._coord,
                           this._y._coord + p.get_head()._y._coord,
                           this._z._coord + p.get_head()._z._coord);
    }

    public double distanceSquared(Point3D p) {
        double x = this._x._coord - p._x._coord;
        double y = this._y._coord - p._y._coord;
        double z = this._z._coord - p._z._coord;
        return x*x+y*y+z*z;
    }

    public double distance(Point3D p){
        return Math.sqrt(distanceSquared(p));
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return _x.equals(point3D._x) &&
               _y.equals(point3D._y) &&
               _z.equals(point3D._z);
    }

    @Override
    public String toString() {
        return "Point3D{" +
                "_x=" + _x +
                ", _y=" + _y +
                ", _z=" + _z +
                '}';
    }
}
