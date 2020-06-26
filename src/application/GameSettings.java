package application;

import utilities.Size;

public class GameSettings {
	public static double refresh_rate = 60f;//screen refresh rate
	public static Size windowSize = new Size(1280, 720);//window size
	public static int backend_refresh_rate = 10;//game input rate
	public static int mousepoll_rate = 4;//how often mouse input will be taken
	public static double mousemove_buffer = 0.2;//buffer between mouse and border of window (20% from edge)

}
