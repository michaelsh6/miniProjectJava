package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.*;

import static primitives.Util.*;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 *
 * @author Dan
 */
public class Polygon extends Geometry {
    /**
     * List of polygon's vertices
     */
    protected List<Point3D> _vertices;
    /**
     * Associated plane in which the polygon lays
     */
    protected Plane _plane;

    /**
     * constructor
     *
     * @param emission emission  color
     * @param material material object
     * @param vertices list of vertices
     */
    public Polygon(Color emission, Material material, Point3D... vertices) {
        super(emission, material);
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        _vertices = List.of(vertices);
        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        _plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (vertices.length == 3) return; // no need for more tests for a Triangle

        Vector n = _plane.getNormal();

        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with
        // the normal. If all the rest consequent edges will generate the same sign -
        // the
        // polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (int i = 1; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
        //bounding box
        for (Point3D point : _vertices) {
            this.boundingBoxMaxX = Math.max(this.boundingBoxMaxX, point.get_x().get());
            this.boundingBoxMaxY = Math.max(this.boundingBoxMaxY, point.get_y().get());
            this.boundingBoxMaxZ = Math.max(this.boundingBoxMaxZ, point.get_z().get());
            this.boundingBoxMinX = Math.min(this.boundingBoxMinX, point.get_x().get());
            this.boundingBoxMinY = Math.min(this.boundingBoxMinY, point.get_y().get());
            this.boundingBoxMinZ = Math.min(this.boundingBoxMinZ, point.get_z().get());
        }

    }

    /**
     * constructor
     *
     * @param emission emission  color
     * @param vertices list of vertices
     */
    public Polygon(Color emission, Point3D... vertices) {
        this(emission, new Material(0, 0, 0), vertices);
    }


    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     *
     * @param vertices list of vertices according to their order by edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex></li>
     *                                  </ul>
     */
    public Polygon(Point3D... vertices) {
        this(Color.BLACK, new Material(0, 0, 0), vertices);
    }

    @Override
    public Vector getNormal(Point3D point) {
        return _plane.getNormal();
    }

    /**
     * implements of Intersectable interface
     *
     * @param ray a ray to calculate the intersections
     * @param max max distance to calculate
     * @return list of intersections
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray, double max) {
        Point3D p0 = ray.get_tail();
        Vector v = ray.get_direction();
        Vector v1 = _vertices.get(0).subtract(p0);
        Vector v2 = _vertices.get(1).subtract(p0);
        boolean sign = Util.alignZero(v1.crossProduct(v2).dotProduct(v)) > 0;
        int size = _vertices.size();
        for (int i = 2; i <= size; i++) {
            v1 = v2;
            v2 = _vertices.get(i % size).subtract(p0);
            if (sign != alignZero(v1.crossProduct(v2).dotProduct(v)) > 0)
                return null;
        }

        List<GeoPoint> Intersections = _plane.findIntersections(ray, max);

        if (Intersections == null) {
            return null;
        }
        GeoPoint p = new GeoPoint(this, Intersections.get(0).getPoint());
        return new LinkedList<>() {{
            add(p);
        }};
    }

}

