package pri.swg.polygon;

import java.awt.*;

public class Polygon {
    private int corners;
    private int[] xs;
    private int[] ys;
    private Color color;
    private int skip = 0;
    private int strokeWidth = 1;
    private boolean drawable = false;

    public Polygon(int corners) {
        this(corners, 0, 1, Color.black);
    }

    public Polygon(int corners, int skip, int strokeWidth, Color color) {
        this.corners = corners;
        if (corners < 3) {
            outputErr("Corners mustn't smaller than 3. Received: " + corners);
            return;
        }

        this.skip = skip;
        float skipLimitation = ((float)corners - 2) / 2;
        if (skip < 0 || skip >= skipLimitation) {
            outputErr("Skip " + skip + " for corners " + corners + " is illegal. The limitation of skip is [0, (corners - 2) / 2). ");
            return;
        }

        if (strokeWidth < 1) {
            outputErr("Stroke width must bigger than 0. Received: " + strokeWidth);
            return;
        }

        this.strokeWidth = strokeWidth;
        this.color = color;

        xs = new int[corners];
        ys = new int[corners];

        drawable = true;
    }

    private void outputErr(String err) {
        System.err.println(this.getClass().getName() + ": " + err);
    }

    public Color getColor() {
        return color;
    }

    public int getCorners() {
        return corners;
    }

    public int getSkip() {
        return skip;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public int[] getXs() {
        return xs;
    }

    public int[] getYs() {
        return ys;
    }

    public void setYs(int[] ys) {
        this.ys = ys;
    }

    public void setXs(int[] xs) {
        this.xs = xs;
    }

    public boolean isDrawable() {
        return drawable;
    }
}
