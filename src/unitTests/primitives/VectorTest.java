package primitives;

import static org.junit.Assert.*;

import org.junit.Test;

public class VectorTest {



    @Test
    public void crossProduct() {

        Vector v1 = new Vector(1.0,2.0,2.0);
        Vector v2 = new Vector(1.0,2.0,3.0);
        Vector v3 = v1.crossProduct(v2);
        assertEquals("",v3, new Vector(-1.0,-1.0,0.0));
    }

    @Test
    public void dotProduct() {
    }

    @Test
    public void lengthSquared() {
    }

    @Test
    public void length() {
    }

    @Test
    public void add() {
    }

    @Test
    public void subtract() {
    }

    @Test
    public void scale() {
    }

    @Test
    public void normalize() {
    }

    @Test
    public void normalized() {
    }

}