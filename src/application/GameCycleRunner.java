package application;

import java.awt.Color;
import java.util.ArrayList;
import java.util.TimerTask;

import gameobject.entities.Player;
import gameobject.utilities.Transform;
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
	public boolean run = false;
	public void init() {
		run = true;
		runBackend();
	}

	int temp1 = 0;

	@Override
	public void run() {
		if(!run)
			return;
		runBackend();
		
	}

	private void runBackend() {
		SharedAttributes attrib = sa;
		try {
			attrib = (SharedAttributes)sa.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		p = attrib.player;
		long start = System.currentTimeMillis();

		playerMovement(attrib);

		determineMouseLookAngle(attrib);
		gameObjectResolver(attrib);

		long end = System.currentTimeMillis();
		attrib.backendcompletion = end - start;
		
		sa = attrib;
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
			zMove += 1;
		if (sa.keyInput.space)
			zMove -= 1;
		
		p.transform.x += (Math.cos(p.lookanglex) * yMove + Math.cos(p.lookanglex-Math.PI/2)*xMove)*speed;
		p.transform.y += (Math.sin(p.lookanglex) * yMove + Math.sin(p.lookanglex-Math.PI/2)*xMove)*speed;
		p.transform.z += zMove * speed;
	}

	void gameObjectResolver(SharedAttributes a) {
		for (GameObject obj : a.game.gameObjects) {
			
			double tx = -obj.transform.x + p.transform.x;
			double ty = -obj.transform.y + p.transform.y;
			double tz = -obj.transform.z + p.transform.z;
			double dist = GameMath.distance(tx, ty);
			double dist3d = GameMath.distance(dist, tz);
			
			double rotx = GameMath.exactAngle(ty, tx, dist);
			
			double roty = GameMath.exactAngle(dist, tz, dist3d);
			double scale = 1/dist3d;
			
			
			double h = obj.size.y;
			double w = obj.size.x;
			obj.relsize_w = Math.abs(Math.cos(rotx)*w) + Math.abs(Math.sin(rotx)*h)*scale;
			obj.relsize_h = Math.abs(Math.cos(roty)*h) + Math.abs(Math.sin(roty)*w)*scale;
			
			double diff = -rotx+p.lookanglex;
			double x2 = Math.sin(diff)*dist;
			double x1 = GameSettings.windowSize.getWidth()/2+obj.relsize_w;
			double z2 = Math.cos(diff)*dist;
			double z = x1/Math.tan(p.fov_horizontal);
			double scalex = z/z2;
			double x2f = x2*scalex-obj.relsize_w;
			
			double diff2 = -p.lookangley+roty+Math.PI/2;
			double y2 = Math.sin(diff2)*dist;
			double y1 = GameSettings.windowSize.getHeight()/2+obj.relsize_h;
			double z2_ = Math.cos(diff2)*dist;
			double z_ = y1/Math.tan(p.fov_vertical);
			double scaley = z_/z2_;
			double y2f = y2*scaley-obj.relsize_h;
			
			
			
			double xoffset = x1+x2f;
			double yoffset = y1+y2f;
			double minz = 0;
			double maxz = 0;
			ArrayList<Vector> vects = new ArrayList<Vector>();
			for (Vector vect : obj.vectors) {
				Vector vout = screenLocation3D(vect.x, vect.y, vect.z, obj.center.x, obj.center.y,
						obj.center.z, rotx, roty + Math.PI / 2, scale).add(new Vector(xoffset,yoffset,0));
				vects.add(vout);
				if(vout.z<minz)
					minz = vout.z;
				if(vout.z>maxz)
					maxz = vout.z;
			}
			minz = Math.abs(minz);
			
			obj.max = maxz+minz;
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
			p.lookanglex += xlook;
			p.lookangley -= ylook;
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
