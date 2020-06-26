package render;

import utilities.Size;
import java.util.ArrayList;

import gameobject.utilities.Transform;
import gameobject.utilities.Rotation;
import utilities.Vector;
import utilities.Polygon;

public class GameObject {
	public Transform transform = new Transform(0,0,0);
	public Rotation rotation = new Rotation(0, 0);
	public double scale = 1;
	public ArrayList<Polygon> polygons = new ArrayList<Polygon>();
	public ArrayList<Vector> vectors = new ArrayList<Vector>();

	public Vector center = new Vector();
	public Vector size = new Vector();
	public double maxZ_out = 0;
	public Size relative_size_out = new Size(0, 0);

}
