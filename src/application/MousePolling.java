package application;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.util.TimerTask;

import utilities.Point;

public class MousePolling extends TimerTask {
	public SharedAttributes sa;
	Point windowPos = new Point(App.app.getLocation());
	Point centerScreen = new Point(GameSettings.windowSize.getWidth() / 2, GameSettings.windowSize.getHeight() / 2);
	Point centerPos;
	Robot r;

	public MousePolling() {
		try {
			r = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	Point prevPoint = windowPos.add(centerScreen);

	@Override
	public void run() {
		centerPos = windowPos.add(centerScreen);
		
		sa.keyInput.mouseCurrent = new Point(MouseInfo.getPointerInfo().getLocation());
		sa.keyInput.mouseChange = prevPoint.subtract(sa.keyInput.mouseCurrent);
		prevPoint = sa.keyInput.mouseCurrent;
		if(sa.keyInput.mouseCurrent.distance(centerPos)>300) {
			r.mouseMove(windowPos.getX()+centerScreen.getX(), windowPos.getY()+centerScreen.getY());
			prevPoint = centerPos;
		}
			
	}

}
