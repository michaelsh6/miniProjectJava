package primitives;

import javax.print.attribute.standard.MediaSize;
import java.time.OffsetTime;
import java.util.Objects;

public class Vector {
    Point3D _head;

    public Vector(Point3D _head) {
        if (_head.equals(Point3D.ZERO))
            throw new IllegalArgumentException("Zero vector exception");
        this._head = new Point3D(_head);
    }

    public Vector(Vector other) {
        this._head = new Point3D(other._head);
    }

    public Vector(double _x, double _y, double _z) {
        this(new Point3D(_x, _y, _z));
    }

    public Vector(Coordinate _x, Coordinate _y, Coordinate _z) {
        this(new Point3D(_x, _y, _z));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return _head.equals(vector._head);
    }



    public Point3D get_head() {
        return new Point3D(_head);
    }

    public Vector crossProduct(Vector v) {
        double x = this._head.get_y().get() * v._head.get_z().get() - this._head.get_z().get() * v._head.get_y().get();
        double y = this._head.get_z().get() * v._head.get_x().get() - this._head.get_x().get() * v._head.get_z().get();
        double z = this._head.get_x().get() * v._head.get_y().get() - this._head.get_y().get() * v._head.get_x().get();
        return new Vector(x, y, z);
    }

    public double dotProduct(Vector v) {
        return this._head.get_x().get() * v._head.get_x().get() +
                this._head.get_y().get() * v._head.get_y().get() +
                this._head.get_z().get() * v._head.get_z().get();
    }

    public double lengthSquared(){
        return this._head.distanceSquared(Point3D.ZERO);
    }

    public double length(){
        return Math.sqrt(lengthSquared());
    }

    public Vector add(Vector p) {
        return new Vector(this._head.add(p));
    }

    public Vector subtract(Vector p) {
        return new Vector(this._head.subtract(p._head));
    }

    public Vector scale(double num){
        return new Vector(
                _head.get_x().get()*num,
                _head.get_y().get()*num,
                _head.get_z().get()*num);
    }

    public Vector normalize(){
        _head = this.normalized()._head;
        return this;
    }

    public Vector normalized(){
        double factor = 1/this.length();
        return this.scale(factor);
    }

    @Override
    public String toString() {
        return "Vector{" +
                "_head=" + _head +
                '}';
    }
}
