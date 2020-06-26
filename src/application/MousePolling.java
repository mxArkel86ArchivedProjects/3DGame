package application;

import utilities.Size;
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.util.TimerTask;

import utilities.Point;

public class MousePolling extends TimerTask {
	public SharedAttributes sa;
	Size ws = GameSettings.windowSize;
	double buffer = GameSettings.mousemove_buffer;

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

		Point current = new Point(MouseInfo.getPointerInfo().getLocation());
		sa.keyInput.mouseChange = prevPoint.subtract(current);
		sa.keyInput.mouseCurrent = current;
		prevPoint = current;
		Point currentInApp = new Point(MouseInfo.getPointerInfo().getLocation()).subtract(windowPos);

		if((currentInApp.x < ws.width*buffer || currentInApp.x>ws.width * (1-buffer) ||
		currentInApp.y<ws.height*buffer || currentInApp.y>ws.height*(1-buffer)) && sa.keyInput.windowFocused){//if outside of constraints
			r.mouseMove(windowPos.getX()+centerScreen.getX(), windowPos.getY()+centerScreen.getY());//move to point
			prevPoint = centerPos;
		}

	}

}
