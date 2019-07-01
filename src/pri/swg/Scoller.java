package pri.swg;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
/**
 * 组件滚动面板
 * @author Shinelon
 * SUPPOSE 1、添加固定宽高度的组件，在高度超出后自动滚动。2、添加不定宽度的组件，自动换行。3、尝试宽高不定的面板添加
 * 
 * SUSPEND DEV. 
 * JSplitePane 中使用 JScrollPane 即可解决上述问题。 
 */
@SuppressWarnings("serial")
public class Scoller extends JScrollPane{
	private int totalHeight=0;
	private Component[] cs;
	private int fixed_width,fixed_height;
	private JPanel panel;
	/**
	 * 固定宽高
	 * @param width		宽
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
	 * 设置滚动面板的宽高
	 */
//	public void setFixedSize(int width,int height) {
//		fixed_width=width;
//		fixed_height=height;
//	}
}
