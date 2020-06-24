package application;

import java.util.TimerTask;

import gameobject.utilities.Transform;
import render.GameObject;
import utilities.CONST;
import utilities.GameMath;
import utilities.Point;
import utilities.Vector;

public class GameCycleRunner extends TimerTask {
	public SharedAttributes sa;
	public String str;

	double speed = 0.01f;
	double looksensitivity = 0.01f;

	int temp1 = 0;

	@Override
	public void run() {
		long start = System.currentTimeMillis();

		playerMovement();

		determineMouseLookAngle();
		gameObjectResolver();

		long end = System.currentTimeMillis();
		sa.backendcompletion = end - start;

		determineFPS();
	}

	void playerMovement() {
		int xMove = 0;
		int yMove = 0;
		int zMove = 0;
		if (sa.keyInput.w)
			yMove += 1;
		if (sa.keyInput.s)
			yMove -= 1;
		if (sa.keyInput.a)
			xMove -= 1;
		if (sa.keyInput.d)
			xMove += 1;
		if (sa.keyInput.c)
			zMove -= 1;
		if (sa.keyInput.space)
			zMove += 1;
		sa.player.transform.x += xMove * speed;
		sa.player.transform.y += yMove * speed;
		sa.player.transform.z += zMove * speed;
	}

	void gameObjectResolver() {
		for (GameObject obj : sa.game.gameObjects) {
			
			double tx = -obj.transform.x + sa.player.transform.x;
			double ty = obj.transform.y - sa.player.transform.y;
			double tz = -obj.transform.z + sa.player.transform.z;
			double dist = GameMath.distance(tx, ty);
			double dist3d = GameMath.distance(dist, tz);
			
			double rotx = GameMath.exactAngle(ty, tx, dist);
			double roty = GameMath.exactAngle(dist, tz, dist3d);

			for (Vector vect : obj.vectors) {
				vect.out = screenLocation3D(vect.x, vect.y, vect.z, sa.game.center.x, sa.game.center.y,
						sa.game.center.z, rotx, roty + Math.PI / 2, 1/dist3d);
			}
		}
	}

	void determineMouseLookAngle() {
		if (sa.keyInput.mouseCurrent != null) {
			double xlook = sa.keyInput.mouseChange.x * looksensitivity;
			double ylook = sa.keyInput.mouseChange.y * looksensitivity;
			sa.lookanglex += xlook;
			sa.lookangley += ylook;
			sa.lookanglex = angleCheck(sa.lookanglex);
			sa.lookangley = angleCheck(sa.lookangley);
		}
	}

	void determineFPS() {
		int fps = (int) (100000 / (GameSettings.refresh_rate * sa.frontendcompletion));
		if (temp1 < 50)
			temp1++;
		else {
			temp1 = 0;
			sa.fps = fps;
		}
	}

	double angleCheck(double angle) {
		double a = angle;
		while (a > CONST.Pi2)
			a -= CONST.Pi2;
		while (a < 0)
			a += CONST.Pi2;
		return a;
	}

	public Point screenLocation(double x, double y, double centerx, double centery, double rotation) {
		double localx = x - centerx;// get location centered at the origin
		double localy = y - centery;
		double dist = Math.sqrt(Math.pow(localx, 2) + Math.pow(localy, 2));// get the distance from the origin
		double localangle = 2 * Math.atan(localy / (localx + dist));// get the angle from the origin on an axis
		double globalangle = localangle + rotation;// add the local angle pertaining to the point with the input theta
		double xout = dist * Math.cos(globalangle);// get the new position x and y from origin
		double yout = dist * Math.sin(globalangle);
		return new Point(centerx + xout, centery + yout);
	}

	public Vector screenLocation3D(double x, double y, double z, double centerx, double centery, double centerz,
			double rotationx, double rotationy, double scale) {
		Vector v = new Vector();

		double localx = x - centerx;// get location centered at the origin
		double localy = y - centery;
		double localz = z - centerz;

		double sinX = Math.sin(rotationx);
		double cosX = Math.cos(rotationx);

		double sinY = Math.sin(rotationy);
		double cosY = Math.cos(rotationy);

		v.x = localx * cosX - localz * sinX;
		v.z = localz * cosX + localx * sinX;

		localz = v.z;

		v.y = localy * cosY - localz * sinY;
		v.z = localz * cosY + localy * sinY;
		return v.add(new Vector(centerx, centery, centerz)).multiply(scale);
	}

}
