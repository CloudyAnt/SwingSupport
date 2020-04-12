package pri.swg;

import java.awt.Color;
import java.awt.Graphics;
import java.net.URL;

import javax.swing.JFrame;

import pri.Resource;
import pri.swg.img.ImageBound;
import pri.swg.img.Imager;
import pri.util.MExecutor;

/**
 * SuiteB 主要测试 Imager、Painter
 * 
 * @author 柴晓
 *
 */
public class SuiteB extends JFrame {
	private Imager painter;
	private static final long serialVersionUID = 1L;

	private SuiteB() {
		setBounds(100, 50, 1000, 700);
		setLayout(null);
		this.getContentPane().setBackground(Color.lightGray);

		Resource resource = Resource.instance();
		URL url = resource.urlOf("Meitu_main.png");
		// 使图像在中心旋转需要提前知道面板边界
		ImageBound imageBound = new ImageBound(0, 0, 1000, 700);
		// Imager i=new Imager(this,url,bounds);
		// i.setDate(700, 700, 0, 0, 0, 0);
		// add(i);
		painter = new Imager(this, url, 360, 3000, imageBound);
		painter.setImgData(600, 600, 300, 310, 0, 10);
		add(painter);
		painter.start();
		// painter.setNotStop(true);
		painter.setBackground(new Color(255, 155, 0));
		Fader.fade(painter, Fader.BACK_GROUND, 1000, Fader.LISTEN_MouseEnter, Color.RED);
		Dragger.oneDirectionDrag(this, painter, Dragger.Vertical, 100, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void paint(Graphics g) {
		super.paint(g);
	}

	public static void main(String[] args) {
		new SuiteB();
		MExecutor.shutdown();
	}
}
