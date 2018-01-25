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
 * Imageͼ�ο�����<hr>
 * <h1>ʵ��ͼ����ת�����ܴ�����<br>
 * �̳���JPanel��ʹ��ʱ�����������֮ǰ�봴��Bounds���󣬹��캯�����������õ�</h1>
 * @author ����
 * @version 1.0 16/09/02
 */
public class Imager extends JPanel{
	private static final long serialVersionUID = 1L;
	private final int TURN=0,SILENT=-1;
	//action ���� time ��ʱ intervals ˢ�¼�� 
	//iW iH ͼƬ���� cX cY ���ĵ㣨��ת��pX pY λ�ã����Ͻǣ�
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
	public void paint(Graphics g){//��ΪҪ��дpaint������Ҫ�Ѵ˵���һ��������
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
//			owner.repaint();		//����֮ǰ��ͼ��ۼ�
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
	 * �Զ���λ����
	 */
	private void autoSet(Component owner,URL url,Bounds bounds){
		int[]ii=getSize(url);
		setBounds(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
		set(owner,url,new Point((this.getWidth()-ii[0])/2,(this.getHeight()-ii[1])/2));
	}
	/**
	 * Ĭ��������ת��ԭͼ��С��
	 * ��ͨ��setDate���þ�����ת���㡢��ת���ģ��Լ�ͼƬ��С
	 * @param owner �����
	 * @param url ͼ��·��
	 * @param angdeg ��ת�Ƕ�
	 * @param time ��ת��ʱ
	 * @param bounds �߽�
	 */
	public Imager(Component owner,URL url,int angdeg,int time,Bounds bounds){
		this();
		autoSet(owner,url,bounds);
		dealSpin(new Point(this.getWidth()/2,this.getHeight()/2),angdeg, time);
	}
	/**
	 * �Զ���������ת
	 * @param owner �����
	 * @param url ͼ��·��
	 * @param center ��ת����
	 * @param position ͼ�����϶���λ��
	 * @param angdeg ��ת�Ƕ�
	 * @param time ��ת��ʱ
	 */
	public Imager(Component owner,URL url,Point center,Point position,int angdeg,int time){
		this();
		set(owner,url,position);
		dealSpin(center,angdeg,time);
	}
	/**
	 * ͨ������
	 * @param owner	�����
	 * @param url ·��
	 * @param position ���Ͻ�
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
	//��ת����
	private void dealSpin(Point center,int angdeg,int time){
		radians=(float) Math.toRadians(angdeg);
		this.time=time;
		cX=center.x;
		cY=center.y;
		action=TURN;
	}
	//��ȡͼ���С
	private int[] getSize(URL url){
		ImageIcon i=new ImageIcon(url);
		int []ii=new int[2];
		ii[0]=sized?iW:i.getIconWidth();
		ii[1]=sized?iH:i.getIconHeight();
		return ii;
	}
	//����ͼ���С
	public void setsImageSize(int width,int height){
		iW=width;
		iH=height;
		sized=true;
	}
	/**
	 * ����
	 */
	public void start(){
		lineChange();
		if(!on){
			lineChange();
			imageStart();
		}
	}
	/**
	 * ��ֹ
	 */
	public void stop(){
		on=false;
	}
	/**
	 * ����ִ��
	 */
	public void goOn(){
		going=true;
		lineChange();
		start();
	}
	/**
	 * ����ִ��
	 */
	public void goBack(){
		going=false;	
		lineChange();
		start();
	}
	//�߳�����
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
	//�ж��Ƿ����
	private boolean judge(){
		return radians>=0?added>=radians:added<radians;
	}
	//�������ı�
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
	 * ������ת���㡢��ת���ġ�ͼƬ��С
	 * @param picWidth ͼƬ���
	 * @param picHeight ͼƬ����
	 * @param centerX ��ת����X
	 * @param centerY ��ת����Y
	 * @param positionX ��ת���� X
	 * @param positionY ��ת����Y
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
	 * TODO ��ת����
	 */
	public void setCircleData() {
		
	}
	/**
	 * TODO ��̬ͼ����
	 */
	public void setStaticData() {
		
	}
}
