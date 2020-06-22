package application;

import java.util.ArrayList;

import utilities.Edge;
import utilities.Vector;

public class Voxel {
	public ArrayList<Edge> edges = new ArrayList<Edge>();
	public void addEdge(Edge e) {
		edges.add(e);
	}
	public Voxel(Edge...edges) {
		for(Edge e : edges)
			addEdge(e);
	}
}
