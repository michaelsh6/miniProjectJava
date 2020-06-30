package renderer;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import scene.Scene;



/**
 * Testing basic shadows
 *
 * @author Dan
 */
public class ShadowTests {

    public Polygon Square(Color color,Material material,Point3D center, double size){
        return new Polygon(color,material,
                center.add(new Vector(size,size,0)),
                center.add(new Vector(-size,size,0)),
                center.add(new Vector(-size,-size,0)),
                center.add(new Vector(size,-size,0))
        );
    }

    public Intersectable pattern(int numWithe, int numLen , double size){
        Color color1 = new Color(1,1,1).scale(80);
        Color color2 = new Color(1,1,1);
        Material material1 = new Material(0.5,0.5,0,0,0);
        Material material2 = new Material(0.2,0.8,30,0,0.8);
        Geometries geometries = new Geometries();
        for (int i = -numWithe;i<numWithe;i++){
            for (int j = -numLen;j<numLen ;j++){
                Material material = (i+j)%2 ==0 ?material1 :material2;
                Color color =(i+j)%2 ==0 ?color1 :color2;
                geometries.add(Square(color,material,new Point3D(i*size,j*size,0),size/2));
            }
        }
        return geometries;

    }

    @Test
    public  void testComplex_scene(){
        Scene scene = new Scene("Test scene");
        double cameraAngel = Math.toRadians(-3);
        scene.setCamera(new Camera(new Point3D(0, -15, 3), new Vector(0, Math.cos(cameraAngel), Math.sin(cameraAngel)), new Vector(0, -Math.sin(cameraAngel), Math.cos(cameraAngel))));
//        double cameraAngel = Math.toRadians(-90);
//        scene.setCamera(new Camera(new Point3D(0, -0, 20), new Vector(0, Math.cos(cameraAngel), Math.sin(cameraAngel)), new Vector(0, -Math.sin(cameraAngel), Math.cos(cameraAngel))));
        scene.setDistance(400);
        scene.setBackground(new Color(255,255,255).scale(0.2));
        scene.setAmbientLight(new AmbientLight(new Color(0,0,0), 0.15));
        Material materialPlane = new Material(1, 0, 0, 0, 0);
        scene.addGeometries(
                pattern(8,5,2),
//
//
                new Sphere(new Color(0,0,255),
                        new Material(0.5, 0.5, 20, 0, 0.9),2,
                        new Point3D(2.1,0,2.1)),
                new Sphere(new Color(100,0,0),
                        new Material(0.5, 0.5, 20, 0, 0.9),1,
                        new Point3D(0,-3,1.1)),
                new Sphere(new Color(173,255,47).scale(.4),
                        new Material(0.3, 0.5, 30, .0,.3 ),2.5,
                        new Point3D(-2.6,0,2.6)),

                new Sphere(new Color(255,215,0),
                        new Material(0.4, 0.4, 20, .6, 0),0.7,
                        new Point3D(-6,2,0.75))
        );

        scene.addLights(
        new PointLight(new Color(255, 255, 255).scale(0.5),
                new Point3D(20, 50, 10), 1, 4E-5, 2E-7,10),
        new DirectionalLight(new Color(255, 255, 204).scale(0.1),
                        new Vector(-1,-0.1,-0.3)),
        new SpotLight(
                new Color(255,255,200).scale(2),
                new Point3D(0,0,20),
                new Vector(0,0,-1),
                1,  4E-5, 2E-7,40, 5));


        ImageWriter imageWriter = new ImageWriter("testComplex_scene", 400, 300, 1440, 1080);
        Render render = new Render(imageWriter, scene).setMultithreading(4).setDebugPrint().setSoftSadow(100);

        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void flattenTest(){
        Geometries geometries = new Geometries(
           //     pattern(8,5,2),


                new Sphere(new Color(0,0,255),
                        new Material(0.5, 0.5, 20, 0, 0.9),2,
                        new Point3D(2.1,0,2.1)),
                new Sphere(new Color(100,0,0),
                        new Material(0.5, 0.5, 20, 0, 0.9),1,
                        new Point3D(0,-3,1.1)),
                new Sphere(new Color(173,255,47).scale(.4),
                        new Material(0.3, 0.5, 30, .0,.3 ),2.5,
                        new Point3D(-2.6,0,2.6)),

                new Sphere(new Color(255,215,0),
                        new Material(0.4, 0.4, 20, .6, 0),0.7,
                        new Point3D(-6,2,0.75))
        );
        geometries.findIntersections(new Ray(new Vector(1,0,0),new Point3D(0,0,0)));
}

    /**
     * Produce a picture of a sphere and triangle with point light and shade
     */
    @Test
    public void SoftShadowTest() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(800);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries( //
                new Polygon(Color.BLACK, new Material(0, 0.8, 60), //
                        new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150)), //
                new Triangle(Color.BLACK, new Material(0, 0.8, 60), //
                        new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 150)), //
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), // )
                        30, new Point3D(0, 0, 115)));

        scene.addLights(new SpotLight(new Color(700, 400, 400), //
                new Point3D(40, -40, -115), new Vector(-1, 1, 4), 1, 4E-4, 2E-5,1,100));

        ImageWriter imageWriter = new ImageWriter("SoftShadowTest", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene)
               // .setSoftSadow(100)
                .setMultithreading(3)
                .setDebugPrint();

        render.renderImage();
        render.writeToImage();
    }


    /**
     * Produce a picture of a sphere and triangle with point light and shade
     */
    @Test
    public void SphereTriangleInitial() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        60, new Point3D(0, 0, 200)), //
                new Triangle(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        new Point3D(-70, 40, 0), new Point3D(-40, 70, 0), new Point3D(-68, 68, 4)));

        scene.addLights(new SpotLight(new Color(400, 240, 0), //
                new Point3D(-100, 100, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7));

        ImageWriter imageWriter = new ImageWriter("sphereTriangleInitial", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Sphere-Triangle shading - move triangle up-right
     */
    @Test
    public void SphereTriangleMove1() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        60, new Point3D(0, 0, 200)), //
                new Triangle(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        new Point3D(-60, 30, 0), new Point3D(-30, 60, 0), new Point3D(-58, 58, 4)));

        scene.addLights(new SpotLight(new Color(400, 240, 0), //
                new Point3D(-100, 100, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7));

        ImageWriter imageWriter = new ImageWriter("sphereTriangleMove1", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Sphere-Triangle shading - move triangle upper-righter
     */
    @Test
    public void SphereTriangleMove2() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        60, new Point3D(0, 0, 200)), //
                new Triangle(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        new Point3D(-50, 20, 0), new Point3D(-20, 50, 0), new Point3D(-48, 48, 4)));

        scene.addLights(new SpotLight(new Color(400, 240, 0), //
                new Point3D(-100, 100, -200), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7));

        ImageWriter imageWriter = new ImageWriter("sphereTriangleMove2", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Sphere-Triangle shading - move spot closer
     */
    @Test
    public void SphereTriangleSpot1() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        60, new Point3D(0, 0, 200)), //
                new Triangle(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        new Point3D(-70, 40, 0), new Point3D(-40, 70, 0), new Point3D(-68, 68, 4)));

        scene.addLights(new SpotLight(new Color(400, 240, 0), //
                new Point3D(-90, 90, -180), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7));

        ImageWriter imageWriter = new ImageWriter("sphereTriangleSpot1", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Sphere-Triangle shading - move spot even more close
     */
    @Test
    public void SphereTriangleSpot2() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        60, new Point3D(0, 0, 200)), //
                new Triangle(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        new Point3D(-70, 40, 0), new Point3D(-40, 70, 0), new Point3D(-68, 68, 4)));

        scene.addLights(new SpotLight(new Color(400, 240, 0), //
                new Point3D(-80, 80, -90), new Vector(1, -1, 3), 1, 1E-5, 1.5E-7));

        ImageWriter imageWriter = new ImageWriter("sphereTriangleSpot2", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a Sphere producing a shading
     */
    @Test
    public void trianglesSphere() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries( //
                new Triangle(Color.BLACK, new Material(0, 0.8, 60), //
                        new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150)), //
                new Triangle(Color.BLACK, new Material(0, 0.8, 60), //
                        new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 150)), //
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), // )
                        30, new Point3D(0, 0, 115)));

        scene.addLights(new SpotLight(new Color(700, 400, 400), //
                new Point3D(40, -40, -115), new Vector(-1, 1, 4), 1, 4E-4, 2E-5));

        ImageWriter imageWriter = new ImageWriter("trianglesSphere", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

}
