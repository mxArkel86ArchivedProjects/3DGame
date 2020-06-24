package render;

import java.util.ArrayList;

import gameobject.utilities.Transform;
import utilities.Vector;
import utilities.Voxel;

public class GameObject {
	public Transform transform;
	public double scale = 1;
	public ArrayList<Voxel> voxels = new ArrayList<Voxel>();
	public ArrayList<Vector> vectors = new ArrayList<Vector>();
	
	public Vector center = new Vector();
}
