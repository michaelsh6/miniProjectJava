package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static org.junit.Assert.*;


public class GeometriesTest {

    /**
     * Test method for {@link geometries.Geometries#findIntersections(primitives.Ray)}
     */
    @Test
    public void findIntersections() {
        Geometries geometries_collection;
        // =============== Boundary Values Tests ==================
        geometries_collection = new Geometries();

        //TC0: empty collection
        Ray ray = new Ray(new Vector(0, 0, 1), new Point3D(4, 4, 0));
        List<GeoPoint> geometries_intersections = geometries_collection.findIntersections(ray);
        assertEquals("no shapes at all", 0,geometries_intersections == null ? 0 : geometries_intersections.size());



        Triangle triangle1 = new Triangle(new Point3D(4,6,0),
                            new Point3D(2,2,0),
                            new Point3D(6,2,0));
        Sphere sphere1 = new Sphere(1,new Point3D(4,4,2));
        Polygon polygon1 = new Polygon(new Point3D(2,6,4),
                                       new Point3D(2,2,4),
                                       new Point3D(6,2,4),
                                       new Point3D(6,6,4));
        geometries_collection = new Geometries(triangle1,sphere1,polygon1);

        // TC1: any shape intersection
        ray = new Ray(new Vector(0, 0, -1), new Point3D(4, 4, -1));
        geometries_intersections = geometries_collection.findIntersections(ray);
        assertEquals("any shape intersection", 0,geometries_intersections == null ? 0 : geometries_intersections.size());

        //TC2: only one shape intersect
        ray = new Ray(new Vector(0, 0, 1), new Point3D(4, 4, 3.5));
        geometries_intersections = geometries_collection.findIntersections(ray);
        assertEquals("only one shape intersect", 1,geometries_intersections == null ? 0 : geometries_intersections.size());

        // TC3: all shapes intersects
        ray = new Ray(new Vector(0, 0, 1), new Point3D(4, 4, -1));
        geometries_intersections = geometries_collection.findIntersections(ray);
        assertEquals("all shapes intersectsl", 4,geometries_intersections == null ? 0 : geometries_intersections.size());

        // ============ Equivalence Partitions Tests ==============

        //TC11: few shapes intersections
        ray = new Ray(new Vector(0, 0, 1), new Point3D(4, 4, 0.5));
        geometries_intersections = geometries_collection.findIntersections(ray);
        assertEquals("few shapes intersections", 3,geometries_intersections == null ? 0 : geometries_intersections.size());

    }
}