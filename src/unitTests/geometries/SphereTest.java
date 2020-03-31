package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;
/**
 * Testing Sphere
 *
 */
public class SphereTest {

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Sphere sphere = new Sphere(2.0,new Point3D(1,1,1));
        assertEquals("Bad normal to Sphere",sphere.getNormal(new Point3D(1,1,3)) ,new Vector(0,0,1));






    }
}