package primitives;

import javax.print.attribute.standard.MediaSize;
import java.time.OffsetTime;
import java.util.Objects;

/**
 * Vector class represents 3d vector
 */
public class Vector {
    /**
     * point3d represent the vector
     */
    Point3D _head;

    /**
     * constractor
     * @param _head point3d
     */
    public Vector(Point3D _head) {
        if (_head.equals(Point3D.ZERO))
            throw new IllegalArgumentException("Zero vector exception");
        this._head = new Point3D(_head);
    }

    /**
     *copy constractor
     * @param other vector to copy
     */
    public Vector(Vector other) {
        this._head = new Point3D(other._head);
    }

    /**
     * get the verctor param in double type
     * @param _x x axis
     * @param _y y axis
     * @param _z z axis
     */
    public Vector(double _x, double _y, double _z) {
        this(new Point3D(_x, _y, _z));
    }

    /**
     *get the verctor param in Coordinate type
     *      * @param _x x axis
     *      * @param _y y axis
     *      * @param _z z axis
     */
    public Vector(Coordinate _x, Coordinate _y, Coordinate _z) {
        this(new Point3D(_x, _y, _z));
    }

    /**
     * if 2 vector is equals
     * @param o Second vector
     * @return boolean if equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return _head.equals(vector._head);
    }


    /**
     *
     * @return new Point3D of the vector
     */
    public Point3D get_head() {
        return new Point3D(_head);
    }

    /**
     * calculate cross Product between 2 vector
     * @param v Second vector
     * @return Vector after cross Product between 2 vector
     */
    public Vector crossProduct(Vector v) {
        double x = this._head.get_y().get() * v._head.get_z().get() - this._head.get_z().get() * v._head.get_y().get();
        double y = this._head.get_z().get() * v._head.get_x().get() - this._head.get_x().get() * v._head.get_z().get();
        double z = this._head.get_x().get() * v._head.get_y().get() - this._head.get_y().get() * v._head.get_x().get();
        return new Vector(x, y, z);
    }

    /**
     *calculate dot Product between 2 vector
     *      * @param v Second vector
     *      * @return double after dot Product between 2 vector
     */
    public double dotProduct(Vector v) {
        return this._head.get_x().get() * v._head.get_x().get() +
                this._head.get_y().get() * v._head.get_y().get() +
                this._head.get_z().get() * v._head.get_z().get();
    }

    /**
     *
     * @return length Squared of the vector
     */
    public double lengthSquared(){
        return this._head.distanceSquared(Point3D.ZERO);
    }

    /**
     *
     * @return length of the vector
     */
    public double length(){
        return Math.sqrt(lengthSquared());
    }

    /**
     * add 2 vector
     * @param p Second vector
     * @return new vector after add the 2 vectors
     */
    public Vector add(Vector p) {
        return new Vector(this._head.add(p));
    }

    /**
     subtract 2 vector
     * @param p Second vector
     * @return new vector after subtract the 2 vectors
     */
    public Vector subtract(Vector p) {
        return new Vector(this._head.subtract(p._head));
    }

    /**
     * scale the vector by scalar
     * @param num double how mach to scale
     * @return new vector after scakeing the vector
     */
    public Vector scale(double num){
        return new Vector(
                _head.get_x().get()*num,
                _head.get_y().get()*num,
                _head.get_z().get()*num);
    }

    /**
     * normalize the vector
     * @return the object itselp
     */
    public Vector normalize(){
        _head = this.normalized()._head;
        return this;
    }

    /**
     *create new normalize vector
     * @return the new normalize vector
     */
    public Vector normalized(){
        double factor = 1/this.length();
        return this.scale(factor);
    }

    /**
     *
     * @return string describe the vector
     */
    @Override
    public String toString() {
        return "Vector{" +
                "_head=" + _head +
                '}';
    }
}
