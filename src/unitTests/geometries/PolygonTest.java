/**
 *
 */
//package unitTests;
package geometries;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.*;
import primitives.*;

import java.util.List;

/**
 * Testing Polygons
 * @author Dan
 *
 */
public class PolygonTest {

    /**
     * Test method for
     * {@link geometries.Polygon #Polygon(primitives.Point3D, primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(0, 1, 0),
                    new Point3D(1, 0, 0), new Point3D(-1, 1, 1));
            fail("Constructed a polygon with wrong order of vertices");
        } catch (IllegalArgumentException e) {}

        // TC03: Not in the same plane
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 2, 2));
            fail("Constructed a polygon with vertices that are not in the same plane");
        } catch (IllegalArgumentException e) {}

        // TC04: Concave quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0.5, 0.25, 0.5));
            fail("Constructed a concave polygon");
        } catch (IllegalArgumentException e) {}

        // =============== Boundary Values Tests ==================

        // TC10: Vertix on a side of a quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0.5, 0.5));
            fail("Constructed a polygon with vertix on a side");
        } catch (IllegalArgumentException e) {}

        // TC11: Last point = first point
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0, 1));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {}

        // TC12: Collocated points
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 1, 0));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {}

    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Polygon pl = new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0),
                new Point3D(-1, 1, 1));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals("Bad normal to trinagle", new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)));

    }
    /**
     * Test method for {@link geometries.Polygon#findIntersections(primitives.Ray)}.
     */
    @Test
    public void TestFindIntersections() {
        Polygon pl = new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0),
                new Point3D(-1, 1, 1));        // ============ Equivalence Partitions Tests ==============
        //TC01: the ray is parallel (0 point)
        Ray ray = new Ray(new Vector(-2,1,1).normalize(),new Point3D(2,3,4));
        List<Point3D> intersects = pl.findIntersections(ray);
        assertEquals("TC01: the ray is parallel error",intersects == null ? 0 : intersects.size(),0);


        ///3 case the ray is intersect the plane
        //TC02: the ray is intersect inside the Polygon (1 point)
        ray = new Ray(new Vector(1,1,1).normalize(),new Point3D(0,0,0));
        intersects = pl.findIntersections(ray);
        assertEquals("TC02: the ray is intersect inside the Polygon error",intersects == null ? 0 : intersects.size(),1);

        //TC03: the ray is intersect the plane but not ray 1 (0 point)
        ray = new Ray(new Vector(1,1,1).normalize(),new Point3D(0,-1,0));
        intersects = pl.findIntersections(ray);
        assertEquals("TC03: the ray is intersect the plane but not ray 1 error",intersects == null ? 0 : intersects.size(),0);

        //TC04: the ray is intersect the plane but not ray 2 (0 point)
        ray = new Ray(new Vector(1,1,1).normalize(),new Point3D(0,-1,-1.1));
        intersects = pl.findIntersections(ray);
        assertEquals("TC04: the ray is intersect the plane but not ray 2 error",intersects == null ? 0 : intersects.size(),0);


        // =============== Boundary Values Tests ==================

        ///3 case the ray is after the plane
        //TC12: the ray is after the plane inside the Polygon (0 point)
        ray = new Ray(new Vector(-1,-1,-1).normalize(),new Point3D(0,0,0));
        intersects = pl.findIntersections(ray);
        assertEquals("TC12: the ray is after the plane inside the Polygon error",intersects == null ? 0 : intersects.size(),0);

        //TC13: the ray is after the plane ray 1 (0 point)
        ray = new Ray(new Vector(-1,-1,-1).normalize(),new Point3D(0,-1,0));
        intersects = pl.findIntersections(ray);
        assertEquals("TC13: the ray is after the plane ray 1 error",intersects == null ? 0 : intersects.size(),0);

        //TC14: the ray is after the plane ray 2 (0 point)
        ray = new Ray(new Vector(-1,-1,-1).normalize(),new Point3D(0,-1,-1.1));
        intersects = pl.findIntersections(ray);
        assertEquals("TC14: the ray is after the plane ray 2 error",intersects == null ? 0 : intersects.size(),0);


        ///3 case the ray is intersect the plane special case
        //TC15: the ray is intersect vertecx of the Polygon (1 point)
        ray = new Ray(new Vector(0,0,1),new Point3D(0,0,0));
        intersects = pl.findIntersections(ray);
        assertEquals("TC15: the ray is intersect vertecx of the Polygon error",intersects == null ? 0 : intersects.size(),0);

        //TC16: the ray is intersect edge of the Polygon (0 point)
        ray = new Ray(new Vector(1,0,0.5).normalize(),new Point3D(0,0,0));
        intersects = pl.findIntersections(ray);
        assertEquals("TC16: the ray is intersect edge of the Polygon error",intersects == null ? 0 : intersects.size(),0);

        //TC17: the ray is intersect with the continuing line of polygon edge (0 point)
        ray = new Ray(new Vector(1,0,-0.5).normalize(),new Point3D(0,0,0));
        intersects = pl.findIntersections(ray);
        assertEquals("TC17: the ray is intersect with the continuing line of polygon edge error",intersects == null ? 0 : intersects.size(),0);


        ///3 case the ray is after the plane special case
        //TC18: the ray is intersect vertecx of the Polygon (1 point)
        ray = new Ray(new Vector(0,0,-1),new Point3D(0,0,0));
        intersects = pl.findIntersections(ray);
        assertEquals("TC18: the ray is intersect vertecx of the Polygon after the plane error",intersects == null ? 0 : intersects.size(),0);

        //TC19: the ray is intersect edge of the Polygon (0 point)
        ray = new Ray(new Vector(-1,0,-0.5).normalize(),new Point3D(0,0,0));
        intersects = pl.findIntersections(ray);
        assertEquals("TC19: the ray is intersect edge of the Polygon after the plane error",intersects == null ? 0 : intersects.size(),0);

        //TC20: the ray is intersect with the continuing line of polygon edge (0 point)
        ray = new Ray(new Vector(-1,0,0.5).normalize(),new Point3D(0,0,0));
        intersects = pl.findIntersections(ray);
        assertEquals("TC20: the ray is intersect with the continuing line of polygon edge after the plane error",intersects == null ? 0 : intersects.size(),0);


    }
}
