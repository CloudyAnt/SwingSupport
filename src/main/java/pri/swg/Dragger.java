package pri.swg;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 拖曳控制类
 * <hr>
 * Drag Control
 *
 * @author 柴晓
 * @version 1.1 17/03/23 原来的基础上增加了横向与纵向拖动效果
 */
// 监听相克：同种监听中的方法是相克的，即监听到另一种活动会立刻终止监听当前活动
public class Dragger {
    /*
     * cStart 组件起始位置 mPre 鼠标当前位置 mStart 鼠标按下位置
     */
    private Point cStart, mPresent, mStart;
    public static final int Horizontal = 2, Vertical = 3;
    private static final int BOTH = 1;

    /**
     * Scope limited drag
     */
    private Dragger(Component c, Component listen, Point limitStart, Point limitEnd) {
        listen.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                cStart = c.getLocation();
                mStart = e.getLocationOnScreen();
            }
        });
        listen.addMouseMotionListener(new MouseAdapter() {
            int xa = -1, xb = 10000, ya = -1, yb = 10000;

            {
                if (limitStart != null && limitEnd != null) {
                    if (limitStart.x < limitEnd.x) {
                        xa = limitStart.x;
                        xb = limitEnd.x;
                    } else {
                        xa = limitEnd.x;
                        xb = limitStart.x;
                    }
                    if (limitStart.y < limitEnd.y) {
                        ya = limitStart.y;
                        yb = limitEnd.y;
                    } else {
                        ya = limitEnd.y;
                        yb = limitStart.y;
                    }
                }
            }

            public void mouseDragged(MouseEvent e) {
                mPresent = e.getLocationOnScreen();
                int x = mPresent.x - mStart.x + cStart.x, y = mPresent.y - mStart.y + cStart.y;
                c.setLocation(new Point(x < xa ? xa : Math.min(x, xb), y < ya ? ya : Math.min(y, yb)));
            }
        });
    }

    /**
     * Scope limited single direction drag
     */
    private Dragger(Component c, Component listen, int direction, int left, int right) {

        int a = Math.min(left, right), b = Math.max(left, right);
        listen.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                cStart = c.getLocation();
                mStart = e.getLocationOnScreen();
            }
        });
        if (direction == Horizontal)
            listen.addMouseMotionListener(new MouseAdapter() {
                public void mouseDragged(MouseEvent e) {
                    mPresent = e.getLocationOnScreen();
                    int x = mPresent.x - mStart.x + cStart.x;
                    c.setLocation(new Point(x < a ? a : Math.min(x, b), cStart.y));
                }
            });
        else if (direction == Vertical)
            listen.addMouseMotionListener(new MouseAdapter() {
                public void mouseDragged(MouseEvent e) {
                    mPresent = e.getLocationOnScreen();
                    int y = mPresent.y - mStart.y + cStart.y;
                    c.setLocation(new Point(cStart.x, y < a ? a : Math.min(y, b)));
                }
            });
    }

    /**
     * Scope unlimited drag
     */
    private Dragger(Component c, Component listen, int type) {
        listen.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                cStart = c.getLocation();
                mStart = e.getLocationOnScreen();
            }
        });
        if (type == BOTH)
            listen.addMouseMotionListener(new MouseAdapter() {
                public void mouseDragged(MouseEvent e) {
                    mPresent = e.getLocationOnScreen();
                    c.setLocation(new Point(mPresent.x - mStart.x + cStart.x, mPresent.y - mStart.y + cStart.y));
                }
            });
        else if (type == Horizontal)
            listen.addMouseMotionListener(new MouseAdapter() {
                public void mouseDragged(MouseEvent e) {
                    mPresent = e.getLocationOnScreen();
                    c.setLocation(new Point(mPresent.x - mStart.x + cStart.x, cStart.y));
                }
            });
        else if (type == Vertical)
            listen.addMouseMotionListener(new MouseAdapter() {
                public void mouseDragged(MouseEvent e) {
                    mPresent = e.getLocationOnScreen();
                    c.setLocation(new Point(cStart.x, mPresent.y - mStart.y + cStart.y));
                }
            });
    }

    /**
     *
     * Let component <i>c</i> draggable. Direction is limited by <i>direction</i>,
     * range is limited by <i>left</i> & <i>right</i>. Drags listened by Component
     * <i>listen</i>
     * 
     * @param left  is top when <i>direction</i> is Vertical
     * @param right is bottom when <i>direction</i> is Vertical
     *
     */
    public static void oneDirectionDrag(Component c, Component listen, int direction, int left, int right) {
        new Dragger(c, listen, direction, left, right);
    }

    /**
     * Let component <i>c</i> draggable. Direction is limited by field
     * <i>direction</i>. Drags listened by Component <i>listen</i>
     */
    public static void oneDirectionDrag(Component c, Component listen, int direction) {
        new Dragger(c, listen, direction);
    }

    /**
     * Let component <i>c</i> draggable. Range is limited by <i>leftTop</i> &
     * <i>rightBottom</i>. Drags listened by Component <i>listen</i>
     */
    public static void drag(Component c, Component listen, Point leftTop, Point rightBottom) {
        new Dragger(c, listen, leftTop, rightBottom);// 使用对象因为有需要使用的全局常量
    }

    /**
     * Let component <i>c</i> draggable with no limits. Drags listened by Component
     * <i>listen</i>
     */
    public static void drag(Component c, Component listen) {
        new Dragger(c, listen, BOTH);
    }

    /**
     * Let component <i>c</i> draggable with no limits
     */
    public static void drag(Component c) {
        new Dragger(c, c, BOTH);
    }
}
