package render;

import java.util.ArrayList;
import java.util.Collections;

import gameobject.utilities.DepthPolygon;
import gameobject.utilities.Transform;
import utilities.Vector;
import utilities.Polygon;

public class GameObject {
	public Transform transform = new Transform(0,0,0);
	public double scale = 1;
	public double rotationx = 0;
	public double rotationy = 0;
	public ArrayList<Polygon> polygons = new ArrayList<Polygon>();
	public ArrayList<Vector> vectors = new ArrayList<Vector>();
	
	public Vector center = new Vector();
	public Vector size = new Vector();
	public double max = 0;
	public double relsize_w = 1;
	public double relsize_h = 1;
	
}
