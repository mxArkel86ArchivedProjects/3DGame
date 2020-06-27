package application;

import java.util.ArrayList;
import java.util.TimerTask;

import gameobject.entities.Player;
import utilities.Size;
import render.GameObject;
import utilities.CONST;
import utilities.GameMath;
import utilities.Point;
import utilities.Vector;

public class GameCycleRunner extends TimerTask {
	public SharedAttributes sa;
	public String str;

	double speed = 0.005f;
	double looksensitivity = 0.001f;
	Player p;
	ArrayList<Double> cfs_vals = new ArrayList<Double>();
	int temp0 = 0;
	
	public boolean run = false;
	public void init() {
		run = true;
		runBackend();
	}

	@Override
	public void run() {
		if(!run)
			return;
		runBackend();
	}

	private void runBackend() {
		p = sa.player;
		long start = System.currentTimeMillis();

		playerMovement(sa);

		determineMouseLookAngle(sa);
		gameObjectResolver(sa);

		long end = System.currentTimeMillis();
		sa.backendcompletion = end - start;
		
		if(temp0>50) {
			temp0 = 0;
		double avg = 0;
		for(double i : cfs_vals) {
			avg+=i;
		}
		avg/=cfs_vals.size();
		sa.cfs = avg;
		}else
			temp0++;
		cfs_vals.add(1000f/(sa.frontendcompletion*GameSettings.refresh_rate));
		if(cfs_vals.size()>50)
			cfs_vals.remove(0);
	}

	void playerMovement(SharedAttributes a) {
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

		p.transform.y += ((Math.sin(p.rotation.x) * yMove + Math.sin(p.rotation.x-Math.PI/2)*xMove)*Math.cos(p.rotation.y)+Math.cos(p.rotation.y+90)*zMove)*speed;
		p.transform.x += (Math.cos(p.rotation.x) * yMove + Math.cos(p.rotation.x-Math.PI/2)*xMove)*speed;
		p.transform.z += (Math.sin(p.rotation.y+90)*zMove+Math.sin(p.rotation.y)*yMove)*speed;
	}

	void gameObjectResolver(SharedAttributes a) {
		for (GameObject obj : a.game.gameObjects) {

			double tx = -obj.transform.x + p.transform.x;
			double ty = -obj.transform.y + p.transform.y;
			double tz = obj.transform.z - p.transform.z;
			double dist = GameMath.distance(ty, tx);
			double dist3d = GameMath.distance(dist, tz);

			double rotx = GameMath.exactAngle(ty, tx, dist);

			double roty = GameMath.exactAngle(tz, dist, dist3d);
			//System.out.println(String.format("%f %f %f = %f", dist, tz, dist3d, roty));
			double scale = 1/dist3d;


			double h = obj.size.y;
			double w = obj.size.x;
			obj.relative_size_out = new Size(Math.abs(Math.cos(rotx)*w) + Math.abs(Math.sin(rotx)*h)*scale, Math.abs(Math.cos(roty)*h) + Math.abs(Math.sin(roty)*w)*scale);

			
			double fov_dist = (GameSettings.windowSize.width)/(2*Math.tan(p.camera_fov/2));

			double xoffset = Math.tan(p.rotation.x-(-p.camera_fov/2+rotx)) * fov_dist;
			double yoffset = Math.tan(p.rotation.y-(-p.camera_fov/2+roty)) * fov_dist;
			double minz = 0;
			double maxz = 0;
			ArrayList<Vector> vects = new ArrayList<Vector>();
			for (Vector vect : obj.vectors) {
				Vector vout = screenLocation3D(vect.x, vect.y, vect.z, obj.center.x, obj.center.y,
						obj.center.z, rotx+xoffset/fov_dist-p.camera_fov/2, -roty+Math.PI-yoffset/fov_dist, scale).add(new Vector(xoffset,yoffset,0));
				
				vects.add(vout);
				if(vout.z<minz)
					minz = vout.z;
				if(vout.z>maxz)
					maxz = vout.z;
			}
			minz = Math.abs(minz);

			obj.maxZ_out = maxz+minz;
			for(int i = 0;i<obj.vectors.size();i++) {
				Vector v = vects.get(i);
				Vector v2 = obj.vectors.get(i);
				v2.out = v;
				v2.out.z+=minz;
			}
		}
	}

	void determineMouseLookAngle(SharedAttributes a) {
		if (sa.keyInput.mouseCurrent != null) {
			double xlook = sa.keyInput.mouseChange.x * looksensitivity;
			double ylook = sa.keyInput.mouseChange.y * looksensitivity;
			p.rotation.x += xlook;
			p.rotation.y += ylook;
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
