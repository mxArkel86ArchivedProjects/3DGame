package gameobject.entities;

import gameobject.utilities.Transform;

public class Player {
	public Transform transform = new Transform(0,0,0);
	public double fov_horizontal = Math.PI/4;
	public double fov_vertical = Math.PI/4;//45deg
	public double lookanglex = 0;
	public double lookangley = 0;
}
