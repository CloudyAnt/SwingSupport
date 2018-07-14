package t_swg;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import pri.swg.Dragger;
import pri.swg.FadeFrame;
import pri.swg.Fader;
import pri.swg.Slider;
/**
 * SuiteA²âÊÔCOperator¡¢FadeFrame¡¢Slider¡¢Dragger
 * @author ²ñÏþ
 *
 */
public class Test_SuiteA extends FadeFrame{
	private static final long serialVersionUID = 1L;
	Test_SuiteA(){
		setBounds(300, 300, 300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(1, 1));
		JPanel jp=new JPanel();
		add(jp);
		Fader.fade(jp, Fader.BACK_GROUND, 1000,Fader.LISTEN_MouseEnter,new Color(0,255,0));
		Slider.d_break(jp, Slider.RIGHT, 200, 300, Slider.LISTEN_MouseEnter, 1f, 0.1f);
		setVisible(true);
		Dragger.drag(this, jp);
	}
	public static void main(String args[]){
		new Test_SuiteA();
	}
}
