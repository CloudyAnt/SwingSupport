package test.t_swg;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

import pri.swg.Dragger;
//import pri.swg.img.Bounds;
//import pri.swg.img.Imager;
import pri.swg.img.ImgPanel;

@SuppressWarnings("serial")
public class Static_Image_Test extends JFrame{
	public Static_Image_Test() {
		setBounds(200, 200, 500, 500);
		setLayout(new GridLayout(1, 1));
		
		ImgPanel i=new ImgPanel(this.getClass().getResource("Meitu_main.png"),0,0,500,500);
//		Imager i=new Imager(this,this.getClass().getResource("Meitu_main.png"),new Bounds(0,0,500,500));
//		i.setImgData(500, 500, 0, 0, 0, 0);
		add(i);
		
		JButton jb=new JButton("Test");
		jb.setBounds(100, 100, 100, 100);
		Dragger.drag(jb);
		
		i.setLayout(null);
		i.add(jb);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		setVisible(true);
	}
	public static void main(String args[]) {
		new Static_Image_Test();
	}
}
