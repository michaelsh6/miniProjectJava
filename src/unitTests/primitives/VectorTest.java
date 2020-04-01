package primitives;

import static org.junit.Assert.*;
import static primitives.Util.isZero;

import org.junit.Test;

public class VectorTest {

    /**
     * test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     * result of (v1 crossProduct v2) is a new vector (normal) that considered like this example:
     * v1 = (x1,y1,z1)
     * v2 = (x2,y2,z2)
     * result = (v1 X v2) = ((y1*z2-z1*y2), (z1*x2-x1*z2), (x1*y2-y1*x2))
     */
    @Test
    public void crossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);

        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v3);

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals("crossProduct() wrong result length", v1.length() * v3.length(), vr.length(), 0.00001);

        // Test cross-product result orthogonality to its operands
        assertTrue("crossProduct() result is not orthogonal to 1st operand", isZero(vr.dotProduct(v1)));
        assertTrue("crossProduct() result is not orthogonal to 2nd operand", isZero(vr.dotProduct(v3)));

        // =============== Boundary Values Tests ==================
        // test zero vector from cross-product of co-lined vectors
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {
        }
    }

    /**
     * test method for {@link primitives.Vector#dotProduct(Vector)}.
     * result of v1 dotProduct v2 is a double number that considered like this example:
     * v1 = (x1,y1,z1)
     * v2 = (x2,y2,z2)
     * result = (x1*x2 + y1*y2 + z1*z2)
     */
    @Test
    public void dotProduct() {
        Vector v1 = new Vector(1.0, 2.0, 2.0);
        Vector v2 = new Vector(1.0, 2.0, -1.0);
        Vector v3 = new Vector(0.0, -2.0, 2.0);

        Vector vr = new Vector(v3.crossProduct(v1));
        // ============ Equivalence Partitions Tests ==============

        // angle = 0 [test if v1 dotProduct v1 is equals to length^2]
        assertEquals("dotProduct() wrong result length", v1.dotProduct(v1), v1.lengthSquared(), 0.00001);
        // angle = 180
        assertEquals("dotProduct() wrong result length", v1.scale(-1).dotProduct(v1), v1.lengthSquared()*(-1), 0.00001);
        // angle = 90
        assertTrue("dotProduct() wrong result length", isZero(v1.dotProduct(v3)));
        // angle < 90 ->negative
        assertTrue("negative result failed",v3.dotProduct(v2) == -6);
        // angle > 90 ->positive
        assertTrue("negative result failed",v1.dotProduct(v2) == 3);
    }
    /**
     * test method for {@link Vector#lengthSquared()}.
     * lengthSquared is a square of vector as edge
     */
    @Test
    public void lengthSquared() {
        Vector v1 = new Vector(1.0, 2.0, 2.0);
        // simple consider:
        assertTrue("lengthSquared consider failed", v1.lengthSquared() == 9);
        // correlation with length() method
        assertEquals("there is no correlation with length() method", Math.pow(v1.length(), 2), v1.lengthSquared(), 0.00001);
    }

    /**
     * test method for {@link Vector#length()}.
     * result of v1 length is a double number that consider like this example:
     * v1 = (x,y,z)
     * result = (x*x + y*y + z*z)^0.5
     */
    @Test
    public void length() {
        Vector v1 = new Vector(1.0, 2.0, 2.0);
        // simple consider:
        assertTrue("length() consider failed", v1.length() == 3);
        // the angle between vector to himself is 0
        assertEquals("dotProduct() wrong result length", v1.dotProduct(v1), v1.length() * v1.length(), 0.00001);
    }


    /**
     * test method for {@link Vector#add(Vector)}.
     * result of v1 + v2 is a vector consider like this example:
     * v1 = (x1,y1,z1)
     * v2 = (x2,y2,z2)
     * result = (x1+x2 , y1+y2 , z1+z2)
     */
    @Test
    public void add() {
        Vector v1 = new Vector(1.0, 2.0, 2.0);
        Vector v2 = new Vector(1.0, 2.0, 3.0);
        Vector v3 = new Vector(v1.add(v2));
        // simple add act
        assertTrue("add() vectors failed",v3.equals(new Vector(2.0,4.0,5.0)));
        // correlation with subtract() method
        assertTrue("subtract of adding vector is not the first vector", v1.equals(v3.subtract(v2)));
        // add vector to himself it's same meaning of duplicate
        assertTrue("add() vectors failed",v1.add(v1).equals(v1.scale(2)));
    }

    /**
     * test method for {@link Vector#add(Vector)}.
     * result of v1 + v2 is a vector consider like this example:
     * v1 = (x1,y1,z1)
     * v2 = (x2,y2,z2)
     * result = (x1-x2 , y1-y2 , z1-z2)
     */
    @Test
    public void subtract() {
        Vector v1 = new Vector(1.0, 2.0, 2.0);
        Vector v2 = new Vector(1.0, 2.0, 3.0);
        Vector v3 = new Vector(v1.subtract(v2));

        // simple subtract act
        assertTrue("subtract() vectors failed",v3.equals(new Vector(0.0,0.0,-1.0)));
        // correlation with add() method
        assertTrue("subtract of adding vector is not the first vector", v1.equals(v3.add(v2)));

        // test zero vector from subtract vector from himself
        try {
            v1.subtract(v1);
            fail("subtract vector from himself create zero vector and no throed");
        } catch (Exception e) {
        }
    }

    /**
     * test method for {@link Vector#scale(double)}.
     * result of v1 scale a number n change the vector values like this example:
     * v1 = (x,y,z)
     * result = (n*x + n*y + n*z)
     */
    @Test
    public void scale() {
        Vector v1 = new Vector(1.0, 2.0, 2.0);

        // simple scale act
        assertTrue("scale() vector by number failed",v1.scale(3).equals(new Vector(3.0,6.0,6.0)));
       // scale by -1 twice not change the values
        assertTrue("wrong result scale", v1.scale(-1).scale(-1).equals(v1));
        try {
            v1 = v1.scale(0);
            fail("scale vector by zero does not throw an exception");
        }
        catch (Exception e) {
        }
    }


    /**
     * test method for {@link Vector#normalize()}
     * change the values of vector to became unit vector by same direct
     * the vector values after normalize will changed like this example:
     * v1 = (x,y,z)
     * len = v1.length()
     * result = (x/len + y/len + z/len)
     */
    @Test
    public void normalize() {
        Vector v1 = new Vector(1.0, 2.0, 2.0);
        Vector v2 = v1;
        double len = v1.length();
        Vector v3 = new Vector(v1.scale(1/len));

        //the method of normalize is change the very vector
        assertTrue("normalize() not change the very vector", v2 == v1.normalize() );
        // simple normalize act
        assertTrue("normalize() vector failed",v1.normalize().equals(v3));

    }
    /**
     * test method for {@link Vector#normalized()}.
     * return a new vector that values changed like normalize method.
     */
    @Test
    public void normalized() {
        Vector v1 = new Vector(1.0, 2.0, 2.0);
        Vector v2 = v1;
        double len = v1.length();
        Vector v3 = new Vector(v1.scale(1/len));

        //the method of normalize is change the very vector
        assertTrue("normalize()  change the very vector", v2 != v1.normalized() );
        // simple normalize act
        assertTrue("normalize() vector failed",v1.normalized().equals(v3));
    }

}