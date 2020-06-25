package gameobject.utilities;

import java.awt.Color;

import utilities.Polygon;
import utilities.Vector;

public class DepthPolygon implements Comparable<DepthPolygon> {
	public Polygon p;
	public double avgz = 0;
	public DepthPolygon(Polygon p) {
		this.p = p;
		for(Vector v : p.vectors)
			avgz+=v.out.z;
		avgz/=p.vectors.size();
		
	}
	@Override
	public int compareTo(DepthPolygon p) {
		if(avgz>p.avgz)
			return 0;
		else
			return 1;
	}
}
