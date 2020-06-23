package utilities;

import java.awt.Color;
import java.util.ArrayList;

public class Voxel {
	public ArrayList<Vector> vectors = new ArrayList<Vector>();
	public Voxel(Vector...vectors) {
		for(Vector v : vectors)
			this.vectors.add(v);
	}
	public Voxel() {
		
	}
	public void addVector(Vector v) {
		vectors.add(v);
	}
	public Color c;
}
