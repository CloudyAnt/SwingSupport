package pri.swg;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * Swing组件滑动控制类<hr>
 * <h1>3个无末端减速静态方法</h1>
 * slide(定点),direct(定向定长),relat(关联)。<br>
 * <h1>3个有末端减速静态方法:</h1>
 * s_break,d_break,r_break。对应以上3个方法，并根据指定参数末端减速。
 * <h1>每个静态方法中都有activity参数，对应操作:</h1>
 * <li>NULL:——立刻执行，无特殊操作</li>
 * <li>NULL_BACK:——立刻执行，并返回原点</li>
 * <li>RETURN:——返回对象</li>
 * <li>LISTEN_MouseEnter:——监听鼠标移入移出</li>
 * <li>LISTEN_MousePress:——监听鼠标按下松开</li>
 * <h1>以上方法禁止写入高频率监听中</h1>
 * <h1>对象可调用的start、stop、goOn、goBack方法允许写入监听</h1>
 * <h1>对象用setter、getter方法设置或获取每次用时（time）、刷新间隔（intervals/ms）</h1><hr>
 * @author 柴晓
 * @version 2.0 大幅修改、优化1.x中的成员，使操作灵活。实现末端减速等方法 16/08/27
 * @version 2.1 优化6个静态公开方法、修复无限反向执行bug 16/09/01
 * @version 2.2 修缮部分程序。增加部分getter、setter应对更多情形 16/09/02
 */
public class Slider {
	public final static int TOP=1,BOTTOM=2,LEFT=3,RIGHT=4,NULL=5,NULL_BACK=6,RETURN=7,
			LISTEN_MouseEnter=8,LISTEN_MousePress=9;
	private Component relative,c;
	private Point start,finish;
	private float xSpeed,ySpeed,xBreakLevel=1,yBreakLevel=1,breakX=0,breakY=0;
	private int intervals=10,times=0;
	//on 启动 needback 一次性执行并返回 Going 正向执行 inRelative 鼠标在关联组件中 inCom在执行组件中
	private boolean on=true,needBack=false,Going=false,inRelative=false,inCom=false;
	//分支——执行指定activity
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
	//不可视构造
	private Slider(int branch,Component relative,Component c,Point finish,int xTime,int yTime){
		this.c=c;
		this.relative=relative;
		this.start=c.getLocation();
		this.finish=finish;
		getSpeed(xTime, yTime);
		branch(branch);
	}
	/**
	 * 定点滑动（无减速）
	 * @see Slider
	 * @param c 操作对象
	 * @param finish 终点
	 * @param xTime 横向用时
	 * @param yTime 纵向用时
	 * @param activity 任务（具体参见类注释）
	 * @return 在activity为RETURN时返回Slider对象
	 */
	public static Slider slide(Component c,Point finish,int xTime,int yTime,int activity){
		return deal(activity,null,c,finish,xTime,yTime);
	}
	/**
	 * 定点滑动（末端减速）
	 * @see Slider
	 * @param c 操作对象
	 * @param finish 终点
	 * @param xTime 横向用时（微秒）
	 * @param yTime 纵向用时（微秒）
	 * @param activity 任务（具体参见类注释）
	 * @param break_percent 末端减速的距离（以小数表示百分比[0.0f,1.0f]）
	 * @param fSpeed_percent 末端减速的最终速度（以小数表示百分比(0.0f,1.0f]）
	 * @return 在activity为RETURN时返回Slider对象
	 */
	public static Slider s_break(Component c,Point finish,int xTime,int yTime,int activity,float break_percent,float fSpeed_percent){
		Slider slide=slide(c,finish,xTime,yTime,RETURN);
		return breakDeal(slide,activity,break_percent,fSpeed_percent);
	}
	/**
	 * 定向滑动（无减速）
	 * @see Slider
	 * @param c 操作对象
	 * @param direction 滑动方向 TOP|LEFT|BOTTOM|RIGHT
	 * @param px 滑动距离
	 * @param time 滑动时间（微秒）
	 * @param activity 任务（具体参见类注释）
	 * @return 在activity为RETURN时返回Slider对象
	 */
	public static Slider direct(Component c,int direction,int px,int time,int activity){
		Point finish=getFinish(c,direction,px);
		return deal(activity,null,c,finish,time,time);
	}
	/**
	 * 定向滑动（末端减速）
	 * @see Slider
	 * @param c 操作对象
	 * @param direction 滑动方向 TOP|LEFT|BOTTOM|RIGHT
	 * @param px 滑动距离
	 * @param time 滑动时间（微秒）
	 * @param activity 任务（具体参见类注释）
	 * @param break_percent 末端减速的距离（以小数表示百分比[0.0f,1.0f]）
	 * @param fSpeed_percent 末端减速的最终速度（以小数表示百分比(0.0f,1.0f]）
	 * @return 在activity为RETURN时返回Slider对象
	 */
	public static Slider d_break(Component c,int direction,int px,int time,int activity,float break_percent,float fSpeed_percent){
		Slider slide=direct(c,direction,px,time,RETURN);
		return breakDeal(slide,activity,break_percent,fSpeed_percent);
	}
	/**
	 * 关联滑动（无减速）
	 * @see Slider
	 * @param relative 相对的组件
	 * @param c 操作对象
	 * @param direction 滑动方向
	 * @param time 滑动时间（微秒）
	 * @param autoLocate 根据相对的自动定位
	 * @param activity 任务（具体参见类注释）
	 * @return 在activity为RETURN时返回Slider对象
	 */
	public static Slider relat(Component relative,Component c,int direction,int time,boolean autoLocate,int activity){
		Point finish=getFinish_autoLoacte(relative, c, direction, autoLocate);
		return deal(activity,relative,c,finish,time,time);
	}
	/**
	 * 关联滑动（末端减速）
	 * @see Slider
	 * @param relative 关联的组件
	 * @param c 操作对象
	 * @param direction 滑动方向
	 * @param time 滑动时间（微秒）
	 * @param autoLocate 根据关联组件自动定位
	 * @param activity 任务（具体参见类注释）
	 * @param break_percent 末端减速的距离（以小数表示百分比[0.0f,1.0f]）
	 * @param fSpeed_percent 末端减速的最终速度（以小数表示百分比(0.0f,1.0f]）
	 * @return 在activity为RETURN时返回Slider对象
	 */
	public static Slider r_break(Component relative,Component c,int direction,int time,boolean autoLocate,int activity,float break_percent,float fSpeed_percent){
		Slider slide=relat(relative,c,direction,time,autoLocate,RETURN);
		return breakDeal(slide,activity,break_percent,fSpeed_percent);
	}
	//普通请求处理
	private static Slider deal(int activity,Component relative,Component c,Point finish,int xTime,int yTime){
		Slider slider=new Slider(activity,relative,c,finish,xTime,yTime);
		if(activity==RETURN)
			return slider;
		return null;
	}
	//请求进一步处理——实现末端减速
	private static Slider breakDeal(Slider slide,int activity,float break_percent,float fSpeed_percent){
		slide.setBreak(break_percent, fSpeed_percent);
		if(activity==RETURN)
			return slide;
		slide.branch(activity);
		return null;
	}
	//根据起始点获取双向的速度（每interval前进px）
	private void getSpeed(int xTime,int yTime){
		xSpeed=Math.abs((float)(finish.x-start.x)/(xTime/intervals));
		ySpeed=Math.abs((float)(finish.y-start.y)/(yTime/intervals));
	}
	//定向滑动获取终点
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
	//关联滑动获取终点。自动定位
	private static Point getFinish_autoLoacte(Component relative,Component c,int direction,boolean autoLocate){
		Point p=new Point(0,0);
		int X=relative.getX(),Y=relative.getY(),W=relative.getWidth(),H=relative.getHeight(),cW=c.getWidth(),cH=c.getHeight();
		System.out.println(relative.getWidth()+" "+relative.getHeight());
		if((W==0&&H==0)){
			Dimension preferred=relative.getPreferredSize();
			System.out.println(preferred);
			W=preferred.width;
			H=preferred.height;
			//TODO 修改点：若关联对象长宽为0，则考虑其使用了相对大小。关联滑动增加内部外部区分。
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
	 * 添加监听
	 * @param listenType 监听类型 LISTEN_MouseEnter、LISTEN_MousePress
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
	 * 添加末端减速
	 * @param break_percent 末端减速减速距离（以小数表示百分比）
	 * @param fSpeed_percent 末端减速最终速度（以小数表示百分比）
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
	 * 继续正向执行
	 */
	public void goOn(){
		Going=true;
		start(c.getLocation(),finish);
	}
	/**
	 * 继续反向执行
	 */
	public void goBack(){
		Going=false;
		start(c.getLocation(),start);
	}
	
	/**
	 * 线程
	 * @param start 起始点
	 * @param finish 终点
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
	//末端减速
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
	//重置滑动的结尾，以下方法应对有位置变更的组件
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