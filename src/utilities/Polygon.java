package utilities;

import java.awt.Color;
import java.util.ArrayList;

public class Polygon {
	public ArrayList<Vector> vectors = new ArrayList<Vector>();
	public Polygon(Vector...vectors) {
		for(Vector v : vectors)
			this.vectors.add(v);
	}
	public Polygon() {
		
	}
	public void addVector(Vector v) {
		vectors.add(v);
	}
	public Color c;
}
