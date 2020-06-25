package application;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import gameobject.entities.Player;
import render.GameObject;
import utilities.Point;
import utilities.Polygon;
import utilities.Vector;

public class GameCycleRender {
	public SharedAttributes sa;

	int mult = 40;
	int vectormax = 0;
	int voxelmax = -1;
	Point offset3 = new Point(100, 100);
	
	

	public void drawComponent(Graphics g) {
		if(!App.app.drawReady)
			return;
		long start = System.currentTimeMillis();
		Player p = sa.player;
		g.setColor(Color.GREEN);

		ArrayList<GameObject> gameObjects = (ArrayList<GameObject>) (sa.game.gameObjects.clone());
		for (GameObject obj : gameObjects) {
			ArrayList<Polygon> polygons = obj.orderedPolygons();
			for (int i = 0; i < polygons.size(); i++) {
				Polygon poly = polygons.get(i);
				
				java.awt.Polygon polyg = new java.awt.Polygon();
				for (Vector v : poly.vectors) {
					if (v.out == null)
						return;
					polyg.addPoint((int) (v.out.x), (int) (v.out.y));
				}
				double avg = 0;
				for(Vector v : poly.vectors)
					avg+=v.out.z;
				avg/=poly.vectors.size();
				int a = (int)(((obj.max-avg)*255)/(obj.max));
				try {
				g.setColor(new Color(a, a, a));
				}catch(Exception e) {
					g.setColor(Color.RED);
				}
				g.fillPolygon(polyg);
			}
		}

		//look angles
		g.setColor(Color.RED);
		g.drawOval(20, 20, 100, 100);
		g.drawLine(70, 70, (int) (70 + Math.cos(p.lookanglex) * 50), (int) (70 + Math.sin(p.lookanglex) * -50));

		g.drawOval(20, 130, 100, 100);
		g.drawLine(70, 180, (int) (70 + Math.cos(p.lookangley) * 50), (int) (180 + Math.sin(p.lookangley) * -50));

		g.drawOval(60, 290, 20, 20);//70, 300
		Point ppos = new Point(70+sa.player.transform.x*50, 300-sa.player.transform.y*50);
		g.drawOval(ppos.getX()-5, ppos.getY()-5, 10, 10);
		double ang1 = -p.lookanglex+p.fov_horizontal;
		double ang2 = -p.lookanglex-p.fov_horizontal;
		g.drawLine(ppos.getX(), ppos.getY(), ppos.getX()+(int)(Math.cos(ang1)*50), ppos.getY()+(int)(Math.sin(ang1)*50));
		g.drawLine(ppos.getX(), ppos.getY(), ppos.getX()+(int)(Math.cos(ang2)*50), ppos.getY()+(int)(Math.sin(ang2)*50));
		
		
		
		long end = System.currentTimeMillis();
		sa.frontendcompletion = end - start;
		g.setFont(new Font("Arial", Font.BOLD, 16));
		g.setColor(Color.BLACK);
		g.drawString(String.format("playerpos= %.1f | %.1f | %.1f", p.transform.x, p.transform.y, p.transform.z), 10, 20);
		g.drawString(String.format("backend completion time= %.0f/%d", (float) (sa.backendcompletion),
				GameSettings.backend_refresh_rate), 10, 80);
		g.drawString(String.format("frontend completion time= %.0f/%.1f", (float) (sa.frontendcompletion),
				1000 / GameSettings.refresh_rate), 10, 110);
		g.drawString(String.format("lookangles= x:%.2f y:%.2f", p.lookanglex, p.lookangley), 10, 140);
		g.drawString(String.format("fps= %d", sa.fps), 10, 170);
		g.drawString("within=" + sa.game.gameObjects.get(0).onScreen, 10, 200);

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
