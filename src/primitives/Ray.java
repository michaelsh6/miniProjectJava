package primitives;

import java.util.Objects;

public class Ray {
    Vector _direction;
    Point3D _tail;

    public Ray(Vector _direction, Point3D _tail) {
        this._direction = _direction.normalized();
        this._tail = _tail;
    }

    public Ray(Ray ray) {
        this._direction = new Vector(ray._direction);
        this._tail = new Point3D(_tail);
    }

    public Vector get_direction() {
        return new Vector(_direction);
    }

    public Point3D get_tail() {
        return new Point3D(_tail);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return _direction.equals(ray._direction) &&
               _tail.equals(ray._tail);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "_direction=" + _direction +
                ", _tail=" + _tail +
                '}';
    }

}
