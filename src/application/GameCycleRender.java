package application;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import utilities.Edge;
import utilities.Point;
import utilities.Shape;
import utilities.Size;
import utilities.Vector;

public class GameCycleRender {
	public SharedAttributes sa;
	/* PSEUDOCODE
	get vector points
	anchor vectors to the center of the shape
	get position on screen
	connect points
	*/
	/*
	
		Voxel[] voxels = new Voxel[] {
				new Voxel(edges[1], edges[2], edges[3], edges[4]),
				new Voxel(edges[4], edges[5], edges[6], edges[7])
		};
	*/
	double rotx = 0;
	double roty = Math.PI/4;//Math.PI/4
	double rotz = 0;
	public void drawComponent(Graphics g) {
		g.setColor(Color.BLACK);
		int offset = 300;
		int mult = 100;
		
		g.drawString("angleX=" + rotx, 10, 10+g.getFontMetrics().getAscent());
		rotx+=0.01f;
		if(rotx>2*Math.PI)
			rotx-=2*Math.PI;
		
		
		Vector center = new Vector(0.5, 0.5, 0.5);
		
		//Shape s = new Shape();
		Vector[] vectors = new Vector[] {
				new Vector(0,0,0), //0
				new Vector(0,1,0), //1
				new Vector(1,0,0), //2
				new Vector(1,1,0), //3
				
				new Vector(0,0,1), //4
				new Vector(0,1,1), //5
				new Vector(1,0,1), //6
				new Vector(1,1,1)  //7
		};
		Edge[] edges = new Edge[] {
				new Edge(vectors[0], vectors[1]),
				new Edge(vectors[1], vectors[3]),
				new Edge(vectors[0], vectors[2]),
				new Edge(vectors[2], vectors[3]),
				
				new Edge(vectors[4], vectors[5]),
				new Edge(vectors[5], vectors[7]),
				new Edge(vectors[4], vectors[6]),
				new Edge(vectors[6], vectors[7]),
				
				new Edge(vectors[0], vectors[4]),
				new Edge(vectors[1], vectors[5]),
				new Edge(vectors[2], vectors[6]),
				new Edge(vectors[3], vectors[7])
		};
		
		
		
		
		for(Vector vect : vectors) {
			Vector v = new Vector();
			Point p = screenLocation(vect.x, vect.y, center.x, center.y, rotx);
			//Point po = rotationOffset(1, 1, rotx);
			v.x+=p.x;
			v.y+=p.y;
			Point p1 = screenLocation(vect.x, vect.z, center.x, center.z, roty);
			//Point po1 = rotationOffset(1, 1, rotx);
			v.x+=p1.x; 
			v.z+=p1.y;
			Point p2 = screenLocation(vect.y, vect.z, center.y, center.z, rotz);
			//Point po2 = rotationOffset(1, 1, rotx);
			v.y+=p2.x;
			v.z+=p2.y;
			v = v.multiply(mult);
			g.drawOval((int)(offset+v.x-5), (int)(offset+v.y-5), 10, 10);
			vect.out = v;
		}
		for(Edge e : edges) {
			g.drawLine((int)(offset+e.v1.out.x), (int)(offset+e.v1.out.y), (int)(offset+e.v2.out.x), (int)(offset+e.v2.out.y));
		}
		
		
		
		
	}
	public Point screenLocation(double x, double y, double centerx, double centery, double rotation) {
		double localx = x - centerx;
		double localy = y - centery;
		double dist = Math.sqrt(Math.pow(localx, 2)+Math.pow(localy, 2));
		double localangle = 2*Math.atan(localy/(localx+dist));
		double globalangle = localangle+rotation;
		double xout = dist * Math.cos(globalangle);
		double yout = dist * Math.sin(globalangle);
		return new Point(centerx+xout, centery+yout);
	}
	public Point rotationOffset(double width, double height, double rotation) {
		double w2 = Math.cos(rotation)*width + Math.sin(rotation)*height;
		double h2 = Math.cos(rotation)*height + Math.sin(rotation)*width;
		return new Point(w2-width, h2-height);
	}
	

}
