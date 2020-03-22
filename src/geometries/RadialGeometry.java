package geometries;

public abstract class RadialGeometry implements Geometry{
    double _radius;

    public RadialGeometry(double _radius) {
        this._radius = _radius;
    }

    public RadialGeometry(RadialGeometry radialGeometry) {
        this._radius = radialGeometry._radius;
    }

    public double get_radius() {
        return _radius;
    }

    @Override
    public String toString() {
        return "RadialGeometry{" +
                "_radius=" + _radius +
                '}';
    }
}
