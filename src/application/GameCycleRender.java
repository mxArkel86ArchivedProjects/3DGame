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

import render.GameObject;
import utilities.Point;
import utilities.Vector;
import utilities.Voxel;

public class GameCycleRender {
	public SharedAttributes sa;

	int mult = 40;
	int vectormax = 0;
	int voxelmax = -1;
	Point offset = new Point(100, 1 * mult);
	Point offset2 = new Point(100, 0);
	Point offset4 = new Point(100, 2 * mult);
	Point offset3 = new Point(400, 350);

	public void drawComponent(Graphics g) {
		long start = System.currentTimeMillis();

		g.setColor(Color.GREEN);

		ArrayList<GameObject> gameObjects = (ArrayList<GameObject>) (sa.game.gameObjects.clone());
		for (GameObject obj : gameObjects) {
			// ArrayList<Vector> vectors = (ArrayList<Vector>)(sa.vectors.clone());
			for (int i = 0; i < obj.voxels.size(); i++) {
				Voxel vox = obj.voxels.get(i);
				Polygon p = new Polygon();
				for (Vector v : vox.vectors) {
					if (v.out == null)
						return;
					p.addPoint((int) (offset3.x + v.out.x), (int) (offset3.y + v.out.y));
				}
				if (vox.c == null)
					vox.c = getColor();
				g.setColor(vox.c);
				g.fillPolygon(p);
			}
		}

		g.setColor(Color.RED);
		g.drawOval(20, 20, 100, 100);
		g.drawLine(70, 70, (int) (70 + Math.cos(sa.lookanglex) * 50), (int) (70 + Math.sin(sa.lookanglex) * -50));

		g.drawOval(20, 130, 100, 100);
		g.drawLine(70, 180, (int) (70 + Math.cos(sa.lookangley) * 50), (int) (180 + Math.sin(sa.lookangley) * -50));

		long end = System.currentTimeMillis();
		sa.frontendcompletion = end - start;
		g.setFont(new Font("Arial", Font.BOLD, 16));
		g.setColor(Color.BLACK);
		g.drawString(String.format("rotationx= %.2f | %.2f", sa.rotationx, Math.toDegrees(sa.rotationx)), 10, 20);
		g.drawString(String.format("rotationy= %.2f | %.2f", sa.rotationy, Math.toDegrees(sa.rotationy)), 10, 50);
		g.drawString(String.format("backend completion time= %.0f/%d", (float) (sa.backendcompletion),
				GameSettings.backend_refresh_rate), 10, 80);
		g.drawString(String.format("frontend completion time= %.0f/%.1f", (float) (sa.frontendcompletion),
				1000 / GameSettings.refresh_rate), 10, 110);
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
		
	}

}
