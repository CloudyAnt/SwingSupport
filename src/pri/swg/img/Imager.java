package pri.swg.img;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
/**
 * Image图形控制类<hr>
 * <h1>实现图像旋转，功能待扩充<br>
 * 继承自JPanel，使用时创建本类对象。之前请创建Bounds对象，构造函数参数中有用到</h1>
 * @author 柴晓
 * @version 1.0 16/09/02
 */
public class Imager extends JPanel{
	private static final long serialVersionUID = 1L;
	private final int TURN=0,SILENT=-1;
	//action 命令 time 用时 intervals 刷新间隔 
	//iW iH 图片长宽 cX cY 中心点（旋转）pX pY 位置（左上角）
	private int action,time,intervals=10,iW,iH,cX,cY,pX,pY,line=0;
	private float radians,added=0;
	private boolean notStop=false,sized=false,on=false,going=true;
	private Image img;
	private Imager thisone;
	private Component owner;
//	public void repaint(){
//		super.repaint();
//		System.out.println("REPAINT");
//	}
	public void paint(Graphics g){//因为要改写paint，所以要把此当成一个组件添加
		super.paint(g);
		Graphics2D g2=(Graphics2D)g;
		switch(action){
		case SILENT:break;
		case TURN:
			g2.rotate(added,cX,cY);break;
		}
		g2.drawImage(img, pX, pY, iW, iH, thisone);
//		owner.repaint();
//		System.out.println("HERE");
//		if(action==CIRCLE){
//			Graphics2D g2=(Graphics2D)g;
////			g2.scale(1,.5);
//			g2.drawImage(img,pX,pY,iW,iH,thisone);
//			owner.repaint();		//消除之前的图像痕迹
//		}else if(action==SILENT){
//			
//		}
	}
//	public static void setImage(JPanel owner,URL url,Bounds bounds){
//		Imager image=new Imager(owner,url,bounds);
//		image.dealSilent();
//	}
	private Imager(){
		this.setOpaque(false);
	}
	public Imager(Component owner,URL url,Bounds bounds){
		this();
		autoSet(owner,url,bounds);
		dealSilent();
	}
	/**
	 * 自动定位设置
	 */
	private void autoSet(Component owner,URL url,Bounds bounds){
		int[]ii=getSize(url);
		setBounds(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
		set(owner,url,new Point((this.getWidth()-ii[0])/2,(this.getHeight()-ii[1])/2));
	}
	/**
	 * 默认中心旋转，原图大小。
	 * 可通过setDate设置具体旋转顶点、旋转中心，以及图片大小
	 * @param owner 父组件
	 * @param url 图像路径
	 * @param angdeg 旋转角度
	 * @param time 旋转用时
	 * @param bounds 边界
	 */
	public Imager(Component owner,URL url,int angdeg,int time,Bounds bounds){
		this();
		autoSet(owner,url,bounds);
		dealSpin(new Point(this.getWidth()/2,this.getHeight()/2),angdeg, time);
	}
	/**
	 * 自定义中心旋转
	 * @param owner 父组件
	 * @param url 图像路径
	 * @param center 旋转中心
	 * @param position 图像左上定点位置
	 * @param angdeg 旋转角度
	 * @param time 旋转用时
	 */
	public Imager(Component owner,URL url,Point center,Point position,int angdeg,int time){
		this();
		set(owner,url,position);
		dealSpin(center,angdeg,time);
	}
	/**
	 * 通用设置
	 * @param owner	父组件
	 * @param url 路径
	 * @param position 左上角
	 */
	private void set(Component owner,URL url,Point position){
		this.owner=owner;
		thisone=this;
		img=Toolkit.getDefaultToolkit().getImage(url);
		
		int[]ii=getSize(url);
		iW=ii[0];
		iH=ii[1];
		
		pX=position.x;
		pY=position.y;
		pX=0;
		pY=0;
	}
	private void dealSilent(){
		action=SILENT;
	}
	//旋转设置
	private void dealSpin(Point center,int angdeg,int time){
		radians=(float) Math.toRadians(angdeg);
		this.time=time;
		cX=center.x;
		cY=center.y;
		action=TURN;
	}
	//获取图像大小
	private int[] getSize(URL url){
		ImageIcon i=new ImageIcon(url);
		int []ii=new int[2];
		ii[0]=sized?iW:i.getIconWidth();
		ii[1]=sized?iH:i.getIconHeight();
		return ii;
	}
	//设置图像大小
	public void setsImageSize(int width,int height){
		iW=width;
		iH=height;
		sized=true;
	}
	/**
	 * 启动
	 */
	public void start(){
		lineChange();
		if(!on){
			lineChange();
			imageStart();
		}
	}
	/**
	 * 终止
	 */
	public void stop(){
		on=false;
	}
	/**
	 * 正向执行
	 */
	public void goOn(){
		going=true;
		lineChange();
		start();
	}
	/**
	 * 反向执行
	 */
	public void goBack(){
		going=false;	
		lineChange();
		start();
	}
	//线程启动
	private void imageStart(){
		on=true;
		int preLine=line;
		Thread t=new Thread(new Runnable() {
			public void run() {
				float times=time/intervals;
				float add;
				if(going)
					add=radians/times;
				else
					add=-radians/times;
				while((on||notStop)&&preLine==line){
					 try {
						Thread.sleep(intervals);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					 added+=add;
					 if(judge()&&!notStop){
						 added=radians;
						 on=false;
					 }
					 repaint();
				}
				on=false;
			}
		});
		t.start();
	}
	//判断是否结束
	private boolean judge(){
		return radians>=0?added>=radians:added<radians;
	}
	//运行链改变
	private void lineChange(){
		line=line>1000?0:++line;
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
	public boolean isNotStop() {
		return notStop;
	}
	public void setNotStop(boolean notStop) {
		this.notStop = notStop;
	}
	public Component getOwner() {
		return owner;
	}
	public void setOwner(Component owner) {
		this.owner = owner;
	}
	public void setImg(URL url){
		img=Toolkit.getDefaultToolkit().getImage(url);
		int[]ii=getSize(url);
		pX=(this.getWidth()-ii[0])/2;
		pY=(this.getHeight()-ii[1])/2;
	}
	/**
	 * 设置旋转顶点、旋转中心、图片大小
	 * @param picWidth 图片宽度
	 * @param picHeight 图片长度
	 * @param centerX 旋转中心X
	 * @param centerY 旋转中心Y
	 * @param positionX 旋转顶点 X
	 * @param positionY 旋转顶点Y
	 */
	public void setImgData(int picWidth,int picHeight,int centerX,int centerY,int positionX,int positionY){
		iW=picWidth;
		iH=picHeight;
		cX=centerX;
		cY=centerY;
		pX=positionX;
		pY=positionY;
	}
	/**
	 * TODO 旋转数据
	 */
	public void setCircleData() {
		
	}
	/**
	 * TODO 静态图数据
	 */
	public void setStaticData() {
		
	}
}
