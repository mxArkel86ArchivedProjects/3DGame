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
import java.util.Random;

import utilities.Point;
import utilities.Vector;
import utilities.Voxel;

public class GameCycleRender {
	public SharedAttributes sa;
	
	int mult =40;
	int vectormax = 0;
	int voxelmax = -1;
	Point offset = new Point(100, 1 * mult);
	Point offset2 = new Point(100, 0);
	Point offset4 = new Point(100, 2 * mult);
	Point offset3 = new Point(400, 350);


	public void drawComponent(Graphics g) {
		long start = System.currentTimeMillis();
		
		g.setColor(Color.GREEN);
		
		ArrayList<Voxel> voxels = (ArrayList<Voxel>)(sa.voxels.clone());
		//ArrayList<Vector> vectors = (ArrayList<Vector>)(sa.vectors.clone());
		for (int i = 0;(i<voxelmax&&i<voxels.size())||(voxelmax==-1&&i<voxels.size());i++) {
			Voxel vox = voxels.get(i);
			Polygon p = new Polygon();
			for(Vector v : vox.vectors) {
				if(v.out==null)
					return;
				p.addPoint((int) (offset3.x + v.out.x), (int) (offset3.y + v.out.y));
			}
			if(vox.c==null)
				vox.c = getColor();
			g.setColor(vox.c);
			g.fillPolygon(p);
		}
		
		/*g.setColor(Color.RED);
		for (int i = 0;i<vectormax&&i<vectors.size();i++) {
			Vector vect = vectors.get(i);
			fillOval(g, (int) (offset3.x + vect.out.x - 1), (int) (offset3.y + vect.out.z - 1), 2, 2);
		}*/
		
		long end = System.currentTimeMillis();
		sa.frontendcompletion = end-start;
		g.setFont(new Font("Arial", Font.BOLD, 16));
		g.setColor(Color.BLACK);
		g.drawString(String.format("rotationx= %.2f | %.2f", sa.rotationx, Math.toDegrees(sa.rotationx)), 10, 20);
		g.drawString(String.format("rotationy= %.2f | %.2f", sa.rotationy, Math.toDegrees(sa.rotationy)), 10, 50);
		g.drawString(String.format("backend completion time= %.0f/%d", (float)(sa.backendcompletion), GameSettings.backend_refresh_rate), 10, 80);
		g.drawString(String.format("frontend completion time= %.0f/%.1f", (float)(sa.frontendcompletion), 1000/GameSettings.refresh_rate), 10, 110);
		g.drawString(String.format("lookangles= x:%.2f y:%.2f", sa.lookanglex, sa.lookangley), 10, 140);
		
		g.drawString(String.format("fps= %d", sa.fps), 10, 170);
		
	}

	private Color getColor() {
		int r = new Random().nextInt(255);
		int g = new Random().nextInt(255);
		int b = new Random().nextInt(255);
		return new Color(r, g, b);
	}
	private void fillOval(Graphics g, int i, int j, int k, int l) {
		g.fillOval(i, j, k, l);
	}

	
	public Point rotationOffset(double width, double height, double rotation) {
		double w2 = Math.cos(rotation) * width + Math.sin(rotation) * height;
		double h2 = Math.cos(rotation) * height + Math.sin(rotation) * width;
		return new Point(w2 - width, h2 - height);
	}

	public void init() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("./src/res/object4.obj"));
			String line = reader.readLine();
			while (line != null) {
					if (line.startsWith("v ")) {

						line = line.substring(2);
						String[] parts = line.split(" ");
						Vector v = new Vector(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]),
								Double.parseDouble(parts[2]));
						sa.vectors.add(v.multiply(mult));
					}
					if (line.startsWith("f ")) {
						line = line.substring(2);
						String[] parts = line.split(" ");
						
						Voxel vox = new Voxel();
						for (int i = 0; i < parts.length; i++) {
							int vint = Math.abs(Integer.parseInt(parts[i].split("/")[0]) - 1);
							if(vint<sa.vectors.size()) {
							Vector v = sa.vectors.get(vint);
							vox.addVector(v);
							}

						}
						sa.voxels.add(vox);
					}
				// ----
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (Vector v : sa.vectors) {
			sa.center.x += v.x;
			sa.center.y += v.y;
			sa.center.z += v.z;
		}
		sa.center.x /= sa.vectors.size();
		sa.center.y /= sa.vectors.size();
		sa.center.z /= sa.vectors.size();
	}

}
