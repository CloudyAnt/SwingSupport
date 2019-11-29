package pri.swg.universal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.border.Border;

public class ULabel extends JLabel {
	private static final long serialVersionUID = 1L;
	private Border border = null;
	private Font font = new Font("微软雅黑", Font.BOLD, 16);
	private Color bg = Color.WHITE, fg = Color.BLACK;
	public static final int BACK_GROUND = 1, FORE_GROUND = 2;

	// private Fader bf=null,ff=null;
	public ULabel() {
		setSize(100, 30);
		setPreferredSize(new Dimension(100, 30));
		set();
	}

	public ULabel(int width, int height, Font font, Border border, Color bg, Color fg, int time, int type) {
		setPreferredSize(new Dimension(width, height));
		setSize(width, height);
		this.font = font;
		this.border = border;
		this.bg = bg;
		this.fg = fg;
		set();
	}

	private void set() {
		setOpaque(false);
		setBorder(border);
		setFocusable(false);
		setHorizontalAlignment(JLabel.CENTER);
		setFont(font);
		setBackground(bg);
		setForeground(fg);
	}
	// public void setFade(int place,Color color){
	// if(place==BACK_GROUND)
	// bg
	// }
}
