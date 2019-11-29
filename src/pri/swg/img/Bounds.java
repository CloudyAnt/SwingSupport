package pri.swg.img;

/**
 * 图像边界信息
 * <hr>
 * 已用到的Support类：
 * <li>Imager</li>
 * 
 * @author 柴晓
 *
 */
public class Bounds {
	private int x, y, Width, Height;

	public Bounds(int x, int y, int Width, int Height) {
		this.x = x;
		this.y = y;
		this.Width = Width;
		this.Height = Height;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return Width;
	}

	public void setWidth(int width) {
		Width = width;
	}

	public int getHeight() {
		return Height;
	}

	public void setHeight(int height) {
		Height = height;
	}

}
