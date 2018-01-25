package pri.swg;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
/**
 * ���뵭������<hr>
 * ʵ�ִ���ĵ��뵭�����̳д��༴�ɡ�<br>
 * @author ����
 * @version 1.0 16/08/29
 * @version 1.2 17/08/09 ���ɳ���
 */
//�����ԣ� winXP�����ݺ�ֱ�ռ��44px��8px���߿���͸������win10�����ݺ�ֱ�ռ��49px��16px���߿���͸�����֣�
public class FadeFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private int fadeTime=200,intervals=10,times=0,turns=1;//times�ǵڼ������У�turns���ֵ��ڼ�������
	private float from=0.0f,to=1.0f,add=0.05f;
	private boolean goOn=true,in_with_show=true,close_after_out=true;
	private FadeFrame thisone;
	/**
	 * ���뵭�����壬Ĭ�Ͻ��ڴ�ʱ��������
	 */
	public FadeFrame(){
		thisone=this;
		setUndecorated(true);
		setOpacity(from);
		addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				if(times==0&&in_with_show)
					fadeIn();
				
			}
		});
	}
	/**
	 * ָ����ʼ͸���Ⱥ�����͸���ȣ��Լ����뵭��ʱ��
	 * @param from	��ʼ͸����
	 * @param to	�յ�͸����
	 * @param fadeTime ����ʱ��
	 * @param in_with_show �Ƿ��ڴ�ʱ����
	 */
	public void set(float from,float to,int fadeTime,boolean in_with_show,boolean close_after_out){
		setOpacity(from);
		this.from=from;
		this.to=to;
		this.fadeTime=fadeTime;
		this.in_with_show=in_with_show;
		this.close_after_out=close_after_out;
		add=(to-from)/(fadeTime/intervals);
	}
	/**
	 * ����
	 */
	public void fadeIn(){
		fade(true);
	}
	/**
	 * ����
	 */
	public void fadeOut(){
		fade(false);
	}
	private void fade(boolean isIn){
		Thread t=new Thread(new Runnable() {
			public void run() {
				times++;
				while(times<turns);
				float a=isIn?add:-add;
				int time=times;
				goOn=true;
				float opacity=thisone.getOpacity();
				while(goOn&&time==times){
					try {
						Thread.sleep(intervals);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					opacity+=a;
					if(isIn?opacity>=to:opacity<=from){
						if(isIn)
							opacity=to;
						else
							opacity=from;
						goOn=false;
					}
					setOpacity(opacity);
				}
				turns++;
				if(time==times&&!isIn&&close_after_out)//�˳��ر�
					thisone.setVisible(false);
			}
		});t.start();
	}
	/**
	 * 
	 * @param from ��ʼ͸����
	 * @param to ����͸����
	 * @param fadeTime ����ʱ��
	 */
	public void setFade(float from,float to,int fadeTime){
		this.from=from;
		this.to=to;
		this.fadeTime=fadeTime;
	}
	public int getFadeTime() {
		return fadeTime;
	}
	public void setFadeTime(int fadeTime) {
		this.fadeTime = fadeTime;
		add=(to-from)/(fadeTime/intervals);
	}
	public int getIntervals() {
		return intervals;
	}
	public void setIntervals(int intervals) {
		this.intervals = intervals;
		add=(to-from)/(fadeTime/intervals);
	}
	public float getFinalOpc() {
		return to;
	}
	public void setFinalOpc(float finalOpc) {
		this.to = finalOpc;
		add=(finalOpc-from)/(fadeTime/intervals);
	}
	public float getStartOpc() {
		return from;
	}
	public void setStartOpc(float startOpc) {
		this.from = startOpc;
		add=(to-startOpc)/(fadeTime/intervals);
	}
	public void setColse_after_out(boolean close){
		close_after_out=close;
	}
}
