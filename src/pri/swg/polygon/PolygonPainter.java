package pri.swg.polygon;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PolygonPainter extends JPanel {

    private int radius;
    private int centralX;
    private int centralY;
    private ArrayList<Polygon> polygons;

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D)g;

        for (Polygon p : polygons) {
            if (p.isDrawable()) {
                int corners = p.getCorners();
                int[] xs = p.getXs();
                int[] ys = p.getYs();
                for (int point = 0; point < corners; point++) {
                    int nextPoint = (point + 1 + p.getSkip()) % corners;
                    g2.setColor(p.getColor());
                    g2.setStroke(new BasicStroke(p.getStrokeWidth()));
                    g2.drawLine(xs[point], ys[point], xs[nextPoint], ys[nextPoint]);
                }
            }
        }
    }

    public PolygonPainter(int radius) {
        this.radius = this.centralX = this.centralY = radius;
        polygons = new ArrayList<>();
    }

    public void addPolygon(Polygon... polygons) {
        for (Polygon p : polygons) {
            addPolygon(p);
        }
    }

    public void addPolygon(Polygon polygon) {
        int corners = polygon.getCorners();
        int[] xs = new int[corners];
        int[] ys = new int[corners];

        double angle = Math.PI * 2 / corners;
        for (int i = 0; i < corners; i++) {
            xs[i] = centralX + (int)(Math.cos(angle * i - (Math.PI / 2)) * radius);
            ys[i] = centralY + (int)(Math.sin(angle * i - (Math.PI / 2)) * radius);
        }

        polygon.setXs(xs);
        polygon.setYs(ys);

        polygons.add(polygon);
    }

    public void clearPolygons() {
        this.polygons.clear();
    }

    public void draw() {
        this.repaint();
    }
}
