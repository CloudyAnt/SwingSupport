package pri.swg;

import pri.util.MExecutor;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

/**
 * 淡入淡出窗体
 * <hr>
 * 实现窗体的淡入淡出，继承此类即可。<br>
 * 
 * @author 柴晓
 * @version 1.0 16/08/29
 * @version 1.2 17/08/09 修缮程序
 */
// winXP窗体纵横分别占用44px、8px（边框无透明），win10窗体纵横分别占用49px、16px（边框有透明部分）
public class FadeFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private int fadeTime = 200, intervals = 10, times = 0, turns = 1;// times是第几次运行，turns是轮到第几次运行
	private float from = 0.0f, to = 1.0f, add = 0.05f;
	private boolean goOn = true, in_with_show = true, close_after_out = true;
	private FadeFrame thisone;

	/**
	 * 淡入淡出窗体，默认将在打开时启动淡入
	 */
	public FadeFrame() {
		thisone = this;
		setUndecorated(true);
		setOpacity(from);
		addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				if (times == 0 && in_with_show)
					fadeIn();

			}
		});
	}

	/**
	 * 指定起始透明度和最终透明度，以及淡入淡出时间
	 * 
	 * @param from         起始透明度
	 * @param to           终点透明度
	 * @param fadeTime     淡化时间
	 * @param in_with_show 是否在打开时淡入
	 */
	public void set(float from, float to, int fadeTime, boolean in_with_show, boolean close_after_out) {
		setOpacity(from);
		this.from = from;
		this.to = to;
		this.fadeTime = fadeTime;
		this.in_with_show = in_with_show;
		this.close_after_out = close_after_out;
		add = (to - from) / (fadeTime / intervals);
	}

	/**
	 * 淡入
	 */
	public void fadeIn() {
		fade(true);
	}

	/**
	 * 淡出
	 */
	public void fadeOut() {
		fade(false);
	}

	private void fade(boolean isIn) {
		MExecutor.execute(() -> {
			times++;
			while (times < turns)
				;
			float a = isIn ? add : -add;
			int time = times;
			goOn = true;
			float opacity = thisone.getOpacity();
			while (goOn && time == times) {
				try {
					Thread.sleep(intervals);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				opacity += a;
				if (isIn ? opacity >= to : opacity <= from) {
					if (isIn)
						opacity = to;
					else
						opacity = from;
					goOn = false;
				}
				setOpacity(opacity);
			}
			turns++;
			if (time == times && !isIn && close_after_out)// 退出关闭
				thisone.setVisible(false);
		});
	}

	/**
	 * 
	 * @param from     起始透明度
	 * @param to       最终透明度
	 * @param fadeTime 淡化时间
	 */
	public void setFade(float from, float to, int fadeTime) {
		this.from = from;
		this.to = to;
		this.fadeTime = fadeTime;
	}

	public int getFadeTime() {
		return fadeTime;
	}

	public void setFadeTime(int fadeTime) {
		this.fadeTime = fadeTime;
		add = (to - from) / (fadeTime / intervals);
	}

	public int getIntervals() {
		return intervals;
	}

	public void setIntervals(int intervals) {
		this.intervals = intervals;
		add = (to - from) / (fadeTime / intervals);
	}

	public float getFinalOpc() {
		return to;
	}

	public void setFinalOpc(float finalOpc) {
		this.to = finalOpc;
		add = (finalOpc - from) / (fadeTime / intervals);
	}

	public float getStartOpc() {
		return from;
	}

	public void setStartOpc(float startOpc) {
		this.from = startOpc;
		add = (to - startOpc) / (fadeTime / intervals);
	}

	public void setColse_after_out(boolean close) {
		close_after_out = close;
	}
}
