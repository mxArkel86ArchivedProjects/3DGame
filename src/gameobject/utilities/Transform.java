package gameobject.utilities;

public class Transform {
	public double x = 0;
	public double y = 0;
	public double z = 0;
	
	public Transform(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int getX() {
		return (int)x;
	}

	public int getY() {
		return (int)y;
	}

	public int getZ() {
		return (int)z;
	}

	public Transform() {
	}
}
