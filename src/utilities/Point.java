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
	public Point(java.awt.Point point) {
		this.x = point.x;
		this.y = point.y;
	}
	public int getX() {
		return (int)(x);
	}
	public int getY() {
		return (int)(y);
	}
	public Point add(Point p) {
		return new Point(this.x+p.x, this.y+p.y);
	}
	public Point subtract(Point p) {
		return new Point(this.x-p.x, this.y-p.y);
	}
	public double distance(Point p) {
		double tx = p.x-x;
		double ty = p.y-y;
		return Math.sqrt(Math.pow(tx, 2)+Math.pow(ty, 2));
	}
	public Point multiply(Point point) {
		return new Point((int)(x*point.x), (int)(y*point.y));
	}
	
}
