package pri.swg;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
/**
 * ����������
 * @author Shinelon
 * SUPPOSE 1����ӹ̶���߶ȵ�������ڸ߶ȳ������Զ�������2����Ӳ�����ȵ�������Զ����С�3�����Կ�߲�����������
 * 
 * SUSPEND DEV. 
 * JSplitePane ��ʹ�� JScrollPane ���ɽ���������⡣ 
 */
@SuppressWarnings("serial")
public class Scoller extends JScrollPane{
	private int totalHeight=0;
	private Component[] cs;
	private int fixed_width,fixed_height;
	private JPanel panel;
	/**
	 * �̶����
	 * @param width		��
	 */
	public Scoller(int width,JPanel panel) {
		super(panel);
		fixed_width=width;
		this.panel=panel;
	}
	public Component add(Component c,int height) {
		totalHeight+=height;
		c.setPreferredSize(new Dimension(fixed_width,height));
//		if(totalHeight+10>fixed_height) {
//			this.ser
//		}
//		return sc_pane.add(c);
		return null;
	}
	/**
	 * ���ù������Ŀ��
	 */
//	public void setFixedSize(int width,int height) {
//		fixed_width=width;
//		fixed_height=height;
//	}
}
