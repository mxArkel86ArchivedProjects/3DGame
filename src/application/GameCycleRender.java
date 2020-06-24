package application;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import render.GameObject;
import utilities.Point;
import utilities.Vector;
import utilities.Polygon;

public class GameCycleRender {
	public SharedAttributes sa;

	int mult = 40;
	int vectormax = 0;
	int voxelmax = -1;
	Point offset3 = new Point(400, 350);

	public void drawComponent(Graphics g) {
		long start = System.currentTimeMillis();

		g.setColor(Color.GREEN);

		ArrayList<GameObject> gameObjects = (ArrayList<GameObject>) (sa.game.gameObjects.clone());
		for (GameObject obj : gameObjects) {
			for (int i = 0; i < obj.polygons.size(); i++) {
				Polygon poly = obj.polygons.get(i);
				
				java.awt.Polygon p = new java.awt.Polygon();
				for (Vector v : poly.vectors) {
					if (v.out == null)
						return;
					p.addPoint((int) (offset3.x + v.out.x), (int) (offset3.y + v.out.y));
				}
				if (poly.c == null)
					poly.c = getColor();
				g.setColor(poly.c);
				g.fillPolygon(p);
			}
		}

		//look angles
		g.setColor(Color.RED);
		g.drawOval(20, 20, 100, 100);
		g.drawLine(70, 70, (int) (70 + Math.cos(sa.lookanglex) * 50), (int) (70 + Math.sin(sa.lookanglex) * -50));

		g.drawOval(20, 130, 100, 100);
		g.drawLine(70, 180, (int) (70 + Math.cos(sa.lookangley) * 50), (int) (180 + Math.sin(sa.lookangley) * -50));

		g.drawOval(60, 290, 20, 20);//70, 300
		g.drawOval((int)(65+sa.player.transform.x*10), (int)(295-sa.player.transform.y*10), 10, 10);
		
		
		long end = System.currentTimeMillis();
		sa.frontendcompletion = end - start;
		g.setFont(new Font("Arial", Font.BOLD, 16));
		g.setColor(Color.BLACK);
		g.drawString(String.format("playerpos= %.1f | %.1f | %.1f", sa.player.transform.x, sa.player.transform.y, sa.player.transform.z), 10, 20);
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

}
