package test.swg;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;

import pri.swg.Fader;
import pri.swg.Liner;

public class OtherTest extends JFrame {
	private static final long serialVersionUID = 1L;
	Liner l;

	public OtherTest() {
		setBounds(100, 100, 400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		l = new Liner(400, 100, Liner.Horizontal);
		l.draw(Color.white, Color.black, Color.white);
		l.setString("测试一下", new Font("微软雅黑", Font.BOLD, 20), Color.cyan, Liner.Left);
		Fader.fade(l, Fader.FORE_GROUND, 200, Fader.LISTEN_MouseEnter, Color.MAGENTA, Color.YELLOW);
		add(l);
		setVisible(true);
	}

	public static void main(String args[]) {
		new OtherTest();
	}
}
