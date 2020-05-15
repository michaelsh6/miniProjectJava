package elements;

import geometries.Intersectable.GeoPoint;
import geometries.Geometry;
import geometries.Polygon;
import org.junit.Test;
import geometries.Sphere;
import geometries.Plane;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.assertEquals;


public class CameraIntegrationTest {

    /**
     * for static testing of 3X3 pixels at 3X3 view planet
     */
    public int CountCameraAndGeometriesIntersectionTest(Geometry geometryShape){
        Camera camera = new Camera(new Point3D(0,0,0),new Vector(0,0,1), new Vector(0,-1,0));
        int distance = 1;
        int nX = 3;
        int nY = 3;
        int count = 0;
        for (int i=0; i<nX; i++)
        {
            for (int j=0; j<nY; j++)
            {
                Ray ray = camera.constructRayThroughPixel(nX, nY, j, i, distance, 3.0, 3.0 );
                List<GeoPoint> intersects = geometryShape.findIntersections(ray);
                count += intersects == null ? 0 : intersects.size();
            }
        }
        return count;
    }

    /**
     * test integration between sphere and camera rays
     */
    @Test
    public void TestConstructRayThroughPixelSphere(){

        // TC01 sphere is front of the view plane and only the center ray cross the sphere
        assertEquals("num of intersection is not correct TC01", 2,
                CountCameraAndGeometriesIntersectionTest(new Sphere(1d, new Point3D(0,0,3))));

        // TC02 all rays from camera cross the sphere twice
        assertEquals("num of intersection is not correct  TC02", 18,
                CountCameraAndGeometriesIntersectionTest(new Sphere(2.3, new Point3D(0,0,2.5))));

        // TC03 few rays from camera cross the sphere
        assertEquals("num of intersection is not correct  TC03", 10,
                CountCameraAndGeometriesIntersectionTest(new Sphere(1.5, new Point3D(0,0,2))));

        // TC04 camera is inside the sphere so all rays from camera cross the sphere once
        assertEquals("num of intersection is not correct", 9,
                CountCameraAndGeometriesIntersectionTest(new Sphere(4d, new Point3D(0,0,1))));

        // TC05 sphere is behind of the camera so no ray cross the sphere
        assertEquals("num of intersection is not correct", 0,
                CountCameraAndGeometriesIntersectionTest(new Sphere(0.5, new Point3D(0,0,-1))));
    }

    /**
     * test integration between plane and camera rays
     */
    @Test
    public void TestConstructRayThroughPixelPlane(){
        // TC01 plane is vertical to 'toward' camera vector
        assertEquals("num of intersection is not correct TC01", 9, CountCameraAndGeometriesIntersectionTest(new Plane
                (new Point3D(1,1,3),new Point3D(1,-1,3) ,new Point3D(0,0,3))));

        // TC02 plane is horizontal to 'toward' camera vector, but all rays cross the plane
        assertEquals("num of intersection is not correct TC02", 9, CountCameraAndGeometriesIntersectionTest(new Plane
                (new Point3D(1,1,2.5),new Point3D(-1,1,2.5) ,new Point3D(0,0,3))));

        //T03 plane is horizontal to 'toward' camera vector, and only few rays cross the plane (few are parallels)
        assertEquals("num of intersection is not correct TC03", 6, CountCameraAndGeometriesIntersectionTest(new Plane
                (new Point3D(1,1,1),new Point3D(-1,1,1) ,new Point3D(0,0,3))));

        // TC04 the plane is behind of the camera and parallel to rays in bottom, so no ray cross the plane
        assertEquals("num of intersection is not correct TC04", 0, CountCameraAndGeometriesIntersectionTest(new Plane
                (new Point3D(1,1,-2),new Point3D(-1,1,-2) ,new Point3D(0,0,-1.5))));
    }

    /**
     * test integration between Polygon and camera rays
     */
    @Test
    public void TestConstructRayThroughPixelSPolygon(){

        // TC01 small polygon one intersection point
        assertEquals("num of intersection is not correct", 1,CountCameraAndGeometriesIntersectionTest(new Polygon(
                new Point3D(0,-1,2), new Point3D(1,1,2) ,new Point3D(-1,1,2))) );

        // TC02 polygon has two intersection point
        assertEquals("num of intersection is not correct", 2, CountCameraAndGeometriesIntersectionTest(new Polygon(
                new Point3D(0,-20,2), new Point3D(1,1,2) ,new Point3D(-1,1,2))));

        // TC03 polygon has all intersection point
        assertEquals("num of intersection is not correct", 9, CountCameraAndGeometriesIntersectionTest(new Polygon(
                new Point3D(0,-20,2), new Point3D(4,4,2) ,new Point3D(-4,4,2))));

        // TC04 the polygon is behind of the camera so no ray cross the plane
        assertEquals("num of intersection is not correct", 0, CountCameraAndGeometriesIntersectionTest(new Plane
                (new Point3D(1,1,-2),new Point3D(-1,1,-2) ,new Point3D(0,0,-1.5))));
    }
}
