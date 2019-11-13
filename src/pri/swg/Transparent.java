package pri.swg;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * 
 * 透明窗体
 * @author 柴家琪
 *
 */
@SuppressWarnings("serial")
public class Transparent extends JFrame{
	private Image background;
	private Transparent thisone;
	private Robot robot;
	JButton jb;
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2=(Graphics2D)g;
		if(background!=null)
			g2.drawImage(background, 0, 0, 500, 500, thisone);
//		jb.repaint();
	}
	public Transparent(){
		thisone=this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 500, 500);
		Dragger.drag(thisone);
		setUndecorated(true);
		setLayout(null);
		jb=new JButton();
		jb.setBounds(200, 200, 100, 100);
		add(jb);
		robot = null;
		try {
			robot=new Robot();
//			Toolkit tk=Toolkit.getDefaultToolkit();，
//			Dimension d=tk.getScreenSize();
			background=robot.createScreenCapture(new Rectangle(200, 200, 500, 500));
		} catch (AWTException e) {
			e.printStackTrace();
		}
		thisone.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e){
				Point l=thisone.getLocation();
				setOpacity(0.2f);
				background=robot.createScreenCapture(new Rectangle(l.x,l.y,500,500));
//				setOpacity(1);
				repaint();
			}
		});
//		HWND hWnd;
//		try {
//			User32.MessageBox(0,"A", "B", 0);
//			hWnd = User32.FindWindow("TXGuiFoundation", "QQ2010");
//			if(hWnd.getValue()>0){  
//            System.out.println("window exists");  
//            User32.SendMessage(hWnd, new UINT(0x10), new WPARAM(0), new LPARAM(0));  
//	        }else{  
//	            System.out.println("window doesn't exists");  
//	        }  
//		} catch (IllegalAccessException e1) {
//			e1.printStackTrace();
//		} catch (NativeException e1) {
//			e1.printStackTrace();
//		}  
        
		setVisible(true);
	}
	public static void main(String args[]){
		new Transparent();
	}
}
