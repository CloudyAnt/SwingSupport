package test.t_swg;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import pri.swg.FadeFrame;

@SuppressWarnings("serial")
public class Test_FadeFrame extends FadeFrame {
	Test_FadeFrame tf;

	public Test_FadeFrame() {
		tf = this;
		set(0.2f, 1, 300, true, true);
		setBounds(200, 200, 400, 400);
		JButton show = new JButton("show"), away = new JButton("away");
		setLayout(new GridLayout(1, 2));
		add(show);
		add(away);
		show.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tf.fadeIn();
			}
		});
		away.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tf.fadeOut();
			}
		});
		setVisible(true);
	}

	public static void main(String args[]) {
		new Test_FadeFrame();
	}
}
