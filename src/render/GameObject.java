package render;

import utilities.Size;
import java.util.ArrayList;

import gameobject.utilities.Transform;
import gameobject.utilities.Rotation;
import utilities.Vector;
import utilities.Polygon;

public class GameObject {
	public Transform transformoffset = new Transform(0,0,0);
	public Rotation rotationoffset = new Rotation(0, 0);
	public double scale = 1;
	public ArrayList<Polygon> polygons = new ArrayList<Polygon>();
	public ArrayList<Vector> vectors = new ArrayList<Vector>();
	public ArrayList<Material> materials = new ArrayList<Material>();

	public Vector center = new Vector();
	public Vector size = new Vector();
	public double maxZ_out = 0;
	public Size relative_size_out = new Size(0, 0);
	public Rotation relative_rotation_out = new Rotation(0,0);
	public Vector diff;
	public double dist2d = 0;

}
