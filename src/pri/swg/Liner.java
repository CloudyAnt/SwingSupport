package pri.swg;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * 横向、纵向颜色渐变<br/>
 * 不支持在绘制好的Panel上添加组件，仅用于装饰
 * 
 * @author 柴晓
 *
 */
public class Liner extends JPanel {
	private static final long serialVersionUID = 1L;
	private int height;
	private int width;
	private BufferedImage bufImg = null; // 在该BufferedImage对象中绘制颜色
	private Font font = new Font("微软雅黑", Font.BOLD, 14);
	private String text = "";
	private int x = 0, y = 0;
	private Color color = Color.black;
	public static final int Vertical = 1, Horizontal = 2, Left = 3, Right = 4, None = 5;
	private boolean horizontal;
	private int alignment = None;

	public Liner(int width, int height, int direction) {
		this.height = height;
		this.width = width;
		this.horizontal = direction == Horizontal;
		this.setPreferredSize(new Dimension(width, height));
		setBounds(0, 0, width, height);
	}

	public void draw(Color a, Color... colors) {
		bufImg = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);// 实例化BufferedImage
		Graphics g = bufImg.getGraphics();
		int singleWidth = horizontal ? width / colors.length : height / colors.length;
		float[] pre = getRGB(a);
		int p = 0;
		for (int i = 0; i < colors.length; i++) {
			float[] next = getRGB(colors[i]);
			float[] dev = new float[3];
			for (int x = 0; x < 3; x++) {
				dev[x] = (next[x] - pre[x]) / singleWidth;
			}
			for (int j = 0; j < singleWidth; j++) {
				int[] show = correct(pre);
				g.setColor(new Color(show[0], show[1], show[2]));
				if (horizontal)
					g.drawLine(p, 0, p, height);
				else
					g.drawLine(0, p, width, p);
				next = crease(pre, dev);
				p++;
			}
			pre = next;
		}
		g.setColor(colors[colors.length - 1]);
		if (horizontal) {
			while (p <= width) {
				g.drawLine(p, 0, p, height);
				p++;
			}
		} else {
			while (p <= height) {
				g.drawLine(0, p, width, p);
				p++;
			}
		}
		repaint();
	}

	public void setString(String string, int x, int y) {
		this.text = string;
		this.x = x;
		this.y = y;
	}

	public void setString(String string, Font font, Color color, int x, int y) {
		setString(string, x, y);
		this.font = font;
		this.color = color;
	}

	public void setString(String string, Font font, Color color, int alignment) {
		setHorizontalAlignment(alignment);
		setString(string, font, color, 0, (font.getSize() + height) / 2);
	}

	public void setText(String text) {
		this.text = text;
		repaint();
	}

	public void append(String text) {
		this.text += text;
		repaint();
	}

	public void setForeground(Color color) {
		this.color = color;
		repaint();
	}

	public Color getForeground() {
		return color;
	}

	public void setHorizontalAlignment(int alignment) {
		switch (alignment) {
		case Vertical:
		case Horizontal:
			this.alignment = None;
			break;
		default:
			this.alignment = alignment;
		}
	}

	private float[] getRGB(Color a) {
		return new float[] { a.getRed(), a.getGreen(), a.getBlue() };
	}

	private int[] correct(float[] pre) {
		int[] correct = new int[3];
		for (int i = 0; i < 3; i++) {
			if (pre[i] > 255)
				correct[i] = 255;
			else if (pre[i] < 0)
				correct[i] = 0;
			else
				correct[i] = (int) pre[i];
		}
		return correct;
	}

	private float[] crease(float[] pre, float[] dev) {
		for (int i = 0; i < 3; i++) {
			pre[i] += dev[i];
		}
		return pre;
	}

	public void paint(Graphics g) {
		g.drawImage(bufImg, 0, 0, null);
		int x;
		switch (alignment) {
		case Left:
			x = y - font.getSize();
			break;
		case Right:
			x = width - y + font.getSize();
			break;
		default:
			x = this.x;
		}
		if (!text.equals("")) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setFont(font);
			g2.setColor(color);
			g2.drawString(text, x, y);
		}

	}

	public String getText() {
		return text;
	}
}
