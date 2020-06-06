package primitives;

import java.lang.management.PlatformManagedObject;
import java.util.Objects;

/**
 * Ray class represents a ray in 3d coordinate system
 */
public class Ray {
    Vector _direction;
    Point3D _tail;

    /**
     * DELTA is a double const value make up for deviation of saving Point3D at computer memory
     */
    private static final double DELTA = 0.1;


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
     *constructor
     * @param direction the direction of the ray
     * @param tail the starting point of the ray
     * @param normal for fixing system inaccurate calculation
     */
    public Ray(Vector direction, Point3D tail, Vector normal) {
        Vector deltaVector = normal.scale(normal.dotProduct(direction) > 0 ? DELTA: -DELTA);
        this._tail = tail.add(deltaVector);
        this._direction = direction.normalize();
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

    public Point3D getPoint(double t){
        return this.get_tail().add(this.get_direction().scale(t));
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
