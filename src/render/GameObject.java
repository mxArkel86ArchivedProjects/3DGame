package render;

import java.util.ArrayList;

import gameobject.utilities.Transform;
import utilities.Vector;
import utilities.Polygon;

public class GameObject {
	public Transform transform = new Transform(0,0,0);
	public double scale = 1;
	public ArrayList<Polygon> polygons = new ArrayList<Polygon>();
	public ArrayList<Vector> vectors = new ArrayList<Vector>();
	
	public Vector center = new Vector();
}
