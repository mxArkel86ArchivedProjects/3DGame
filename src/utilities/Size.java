package utilities;

public class Size {
	public double width;
	public double height;
	
	public Size(double width, double height) {
		this.width = width;
		this.height = height;
	}
	public Size(int width, int height) {
		this.width = width;
		this.height = height;
	}
	public int getWidth() {
		return (int)(width);
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public int getHeight() {
		return (int)(height);
	}
	public void setHeight(double height) {
		this.height = height;
	}
}
