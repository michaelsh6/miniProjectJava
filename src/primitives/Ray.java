package primitives;

import java.util.Objects;

/**
 * Ray class represents a ray in 3d coordinate system
 */
public class Ray {
    Vector _direction;
    Point3D _tail;

    /**
     *constructor
     * @param _direction the direction of the ray
     * @param _tail the starting point of the ray
     */
    public Ray(Vector _direction, Point3D _tail) {
        this._direction = _direction.normalized();
        this._tail = _tail;
    }

    /**
     *copy constractor
     * @param ray ray to copy
     */
    public Ray(Ray ray) {
        this._direction = new Vector(ray._direction);
        this._tail = new Point3D(ray._tail);
    }

    /**
     *
     * @return get the direction of the ray(vector)
     */
    public Vector get_direction() {
        return new Vector(_direction);
    }

    /**
     *
     * @return get  starting point of the ray(point3D)
     */
    public Point3D get_tail() {
        return new Point3D(_tail);
    }

    /**
     * if 2 rays is equals
     * @param o Second ray
     * @return boolean if equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return _direction.equals(ray._direction) &&
               _tail.equals(ray._tail);
    }

    /**
     *
     * @return string describe the ray
     */
    @Override
    public String toString() {
        return "Ray{" +
                "_direction=" + _direction +
                ", _tail=" + _tail +
                '}';
    }

}
