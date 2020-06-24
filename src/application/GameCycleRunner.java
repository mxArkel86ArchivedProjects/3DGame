package application;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.TimerTask;
import utilities.CONST;
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
		
		/*int x = 0;
		int y = 0;
		if(sa.keyInput.w)
			y+=1;
		if(sa.keyInput.s)
			y-=1;
		if(sa.keyInput.a)
			x-=1;
		if(sa.keyInput.d)
			x+=1;
		sa.rotationx+=speed*x;
		sa.rotationy+=speed*y;
		if(sa.rotationx>CONST.Pi2)
			sa.rotationx-=CONST.Pi2;
		if(sa.rotationx<0)
			sa.rotationx+=CONST.Pi2;
		if(sa.rotationy>CONST.Pi2)
			sa.rotationy-=CONST.Pi2;
		if(sa.rotationy<0)
			sa.rotationy+=CONST.Pi2;
		*/
		if(sa.keyInput.mouseCurrent!=null) {
			
			
		double xlook = sa.keyInput.mouseChange.x*looksensitivity;
		double ylook = sa.keyInput.mouseChange.y*looksensitivity;
		sa.lookanglex +=xlook;
		sa.lookangley +=ylook;
		sa.lookanglex = angleCheck(sa.lookanglex);
		sa.lookangley = angleCheck(sa.lookangley);
		sa.rotationx = sa.lookanglex;
		sa.rotationy = sa.lookangley;
		}
		
		for (Vector vect : sa.vectors) {
			vect.out = screenLocation3D(vect.x, vect.y, vect.z, sa.center.x, sa.center.y, sa.center.z, sa.rotationx,
					sa.rotationy);
		}
		long end = System.currentTimeMillis();
		sa.backendcompletion = end-start;
		
		int fps = (int)(100000/(GameSettings.refresh_rate*sa.frontendcompletion));
		if(temp1<50)
			temp1++;
		else {
			temp1=0;
			sa.fps = fps;
		}
	}	
	private double angleCheck(double angle) {
		double a = angle;
		while(a>CONST.Pi2)
			a-=CONST.Pi2;
		while(a<0)
			a+=CONST.Pi2;
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
			double rotationx, double rotationy) {
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
		return v.add(new Vector(centerx, centery, centerz));
	}


}
