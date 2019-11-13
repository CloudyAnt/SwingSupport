package pri.swg;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.Border;
/**
 * TODO In creating
 * @author Shinelon
 *
 */
public class RoundBorder implements Border {
    private Color color;
 
    public RoundBorder(Color color) {// 有参数的构造方法
        this.color = color;
    }
    public RoundBorder() {// 无参构造方法
        this.color = Color.BLACK;
        // 如果实例化时，没有传值
        // 默认是黑色边框
    }
    public Insets getBorderInsets(Component c) {
        return new Insets(0, 0, 0, 0);
    }
    public boolean isBorderOpaque() {
        return false;
    }
    public void paintBorder(Component c, Graphics g, int x, int y, int width,int height) {
        g.setColor(color);
        g.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, 15, 15);
    }
}
