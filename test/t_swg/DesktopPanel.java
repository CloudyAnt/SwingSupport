package t_swg;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;

import pri.swg.Dragger;

@SuppressWarnings("serial")
public class DesktopPanel extends JFrame{
	public DesktopPanel() {
		setBounds(200, 200, 500, 500);
		setLayout(new GridLayout(1, 1));
		
		//�������
		JDesktopPane jd=new JDesktopPane();
		JLabel back=new JLabel(new ImageIcon(this.getClass().getResource("mention.png"))),
				back2=new JLabel(new ImageIcon(this.getClass().getResource("mention.png")));//��ȡ����ͼ ������JLabel��
		back2.setBounds(0, 0,200 ,200);				//���ñ���ͼ��С
		back2.setBounds(0, 0,400 ,200);				//���ñ���ͼ��С
		
		jd.add(back,new Integer(Integer.MIN_VALUE));//��ӱ���ͼ
		jd.add(back2,new Integer(Integer.MIN_VALUE));//��ӱ���ͼ
		//֮�󣬽�������嵱��һ��������þ��У�
		JButton b=new JButton("TEST");
		b.setBounds(0, 0, 100, 100);
		jd.add(b);
		//����
		
		
		jd.setBackground(Color.cyan);
		jd.setLayout(null);
		
//		URL ub=this.getClass().getResource("mention.png");
//		ImageIcon im=new ImageIcon(ub);
		
		
		
		
		
		
		JButton jb=new JButton("Test");
		jb.setBounds(100, 100, 100, 100);
		Dragger.drag(jb);
		
		jd.add(jb);
		
		add(jd);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	public static void main(String args[]) {
		new DesktopPanel();
	}
}
