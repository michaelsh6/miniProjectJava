package renderer;

import elements.Camera;
import geometries.Intersectable;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;

import java.awt.*;
import java.util.List;

import static org.junit.Assert.*;

public class ImageWriterTest {

    //TODO
    @Test
    public void writeToImageTest() {
        ImageWriter _imageWriter = new ImageWriter("test",1600,1000,800,500);
        Color background = Color.BLUE;
        Color pixelColor = Color.WHITE;
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        for (int row = 0; row < nY; row++) {
            for (int column = 0; column < nX; column++){
                if (row%50 ==0 ||column%50==0){
                    _imageWriter.writePixel(column, row, pixelColor);
                }
                else {
                    _imageWriter.writePixel(column, row,background);
                }
            }
        }
        _imageWriter.writeToImage();
    }

}