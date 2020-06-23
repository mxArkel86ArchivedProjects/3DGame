package application;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
	ArrayList<Vector> vectors = new ArrayList<Vector>();
	ArrayList<Edge> edges = new ArrayList<Edge>();
	Vector center = new Vector();
	int mult = 1;
	int conn = 2000;
	Point offset = new Point(100, 1*mult);
	Point offset2 = new Point(100, 0);
	Point offset4 = new Point(100, 2*mult);
	Point offset3 = new Point(400, 300);
	public GameCycleRender() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(
					"./src/res/object5.obj"));
			String line = reader.readLine();
			while (line != null) {
				if(edges.size()<conn) {
				if(line.startsWith("v ")) {
					
					line = line.substring(3);
					String[] parts = line.split(" ");
					Vector v = new Vector(Double.parseDouble(parts[0]),Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
					vectors.add(v.multiply(mult));
				}
				if(line.startsWith("f ")) {
					line = line.substring(2);
					String[] parts = line.split(" ");
					
					for(int i = 0;i<parts.length;i++) {
						try {
						int vint = Math.abs(Integer.parseInt(parts[i].split("/")[0])-1);
						Vector v = vectors.get(vint);
						Vector v2;
						if(vint>parts.length)
							v2 = vectors.get(Math.abs(Integer.parseInt(parts[0].split("/")[0])-1));
						else
							v2 = vectors.get(vint+1);
						Edge e = new Edge(v, v2);
						edges.add(e);
						}catch(Exception e) {
							
						}
					}
					
					
				}
				}
				//----
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		center = new Vector();
		for(Vector v : vectors) {
			center.x+=v.x;
			center.y+=v.y;
			center.z+=v.z;
		}
		center.x/=vectors.size();
		center.y/=vectors.size();
		center.z/=vectors.size();
	}
	public void drawComponent(Graphics g) {
		
		//Shape s = new Shape();
		
		/*
		 Vector[] vectors = new Vector[] {
				new Vector(0.5,0.5,0), //0
				
				new Vector(0,1,1), //0
				new Vector(1,1,1), //0
				new Vector(1,0,1), //0
				new Vector(0,0,1), //0
		};
		 Edge[] edges = new Edge[] {
				new Edge(vectors[0], vectors[1]),
				new Edge(vectors[0], vectors[2]),
				new Edge(vectors[0], vectors[3]),
				new Edge(vectors[0], vectors[4]),
				
				new Edge(vectors[1], vectors[2]),
				new Edge(vectors[2], vectors[3]),
				new Edge(vectors[3], vectors[4]),
				new Edge(vectors[4], vectors[1]),
		};*/
		
		
		
		g.setFont(new Font("Arial", Font.BOLD, 12));
		g.setColor(Color.BLACK);
		/*for(Edge e : edges) {
			Vector v1 = e.v1.multiply(mult);
			Vector v2 = e.v2.multiply(mult);
			drawLine(g, (int)(offset.x+v1.y), (int)(offset.y+v1.z), (int)(offset.x+v2.y), (int)(offset.y+v2.z));
		}
		for(Vector vect : vectors) {
			Vector v = vect.multiply(mult);
			fillOval(g, (int)(offset.x+v.y-5), (int)(offset.y+v.z-5), 10, 10);
		}
		for(Edge e : edges) {
			Vector v1 = e.v1.multiply(mult);
			Vector v2 = e.v2.multiply(mult);
			drawLine(g, (int)(offset4.x+v1.x), (int)(offset4.y+v1.z), (int)(offset4.x+v2.x), (int)(offset4.y+v2.z));
		}
		for(Vector vect : vectors) {
			Vector v = vect.multiply(mult);
			fillOval(g, (int)(offset4.x+v.x-5), (int)(offset4.y+v.z-5), 10, 10);
		}
		//top view
		for(Edge e : edges) {
			Vector v1 = e.v1.multiply(mult);
			Vector v2 = e.v2.multiply(mult);
			drawLine(g, (int)(offset2.x+v1.x), (int)(offset2.y+v1.y), (int)(offset2.x+v2.x), (int)(offset2.y+v2.y));
		}
		for(Vector vect : vectors) {
			Vector v = vect.multiply(mult);
			fillOval(g, (int)(offset2.x+v.x-5), (int)(offset2.y+v.y-5), 10, 10);
		}*/
		
		
		
		for(Vector vect : vectors) {
			vect.out = screenLocation3D(vect.x, vect.y, vect.z, center.x, center.y, center.z, sa.rotationx, sa.rotationy);
		}
		g.setColor(Color.GREEN);
		Polygon p = new Polygon();
		for(Edge e : edges) {

            p.addPoint((int)(offset3.x+e.v1.out.x), (int)(offset3.y+e.v1.out.z));
		}
		g.drawPolygon(p);
		g.setColor(Color.GREEN);
		for(Vector vect : vectors) {
			fillOval(g, (int)(offset3.x+vect.out.x-1), (int)(offset3.y+vect.out.z-1), 2, 2);
		}
		
	}
	private void fillOval(Graphics g, int i, int j, int k, int l) {
		g.setColor(Color.GREEN);
		g.fillOval(i, j, k, l);
	}
	private void drawLine(Graphics g, int i, int j, int k, int l) {
		g.setColor(Color.BLUE);
		Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5));
        g2.draw(new Line2D.Float(i, j, k, l));
	}
	public Point screenLocation(double x, double y, double centerx, double centery, double rotation) {
		double localx = x - centerx;//get location centered at the origin
		double localy = y - centery;
		double dist = Math.sqrt(Math.pow(localx, 2)+Math.pow(localy, 2));//get the distance from the origin
		double localangle = 2*Math.atan(localy/(localx+dist));//get the angle from the origin on an axis
		double globalangle = localangle+rotation;//add the local angle pertaining to the point with the input theta
		double xout = dist * Math.cos(globalangle);//get the new position x and y from origin
		double yout = dist * Math.sin(globalangle);
		return new Point(centerx+xout, centery+yout);
	}
	public Vector screenLocation3D(double x, double y, double z, double centerx, double centery, double centerz, double rotationx, double rotationy) {
		Vector v = new Vector();
		
		double localx = x - centerx;//get location centered at the origin
		double localy = y - centery;
		double localz = z - centerz;
		
        double sinX = Math.sin(rotationx);
        double cosX = Math.cos(rotationx);
 
        double sinY = Math.sin(rotationy);
        double cosY = Math.cos(rotationy);
        
        v.x = localx * cosX - localz * sinX;
        v.z = localz * cosX + localx * sinX;

        localz = v.z;

        v.y = localy * cosY - localz * sinY;
        v.z = localz * cosY + localy * sinY;
		//System.out.println(xout);
		return v.add(new Vector(centerx, centery, centerz));
	}
	public Point rotationOffset(double width, double height, double rotation) {
		double w2 = Math.cos(rotation)*width + Math.sin(rotation)*height;
		double h2 = Math.cos(rotation)*height + Math.sin(rotation)*width;
		return new Point(w2-width, h2-height);
	}
	

}
