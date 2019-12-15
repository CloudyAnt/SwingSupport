package test.t_swg;

import pri.swg.FadeFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class FadeFrameTest extends FadeFrame {
	private FadeFrameTest tf;

	private FadeFrameTest() {
		tf = this;
		set(0.2f, 1, 300, true, true);
		setBounds(200, 200, 400, 400);
		JButton away = new JButton("away");
		setLayout(new GridLayout(1, 2));
		add(away);

		away.addActionListener(e -> {
			tf.fadeOut();
			System.exit(0);
		});
		setVisible(true);
	}

	public static void main(String[] args) {
		new FadeFrameTest();
	}
}
