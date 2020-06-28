package utilities;

public class Vector {
	public double x = 0;
	public double y = 0;
	public double z = 0;
	public Vector textureMap;
	public Vector out;
	public Vector(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Vector() {
		// TODO Auto-generated constructor stub
	}
	public Vector subtract(Vector in) {
		return new Vector(x-in.x, y-in.y, z-in.z);
	}
	public Vector add(Vector in) {
		return new Vector(x+in.x, y+in.y, z+in.z);
	}
	public Vector multiply(double mult) {
		return new Vector(x*mult, y*mult, z*mult);
	}
}
