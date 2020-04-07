package pri.swg.img;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImgPanel extends JPanel {
	private int x, y, width, height;
	private Image img;

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(img, x, y, width, height, this);
	}

	public ImgPanel(URL url, int x, int y, int width, int height) {
		this.img = Toolkit.getDefaultToolkit().getImage(url);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.setVisible(true);
	}
}
