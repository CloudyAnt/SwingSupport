package test.swg;

import pri.swg.Dragger;
import pri.swg.polygon.Polygon;
import pri.swg.polygon.PolygonPainter;

import javax.swing.*;
import java.awt.*;

public class PolygonerTest extends JFrame {
    public PolygonerTest(int length, PolygonPainter painter) {
        setBounds(200, 200, length, length);
        setLayout(new GridLayout(1, 1));
        setUndecorated(true);

        add(painter);

        Dragger.drag(this, painter);
        setVisible(true);
    }

    public static void main(String[] args) {
        PolygonPainter polygonPainter = new PolygonPainter(250);
        new PolygonerTest(500, polygonPainter);
        Polygon heptagram = new Polygon(7, 1, 1, Color.blue);
        Polygon octagram = new Polygon(8, 2, 2, Color.green);
        polygonPainter.addPolygon(heptagram, octagram);
        polygonPainter.draw();
    }
}
