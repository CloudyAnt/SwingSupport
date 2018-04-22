package pri.swg;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * Swing�����ɫ���������<hr>
 * <h1>��2���������ã�</h1>
 * <li>���ε�gradual��Ĭ�ϲ��ֲ���</li>
 * <li>������gradual���Զ�ȫ������</li>
 * <h1>����activity������⣺</h1>
 * <li>NULL����������ִ�У����������</li>
 * <li>NULL_BACK����������ִ�У�������ԭɫ</li>
 * <li>LISTEN_MouseEnter������������������Ƴ�</li>
 * <li>LISTEN_MousePress������������갴���ɿ�</li>
 * <li>RETURN���������ض���</li>
 * <h1>���Ϸ�����ֹд���Ƶ�ʼ�����</h1>
 * <h1>����ɵ��õ�start��stop��goOn��goBack��������д�����</h1>
 * <h1>������setter��getter�������û��ȡÿ����ʱ��time����ˢ�¼����intervals/ms����λ�ã�index��</h1><hr>
 * @author ����
 * @version 2.0 ����Ż�1.x�ṹ��ʹ������� 16/09/01
 * @version 2.1 �޸�����ִ�е�index����Ϊ-1����
 * @version 2.2 ��ȫ�޸�Խ������ 17/5/21
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
	//�����ӹ���
	private Fader(Component c,int place,int time,int activity,Color...colors){
		this.c=c;
		this.place=place;
		this.time=time;
		this.length=colors.length+1;
		this.colors=new Color[colors.length+1];
		this.colors[0]=getSource(place);
		fullTimes=time/intervals; 					//���д��´������ݴ��ж�ÿ�δ��¼�ֵ
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
	 * ��ɫ���䣨Ĭ�ϵ�����ʱ1s)
	 * @param c ��ɫ��������
	 * @param activity ִ�����񣨾���μ�����ע�ͣ�
	 * @param colors ���䵽����ɫ
	 * @return ��activityΪRETURN���򷵻ض��󣬷��򷵻�null
	 * @see Fader
	 */
	public static Fader fade(Component c,int activity,Color...colors){
		return deal(c,BACK_GROUND,1000,activity,colors);
	}
	/**
	 * ȫ�Ρ�����ɫ����
	 * @param c ��ɫ��������
	 * @param place ����λ��
	 * @param time ���ν�����ʱ��ms��
	 * @param activity ִ�����񣨾���μ�����ע�ͣ�
	 * @param colors ���䵽����ɫ
	 * @return ��activityΪRETURN���򷵻ض��󣬷��򷵻�null
	 * @see Fader
	 */
	public static Fader fade(Component c,int place,int time,int activity,Color...colors){
		return deal(c,place,time,activity,colors);
	}
	//��������
	private static Fader deal(Component c,int place,int time,int activity,Color...colors){
		Fader co=new Fader(c,place,time,activity,colors);
		if(activity==RETURN)
			return co;
		return null;
	}
	//��ȡ��ǰָ������轥��λ�õ���ɫ
	private Color getSource(int place){
		return place==BACK_GROUND?c.getBackground():c.getForeground();
	}
	/**
	 * �������䡣��ֹ֮ǰ�̡߳�
	 */
	public void start(){
		index++;
		lineChange();
		start(present,next,lines);
	}
	/**
	 * ��ֹ(��ͣ)����
	 */
	public void stop(){
		on=false;
	}
	/**
	 * ����ִ��
	 */
	public void goOn(){
		Going=true;
		lineChange();
		if(index<length-1)
			start(getRGBA(getSource(place)),getRGBA(colors[++index]),lines);
	}
	/**
	 * ����ִ��
	 */
	public void goBack(){
		Going=false;
		lineChange();
		if(index>0)
			start(getRGBA(getSource(place)),getRGBA(colors[--index]),lines);
	}
	//��Ӽ���
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
	 * ���ò����������½׶ν����߳�
	 * @param pre ��ǰ��ɫRGBA����
	 * @param nex ��һ����ɫRGBA����
	 * @param lines ��ǰ���������
	 */
	private void start(Integer pre[],Integer nex[],int line){
		present=pre;
		synchronized("N"){
		next=nex;
		}
		float add[]=new float[4];
		for(int i=0;i<add.length;i++){
			add[i]=(float)(nex[i]-pre[i])/fullTimes;//ÿ����ɫ��ÿ�δ��¼�ֵ
			pre_float[i]=pre[i];					//��ʼ��
		}
		doSlide(add,line);
	}
	/**
	 * �����߳�
	 * @param add ÿ�μ�ֵ����
	 * @param line ���������
	 */
	private void doSlide(final float add[],int line){
		on=true;
		new Thread(()-> {
				//�Ƿ�������ӡ�ÿ����ɫ���Ե���Ŀ����Ӧ�Ľ�����
				Boolean locked[]={new Boolean(true),new Boolean(true),new Boolean(true),new Boolean(true)};
				while(on&&line==lines){
					try {
						Thread.sleep(intervals);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}//ͬ��next���ڸ�pre_float��ֵ��next�ĸı�ᵼ��judge�жϴ���
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
				if(line==lines){			//����Ƿ�Ҫ����
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
	 * ������ɫ��
	 */
	private void setColor(float[] pre_float){
		if(place==BACK_GROUND)
			c.setBackground(new Color((int)pre_float[0],(int)pre_float[1],(int)pre_float[2],(int)pre_float[3]));
		else if(place==FORE_GROUND)
			c.setForeground(new Color((int)pre_float[0],(int)pre_float[1],(int)pre_float[2],(int)pre_float[3]));
	}
	/**
	 * �ж��Ƿ�ﵽĿ��
	 * @param i �±꣨r��g��b��a��
	 * @return �Ƿ�������ӣ�doSlide�����У�
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
	 * �ı�������
	 */
	private void lineChange(){
		lines=lines>10000?0:++lines;
	}
	/**��ȡ��ɫ��RGBAֵ����
	 * @param c Դ��ɫ
	 * @return RGBA����
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
