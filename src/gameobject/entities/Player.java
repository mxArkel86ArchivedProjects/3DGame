package gameobject.entities;

import gameobject.utilities.Rotation;
import gameobject.utilities.Transform;

public class Player {
	public Transform transform = new Transform(0,-1,0);
	public Rotation rotation = new Rotation(0,0);
	public double camera_fov = Math.PI/4;
}
