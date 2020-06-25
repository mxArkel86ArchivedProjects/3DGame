package gameobject.utilities;

import utilities.Polygon;
import utilities.Vector;

public class DepthPolygon implements Comparable<DepthPolygon> {
	public Polygon p;
	
	public DepthPolygon(Polygon p) {
		this.p = p;
	}
	public double avg() {
		double avgz = 0;
		for(Vector v : p.vectors)
			avgz+=v.out.z;
		avgz/=p.vectors.size();
		return avgz;
	}
	@Override
	public int compareTo(DepthPolygon p) {
		if(p.avg()>avg())
			return 1;
		else if (p.avg()==avg())
			return 0;
		else 
			return -1;
	}
}
