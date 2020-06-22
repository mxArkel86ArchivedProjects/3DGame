package utilities;

public class Point {
	public double x;
	public double y;
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return (int)(x);
	}
	public int getY() {
		return (int)(y);
	}
	public void add(double x, double y) {
		this.x+=x;
		this.y+=y;
	}
	public Point multiply(int n) {
		return new Point((int)(x*n), (int)(y*n));
	}
	
}
