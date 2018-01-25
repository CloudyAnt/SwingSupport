package pri.swg;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * ��ҷ������<hr>
 * @author ����
 * @version 1.0 16/09/02
 * @version 1.1 17/03/23 ԭ���Ļ����������˺����������϶�Ч��
 */
//������ˣ�ͬ�ּ����еķ�������˵ģ�����������һ�ֻ��������ֹ������ǰ�
public class Dragger{
	/*
	 * cStart 	�����ʼλ��
	 * mPre		��굱ǰλ��
	 * mStart	��갴��λ��
	 */
	private Point cStart,mPre,mStart;
	public static final int Horizont=2,Vertical=3;
	private static final int BOTH=1;
	/**
	 * ָ����Χ��˫����ҷ
	 */
	private Dragger(Component c,Component listen,Point ap,Point bp){
		listen.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e){
				cStart=c.getLocation();
				mStart=e.getLocationOnScreen();
			}
		});
		listen.addMouseMotionListener(new MouseAdapter() {
			int xa=-1,xb=10000,ya=-1,yb=10000;
			{
				if(ap!=null&&bp!=null){
					if(ap.x<bp.x){
						xa=ap.x;xb=bp.x;
					}else{
						xa=bp.x;xb=ap.x;
					}
					if(ap.y<bp.y){
						ya=ap.y;yb=bp.y;
					}else{
						ya=bp.y;yb=ap.y;
					}
				}
			}
			public void mouseDragged(MouseEvent e){
				mPre=e.getLocationOnScreen();
				int x=mPre.x-mStart.x+cStart.x,y=mPre.y-mStart.y+cStart.y;
				c.setLocation(new Point(x<xa?xa:x>xb?xb:x,y<ya?ya:y>yb?yb:y));
			}
		});
	}
	/**
	 * ָ����Χ�ĵ�����ҷ
	 */
	private Dragger(Component c,Component listen,int type,int a,int b){
		listen.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e){
				cStart=c.getLocation();
				mStart=e.getLocationOnScreen();
			}
		});
		if(type==Horizont)
		listen.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e){
				mPre=e.getLocationOnScreen();
				int x=mPre.x-mStart.x+cStart.x;
				c.setLocation(new Point(x=x<a?a:x>b?b:x,cStart.y));
			}
		});else if(type==Vertical)
		listen.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e){
				mPre=e.getLocationOnScreen();
				int y=mPre.y-mStart.y+cStart.y;
				c.setLocation(new Point(cStart.x,y<a?a:y>b?b:y));
			}
		});
	}
	/**
	 * ��ָ����Χ����ҷ
	 */
	private Dragger(Component c,Component listen,int type){
		listen.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e){
				cStart=c.getLocation();
				mStart=e.getLocationOnScreen();
			}
		});
		if(type==BOTH)
		listen.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e){
				mPre=e.getLocationOnScreen();
				c.setLocation(new Point(mPre.x-mStart.x+cStart.x,mPre.y-mStart.y+cStart.y));
			}
		});else if(type==Horizont)
		listen.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e){
				mPre=e.getLocationOnScreen();
				c.setLocation(new Point(mPre.x-mStart.x+cStart.x,cStart.y));
			}
		});else if(type==Vertical)
		listen.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e){
				mPre=e.getLocationOnScreen();
				c.setLocation(new Point(cStart.x,mPre.y-mStart.y+cStart.y));
			}
		});
	}
	/**
	 * ָ����Χ�ĵ�����ҷ
	 */
	public static void drag_VOH(Component c,Component listen,int type,int a,int b){
		new Dragger(c,listen,type,a,b);
	}
	/**
	 * ָ����Χ��˫����ҷ
	 */
	public static void drag(Component c,Component listen,Point ap,Point bp){
		new Dragger(c,listen,ap,bp);//ʹ�ö�����Ϊ����Ҫʹ�õ�ȫ�ֳ���
	}
	
	/**
	 * �������listen��ҷc����ѡ����
	 */
	public static void drag_VOH(Component c,Component listen,int type){
		new Dragger(c,listen,type);
	}
	/**
	 * �������listen��ҷ
	 */
	public static void drag(Component c,Component listen){
		new Dragger(c,listen,BOTH);
	}
	/**
	 * ʹ���c����ҷ
	 */
	public static void drag(Component c){
		new Dragger(c,c,BOTH);
	}
}
