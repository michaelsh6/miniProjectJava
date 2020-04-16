package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;
/**
 * Testing Plane
 *
 */
public class PlaneTest {

    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Plane pl = new Plane(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals("Bad normal to triangle", new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)));
    }

    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    public void TestFindIntersections() {
        // ============ Equivalence Partitions Tests ==============

        Plane pl = new Plane(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));        // ============ Equivalence Partitions Tests ==============
        //TC01: the ray is parallel (0 point)
        Ray ray = new Ray(new Vector(-2,1,1).normalize(),new Point3D(2,3,4));
        List<Point3D> intersects = pl.findIntersections(ray);
        assertEquals("TC01: the ray is parallel error",intersects == null ? 0 : intersects.size(),0);

        //TC02: the ray is intersect plane (1 point)
        ray = new Ray(new Vector(1,1,1).normalize(),new Point3D(0,0,0));
        intersects = pl.findIntersections(ray);
        assertEquals("TC02:the ray is intersect plane error",intersects == null ? 0 : intersects.size(),1);


        // =============== Boundary Values Tests ==================
        //TC11: the ray is after the plane (0 point)
        ray = new Ray(new Vector(-1,-1,-1).normalize(),new Point3D(0,0,0));
        intersects = pl.findIntersections(ray);
        assertEquals("TC12: the ray is after the plane error",intersects == null ? 0 : intersects.size(),0);


    }
}