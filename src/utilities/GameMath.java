package utilities;

public class GameMath {
	public static double exactAngle(double y, double x, double dist) {
		return 2*Math.atan(y/(x+dist));
	}
	public static double exactAngle(double y, double x) {
		return exactAngle(y, x, Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2)));
	}
	public static double distance(double tx, double ty) {
		return Math.sqrt(Math.pow(tx, 2)+Math.pow(ty, 2));
	}
}
