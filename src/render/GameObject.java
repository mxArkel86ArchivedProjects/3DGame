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
	public ArrayList<Polygon> polygons = new ArrayList<Polygon>();
	public ArrayList<Vector> vectors = new ArrayList<Vector>();
	public boolean onScreen = false;
	
	public Vector center = new Vector();
	public Vector size = new Vector();
	public double max = 0;
	public double relsize = 1;
	public ArrayList<Polygon> orderedPolygons() {
		ArrayList<Polygon> fin = new ArrayList<Polygon>();
		ArrayList<DepthPolygon> dpolys = new ArrayList<DepthPolygon>();
		for(Polygon p : polygons) {
			dpolys.add(new DepthPolygon(p));
		}
		Collections.sort(dpolys);
		for(DepthPolygon dp : dpolys)
			fin.add(dp.p);
		
		return fin;
	}
}
