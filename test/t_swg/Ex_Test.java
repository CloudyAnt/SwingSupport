package t_swg;

import javax.swing.JFrame;

import pri.swg.Dragger;

public class Ex_Test extends JFrame{
	private static final long serialVersionUID = 1L;
	Ex_Test(){
		setBounds(100,100,100,100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		Dragger.drag(this);
	}
	public static void main(String args[]){
		new Ex_Test();
	}
}
