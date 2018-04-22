package pri.swg;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * Swing�������������<hr>
 * <h1>3����ĩ�˼��پ�̬����</h1>
 * slide(����),direct(���򶨳�),relat(����)��<br>
 * <h1>3����ĩ�˼��پ�̬����:</h1>
 * s_break,d_break,r_break����Ӧ����3��������������ָ������ĩ�˼��١�
 * <h1>ÿ����̬�����ж���activity��������Ӧ����:</h1>
 * <li>NULL:��������ִ�У����������</li>
 * <li>NULL_BACK:��������ִ�У�������ԭ��</li>
 * <li>RETURN:�������ض���</li>
 * <li>LISTEN_MouseEnter:����������������Ƴ�</li>
 * <li>LISTEN_MousePress:����������갴���ɿ�</li>
 * <h1>���Ϸ�����ֹд���Ƶ�ʼ�����</h1>
 * <h1>����ɵ��õ�start��stop��goOn��goBack��������д�����</h1>
 * <h1>������setter��getter�������û��ȡÿ����ʱ��time����ˢ�¼����intervals/ms��</h1><hr>
 * @author ����
 * @version 2.0 ����޸ġ��Ż�1.x�еĳ�Ա��ʹ������ʵ��ĩ�˼��ٵȷ��� 16/08/27
 * @version 2.1 �Ż�6����̬�����������޸����޷���ִ��bug 16/09/01
 * @version 2.2 ���ɲ��ֳ������Ӳ���getter��setterӦ�Ը������� 16/09/02
 */
public class Slider {
	public final static int TOP=1,BOTTOM=2,LEFT=3,RIGHT=4,NULL=5,NULL_BACK=6,RETURN=7,
			LISTEN_MouseEnter=8,LISTEN_MousePress=9;
	private Component relative,c;
	private Point start,finish;
	private float xSpeed,ySpeed,xBreakLevel=1,yBreakLevel=1,breakX=0,breakY=0;
	private int intervals=10,times=0;
	//on ���� needback һ����ִ�в����� Going ����ִ�� inRelative ����ڹ�������� inCom��ִ�������
	private boolean on=true,needBack=false,Going=false,inRelative=false,inCom=false;
	//��֧����ִ��ָ��activity
	private void branch(int branch){
		if(branch==NULL)
			doSlide(start,finish);
		else if(branch==NULL_BACK){
			needBack=true;
			doSlide(start,finish);
		}
		else if(branch>=LISTEN_MouseEnter)
			addListen(branch);
	}
	//�����ӹ���
	private Slider(int branch,Component relative,Component c,Point finish,int xTime,int yTime){
		this.c=c;
		this.relative=relative;
		this.start=c.getLocation();
		this.finish=finish;
		getSpeed(xTime, yTime);
		branch(branch);
	}
	/**
	 * ���㻬�����޼��٣�
	 * @see Slider
	 * @param c ��������
	 * @param finish �յ�
	 * @param xTime ������ʱ
	 * @param yTime ������ʱ
	 * @param activity ���񣨾���μ���ע�ͣ�
	 * @return ��activityΪRETURNʱ����Slider����
	 */
	public static Slider slide(Component c,Point finish,int xTime,int yTime,int activity){
		return deal(activity,null,c,finish,xTime,yTime);
	}
	/**
	 * ���㻬����ĩ�˼��٣�
	 * @see Slider
	 * @param c ��������
	 * @param finish �յ�
	 * @param xTime ������ʱ��΢�룩
	 * @param yTime ������ʱ��΢�룩
	 * @param activity ���񣨾���μ���ע�ͣ�
	 * @param break_percent ĩ�˼��ٵľ��루��С����ʾ�ٷֱ�[0.0f,1.0f]��
	 * @param fSpeed_percent ĩ�˼��ٵ������ٶȣ���С����ʾ�ٷֱ�(0.0f,1.0f]��
	 * @return ��activityΪRETURNʱ����Slider����
	 */
	public static Slider s_break(Component c,Point finish,int xTime,int yTime,int activity,float break_percent,float fSpeed_percent){
		Slider slide=slide(c,finish,xTime,yTime,RETURN);
		return breakDeal(slide,activity,break_percent,fSpeed_percent);
	}
	/**
	 * ���򻬶����޼��٣�
	 * @see Slider
	 * @param c ��������
	 * @param direction �������� TOP|LEFT|BOTTOM|RIGHT
	 * @param px ��������
	 * @param time ����ʱ�䣨΢�룩
	 * @param activity ���񣨾���μ���ע�ͣ�
	 * @return ��activityΪRETURNʱ����Slider����
	 */
	public static Slider direct(Component c,int direction,int px,int time,int activity){
		Point finish=getFinish(c,direction,px);
		return deal(activity,null,c,finish,time,time);
	}
	/**
	 * ���򻬶���ĩ�˼��٣�
	 * @see Slider
	 * @param c ��������
	 * @param direction �������� TOP|LEFT|BOTTOM|RIGHT
	 * @param px ��������
	 * @param time ����ʱ�䣨΢�룩
	 * @param activity ���񣨾���μ���ע�ͣ�
	 * @param break_percent ĩ�˼��ٵľ��루��С����ʾ�ٷֱ�[0.0f,1.0f]��
	 * @param fSpeed_percent ĩ�˼��ٵ������ٶȣ���С����ʾ�ٷֱ�(0.0f,1.0f]��
	 * @return ��activityΪRETURNʱ����Slider����
	 */
	public static Slider d_break(Component c,int direction,int px,int time,int activity,float break_percent,float fSpeed_percent){
		Slider slide=direct(c,direction,px,time,RETURN);
		return breakDeal(slide,activity,break_percent,fSpeed_percent);
	}
	/**
	 * �����������޼��٣�
	 * @see Slider
	 * @param relative ��Ե����
	 * @param c ��������
	 * @param direction ��������
	 * @param time ����ʱ�䣨΢�룩
	 * @param autoLocate ������Ե��Զ���λ
	 * @param activity ���񣨾���μ���ע�ͣ�
	 * @return ��activityΪRETURNʱ����Slider����
	 */
	public static Slider relat(Component relative,Component c,int direction,int time,boolean autoLocate,int activity){
		Point finish=getFinish_autoLoacte(relative, c, direction, autoLocate);
		return deal(activity,relative,c,finish,time,time);
	}
	/**
	 * ����������ĩ�˼��٣�
	 * @see Slider
	 * @param relative ���������
	 * @param c ��������
	 * @param direction ��������
	 * @param time ����ʱ�䣨΢�룩
	 * @param autoLocate ���ݹ�������Զ���λ
	 * @param activity ���񣨾���μ���ע�ͣ�
	 * @param break_percent ĩ�˼��ٵľ��루��С����ʾ�ٷֱ�[0.0f,1.0f]��
	 * @param fSpeed_percent ĩ�˼��ٵ������ٶȣ���С����ʾ�ٷֱ�(0.0f,1.0f]��
	 * @return ��activityΪRETURNʱ����Slider����
	 */
	public static Slider r_break(Component relative,Component c,int direction,int time,boolean autoLocate,int activity,float break_percent,float fSpeed_percent){
		Slider slide=relat(relative,c,direction,time,autoLocate,RETURN);
		return breakDeal(slide,activity,break_percent,fSpeed_percent);
	}
	//��ͨ������
	private static Slider deal(int activity,Component relative,Component c,Point finish,int xTime,int yTime){
		Slider slider=new Slider(activity,relative,c,finish,xTime,yTime);
		if(activity==RETURN)
			return slider;
		return null;
	}
	//�����һ��������ʵ��ĩ�˼���
	private static Slider breakDeal(Slider slide,int activity,float break_percent,float fSpeed_percent){
		slide.setBreak(break_percent, fSpeed_percent);
		if(activity==RETURN)
			return slide;
		slide.branch(activity);
		return null;
	}
	//������ʼ���ȡ˫����ٶȣ�ÿintervalǰ��px��
	private void getSpeed(int xTime,int yTime){
		xSpeed=Math.abs((float)(finish.x-start.x)/(xTime/intervals));
		ySpeed=Math.abs((float)(finish.y-start.y)/(yTime/intervals));
	}
	//���򻬶���ȡ�յ�
	private static Point getFinish(Component c,int direction,int px){
		int X=c.getX(),Y=c.getY();
		if(direction==LEFT)
			return new Point(X-px,Y);
		else if(direction==TOP)
			return new Point(X,Y-px);
		else if(direction==RIGHT)
			return new Point(X+px,Y);
		else if(direction==BOTTOM)
			return new Point(X,Y+px);
		return new Point(0,0);
	}
	//����������ȡ�յ㡣�Զ���λ
	private static Point getFinish_autoLoacte(Component relative,Component c,int direction,boolean autoLocate){
		Point p=new Point(0,0);
		int X=relative.getX(),Y=relative.getY(),W=relative.getWidth(),H=relative.getHeight(),cW=c.getWidth(),cH=c.getHeight();
		System.out.println(relative.getWidth()+" "+relative.getHeight());
		if((W==0&&H==0)){
			Dimension preferred=relative.getPreferredSize();
			System.out.println(preferred);
			W=preferred.width;
			H=preferred.height;
			//TODO �޸ĵ㣺���������󳤿�Ϊ0��������ʹ������Դ�С���������������ڲ��ⲿ���֡�
		}
		if(direction==LEFT){
			if(autoLocate)
				c.setLocation(X, Y+(H-cH)/2);
			p=new Point(X-cW,Y+(H-cH)/2);
		}
		else if(direction==TOP){
			if(autoLocate)
				c.setLocation(X+(W-cW)/2, Y);
			p=new Point(X+(W-cW)/2,Y-cH);
		}
		else if(direction==RIGHT){
			if(autoLocate)
				c.setLocation(X+W-cW, Y+(H-cH)/2);
			p=new Point(X+W,Y+(H-cH)/2);
		}
		else if(direction==BOTTOM){
			if(autoLocate)
				c.setLocation(X+(W-cW)/2, Y+H-cH);
			p=new Point(X+(W-cW)/2,Y+H);
		}
		return p;
	}
	/**
	 * ��Ӽ���
	 * @param listenType �������� LISTEN_MouseEnter��LISTEN_MousePress
	 */
	public void addListen(int listenType){
		if(listenType==LISTEN_MouseEnter){
			if(relative!=null){
				relative.addMouseListener(new MouseAdapter() {
					public void mouseExited(MouseEvent e) {
						inRelative=false;
						if(!inCom)
							goBack();
					}
					public void mouseEntered(MouseEvent e) {
						inRelative=true;
						if(!Going)
							goOn();
					}
				});
			}
			c.addMouseListener(new MouseAdapter() {
				public void mouseExited(MouseEvent e) {
					inCom=false;
					if(!inRelative)
						goBack();
				}
				public void mouseEntered(MouseEvent e) {
					inCom=true;
					if(!Going)
						goOn();
				}
			});
		}
		else if(listenType==LISTEN_MousePress){
			if(relative!=null){
				relative.addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent e){
						goOn();
					}
					public void mouseReleased(MouseEvent e){
						goBack();
					}
				});
			}
			c.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e){
					goOn();
				}
				public void mouseReleased(MouseEvent e){
					goBack();
				}
			});
		}
	}
	/**
	 * ���ĩ�˼���
	 * @param break_percent ĩ�˼��ټ��پ��루��С����ʾ�ٷֱȣ�
	 * @param fSpeed_percent ĩ�˼��������ٶȣ���С����ʾ�ٷֱȣ�
	 */
	public void setBreak(float break_percent,float fSpeed_percent){
		breakX=Math.abs(((finish.x-start.x)*break_percent));
		breakY=Math.abs(((finish.y-start.y)*break_percent));
		xBreakLevel=1/fSpeed_percent;
		yBreakLevel=1/fSpeed_percent;
	}
	public void start(){
		doSlide(start,finish);
	}
	private void start(Point start,Point finish){
		times++;
		doSlide(start, finish);
	}
	public void stop(){
		on=false;
	}
	/**
	 * ��������ִ��
	 */
	public void goOn(){
		Going=true;
		start(c.getLocation(),finish);
	}
	/**
	 * ��������ִ��
	 */
	public void goBack(){
		Going=false;
		start(c.getLocation(),start);
	}
	
	/**
	 * �߳�
	 * @param start ��ʼ��
	 * @param finish �յ�
	 */
	private void doSlide(final Point start,final Point finish){
		new Thread(()-> { 
			int time=times;
			int xDir=start.x<=finish.x?1:-1;
			int yDir=start.y<=finish.y?1:-1;
			float[]present={start.x,start.y};
			
			on=true;
			boolean xFinish=false,yFinish=false;
			while(on&&time==times){
				try {
					Thread.sleep(intervals);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(xFinish&&yFinish){
					
					on=false;
					c.setLocation(finish);
				}
				else{
					c.setLocation((int)present[0],(int)present[1]);
					if(!xFinish){
						float distence=Math.abs(finish.x-present[0]);
						if(distence>=breakX)
							present[0]+=xSpeed*xDir;
						else
							present[0]+=present(distence,true)*xDir;
						xFinish=xDir==1?present[0]>=finish.x:present[0]<=finish.x;
					}
					if(!yFinish){
						present[1]+=ySpeed*yDir;
						float distence=Math.abs(finish.y-present[1]);
						if(distence>=breakY)
							present[1]+=ySpeed*yDir;
						else
							present[1]+=present(distence,false)*yDir;
						yFinish=yDir==1?present[1]>=finish.y:present[1]<=finish.y;
					}
				}
			}
			if(needBack){
				goBack();
				needBack=false;
			}
		}).start();
	};
	//ĩ�˼���
	private float present(float distence,boolean isX){
		float speed = 0;
		if(isX)
			speed=((xBreakLevel-1)*xSpeed*distence)/(xBreakLevel*breakX)+xSpeed/xBreakLevel;
		else
			speed=((yBreakLevel-1)*ySpeed*distence)/(yBreakLevel*breakY)+ySpeed/yBreakLevel;
		return speed;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	public int getIntervals() {
		return intervals;
	}
	public void setIntervals(int intervals){
		this.intervals=intervals;
	}
	public Point getStart() {
		return start;
	}
	public void setStart(Point start) {
		this.start = start;
	}
	public Point getFinish() {
		return finish;
	}
	//���û����Ľ�β�����·���Ӧ����λ�ñ�������
	public void setFinish(int direction,boolean autoLocate) {
		finish = getFinish_autoLoacte(relative, c, direction, autoLocate);
	}
	public void setFinish(int direction,int px){
		finish = getFinish(c,direction,px);
	}
	public void setFinish(Point finish) {
		this.finish = finish;
	}
	public boolean isGoing() {
		return Going;
	}
}