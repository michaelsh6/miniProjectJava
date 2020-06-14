package primitives;

import java.lang.management.PlatformManagedObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Ray class represents a ray in 3d coordinate system
 */
public class Ray {
    Vector _direction;
    Point3D _tail;

    /**
     * DELTA is a double const value make up for deviation of saving Point3D at computer memory
     */
    private static final double DELTA = 0.01;


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

    /**
     * create list of rays start from the original ray location and direction by grid structure
     * @param radius the range of the ray direction angel
     * @param numOfSample num of Sample to return
     * @return list of ray
     */
    public List<Ray> BeamOfRay(double radius, int numOfSample){
        List<Ray> rayList = new LinkedList<>();
        rayList.add(this);
        if(Util.isZero(radius) | numOfSample<=1)
            return rayList;
        Vector x_axis = this._direction.getOrthogonal();
        Vector y_axis = this._direction.crossProduct(x_axis);
        int loopLength = (int)Math.sqrt(numOfSample);
        double factor = radius/loopLength;
        for (int i = 1; i <= loopLength; i++) {
            for (int j = 1; j <= loopLength; j++) {
                Vector current_x_axis = x_axis.scale(i*factor);
                Vector current_y_axis = y_axis.scale(j*factor);
                Vector currentVector = this._direction.add(current_x_axis).add(current_y_axis);
                rayList.add(new Ray(currentVector, this._tail));
            }
        }

        return rayList;
    }

}


//        double[] randomRadius = new Random().doubles(2*numOfSample, -radius, radius).distinct().toArray();
//        int reyListLen = randomRadius.length/2;
//        for (int i = 0; i < reyListLen; i++) {
//            Vector current_x_axis = x_axis.scale(randomRadius[i]);
//            Vector current_y_axis = y_axis.scale(randomRadius[reyListLen+i]);
//            Vector currentVector = this._direction.add(current_x_axis).add(current_y_axis);
//            rayList.add(new Ray(currentVector,this._tail));
//        }