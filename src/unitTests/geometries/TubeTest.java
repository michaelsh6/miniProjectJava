package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

/**
 * Testing Tube
 *
 */
public class TubeTest {

    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Ray ray = new Ray(new Vector(0,0,1),new Point3D(1,0,3));
        Tube tube = new Tube(2, ray) ;

        assertEquals("Bad normal to Tube",tube.getNormal(new Point3D(-1,0,6)) ,new Vector(-1,0,0));
    }

    @Test
    public void TestFindIntersections() {
        ///TODO implement
    }
}