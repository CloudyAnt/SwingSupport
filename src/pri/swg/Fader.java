package pri.swg;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * Swing组件颜色淡入控制类<hr>
 * <h1>共2个方法可用：</h1>
 * <li>三参的gradual，默认部分参数</li>
 * <li>完整的gradual，自定全部参数</li>
 * <h1>其中activity参数详解：</h1>
 * <li>NULL：——立刻执行，无特殊操作</li>
 * <li>NULL_BACK：——立刻执行，并返回原色</li>
 * <li>LISTEN_MouseEnter：——监听鼠标移入移出</li>
 * <li>LISTEN_MousePress：——监听鼠标按下松开</li>
 * <li>RETURN：——返回对象</li>
 * <h1>以上方法禁止写入高频率监听中</h1>
 * <h1>对象可调用的start、stop、goOn、goBack方法允许写入监听</h1>
 * <h1>对象用setter、getter方法设置或获取每次用时（time）、刷新间隔（intervals/ms）、位置（index）</h1><hr>
 * @author 柴晓
 * @version 2.0 大幅优化1.x结构，使操作灵活 16/09/01
 * @version 2.1 修复反向执行的index可能为-1错误
 * @version 2.2 完全修复越界问题 17/5/21
 */
public class Fader {
	public static final int BACK_GROUND=-11,FORE_GROUND=11,
			NULL=0,NULL_BACK=10,RETURN=1,LISTEN_MouseEnter=2,LISTEN_MousePress=3;
	private Component c;
	private int place,time,intervals=10,length,lines=0,index=0,fullTimes;
	private Color[]colors;
	private Integer[] present=new Integer[4],next=new Integer[4];
	private float[] pre_float=new float[4];
	private boolean on=true,Going=true,needBack=false;
	//不可视构造
	private Fader(Component c,int place,int time,int activity,Color...colors){
		this.c=c;
		this.place=place;
		this.time=time;
		this.length=colors.length+1;
		this.colors=new Color[colors.length+1];
		this.colors[0]=getSource(place);
		fullTimes=time/intervals; 					//运行大致次数，据此判断每次大致加值
		for(int i=1;i<this.colors.length;i++){
			this.colors[i]=colors[i-1];
		}
		present=getRGBA(this.colors[0]);
		next=getRGBA(this.colors[1]);
		if(activity==NULL)
			start();
		else if(activity==NULL_BACK){
			needBack=true;
			start();
		}
		else if(activity>=LISTEN_MouseEnter){
			addListen(activity);
		}
	}
	/**
	 * 颜色渐变（默认单次用时1s)
	 * @param c 颜色渐变的组件
	 * @param activity 执行任务（具体参见本类注释）
	 * @param colors 渐变到的颜色
	 * @return 若activity为RETURN，则返回对象，否则返回null
	 * @see Fader
	 */
	public static Fader fade(Component c,int activity,Color...colors){
		return deal(c,BACK_GROUND,1000,activity,colors);
	}
	/**
	 * 全参——颜色渐变
	 * @param c 颜色渐变的组件
	 * @param place 渐变位置
	 * @param time 单次渐变用时（ms）
	 * @param activity 执行任务（具体参见本类注释）
	 * @param colors 渐变到的颜色
	 * @return 若activity为RETURN，则返回对象，否则返回null
	 * @see Fader
	 */
	public static Fader fade(Component c,int place,int time,int activity,Color...colors){
		return deal(c,place,time,activity,colors);
	}
	//处理请求
	private static Fader deal(Component c,int place,int time,int activity,Color...colors){
		Fader co=new Fader(c,place,time,activity,colors);
		if(activity==RETURN)
			return co;
		return null;
	}
	//获取当前指定组件需渐变位置的颜色
	private Color getSource(int place){
		return place==BACK_GROUND?c.getBackground():c.getForeground();
	}
	/**
	 * 启动渐变。终止之前线程。
	 */
	public void start(){
		index++;
		lineChange();
		start(present,next,lines);
	}
	/**
	 * 终止(暂停)进程
	 */
	public void stop(){
		on=false;
	}
	/**
	 * 正向执行
	 */
	public void goOn(){
		Going=true;
		lineChange();
		if(index<length-1)
			start(getRGBA(getSource(place)),getRGBA(colors[++index]),lines);
	}
	/**
	 * 反向执行
	 */
	public void goBack(){
		Going=false;
		lineChange();
		if(index>0)
			start(getRGBA(getSource(place)),getRGBA(colors[--index]),lines);
	}
	//添加监听
	private void addListen(int listenType){
		if(listenType==LISTEN_MouseEnter){
			c.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e){
					goOn();
				}
				public void mouseExited(MouseEvent e){
					goBack();
				}
			});
		}
		else if(listenType==LISTEN_MousePress){
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
	 * 设置参数并启动下阶段渐变线程
	 * @param pre 当前颜色RGBA数组
	 * @param nex 下一个颜色RGBA数组
	 * @param lines 当前运行链标记
	 */
	private void start(Integer pre[],Integer nex[],int line){
		present=pre;
		synchronized("N"){
		next=nex;
		}
		float add[]=new float[4];
		for(int i=0;i<add.length;i++){
			add[i]=(float)(nex[i]-pre[i])/fullTimes;//每种颜色的每次大致加值
			pre_float[i]=pre[i];					//初始化
		}
		doSlide(add,line);
	}
	/**
	 * 渐变线程
	 * @param add 每次加值数组
	 * @param line 运行链标记
	 */
	private void doSlide(final float add[],int line){
		on=true;
		new Thread(()-> {
				//是否继续增加。每个颜色属性到达目标后对应的解锁。
				Boolean locked[]={new Boolean(true),new Boolean(true),new Boolean(true),new Boolean(true)};
				while(on&&line==lines){
					try {
						Thread.sleep(intervals);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}//同步next，在给pre_float赋值后next的改变会导致judge判断错误
					synchronized("N"){
						for(int i=0;i<add.length;i++){
							if(locked[i]){
								pre_float[i]+=add[i];
								locked[i]=judge(i);
							}
						}
					}
					setColor(pre_float);
					if(!locked[0]&&!locked[1]&&!locked[2]&&!locked[3])
						on=false;
				}
				if(line==lines){			//检查是否要继续
					boolean still=false;
					if(Going){
						if(index+1<length){
							index++;
							still=true;
						}
					}
					else{
						if(index-1>=0){
							index--;
							still=true;
						}
					}
					if(still){
						start(getRGBA(getSource(place)),getRGBA(colors[index]),line);
					}else if(needBack){
						goBack();
						needBack=false;
					}
				}
			
		}).start();
	}
	/**
	 * 设置颜色。
	 */
	private void setColor(float[] pre_float){
		if(place==BACK_GROUND)
			c.setBackground(new Color((int)pre_float[0],(int)pre_float[1],(int)pre_float[2],(int)pre_float[3]));
		else if(place==FORE_GROUND)
			c.setForeground(new Color((int)pre_float[0],(int)pre_float[1],(int)pre_float[2],(int)pre_float[3]));
	}
	/**
	 * 判断是否达到目标
	 * @param i 下标（r、g、b、a）
	 * @return 是否继续增加（doSlide方法中）
	 */
	private boolean judge(int i){
		boolean flag=true;
		if(next[i]>=present[i]&&pre_float[i]>=next[i]||pre_float[i]<0)
			flag=false;
		else if(next[i]<=present[i]&&pre_float[i]<=next[i]||pre_float[i]>255)
			flag=false;
		if(!flag)
			pre_float[i]=next[i];
		return flag;
	}
	/**
	 * 改变运行链
	 */
	private void lineChange(){
		lines=lines>10000?0:++lines;
	}
	/**获取颜色的RGBA值数组
	 * @param c 源颜色
	 * @return RGBA数组
	 */
	public static Integer[] getRGBA(Color c){
		Integer[] rgb=new Integer[4];
		rgb[0]=c.getRed();
		rgb[1]=c.getGreen();
		rgb[2]=c.getBlue();
		rgb[3]=c.getAlpha();
		return rgb;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getIntervals() {
		return intervals;
	}
	public void setIntervals(int intervals) {
		this.intervals = intervals;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public boolean isNeedBack() {
		return needBack;
	}
	public void setNeedBack(boolean needBack) {
		this.needBack = needBack;
	}
	
}
