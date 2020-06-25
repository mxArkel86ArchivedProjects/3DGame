package application;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import gameobject.entities.Player;
import gameobject.utilities.DepthPolygon;
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
	public boolean draw = false;
	
	public void init() {
		draw = true;
	}
	
	

	public void drawComponent(Graphics2D g) {
		if(!draw)return;
		long start = System.currentTimeMillis();
		Player p = sa.player;
		
		g.setFont(new Font("Arial", Font.BOLD, 16));
		
		ArrayList<GameObject> gameObjects = (ArrayList<GameObject>) (sa.game.gameObjects.clone());
		for (GameObject obj : gameObjects) {
			ArrayList<DepthPolygon> polygons = obj.orderedPolygons();

			for (int i = 0; i < polygons.size(); i++) {
				DepthPolygon dpoly = polygons.get(i);
				Polygon poly = dpoly.p;
				
				java.awt.Polygon drawpolyg = new java.awt.Polygon();
				for (Vector v : poly.vectors) {
					if (v.out == null)
						return;
					drawpolyg.addPoint((int) (v.out.x), (int) (v.out.y));
				}
				double avg = 0;
				for(Vector v : poly.vectors)
					avg+=v.out.z;
				avg/=poly.vectors.size();
				int a = (int)(((obj.max-avg)*255)/(obj.max));
				try {
				g.setColor(new Color(a, a, a));
				}catch(Exception e) {
					
				}
				g.fillPolygon(drawpolyg);
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
		
		g.setColor(Color.BLACK);
		g.drawString(String.format("playerpos= %.1f | %.1f | %.1f", p.transform.x, p.transform.y, p.transform.z), 10, 20);
		g.drawString(String.format("backend completion time= %.0f/%d", (float) (sa.backendcompletion),
				GameSettings.backend_refresh_rate), 10, 80);
		g.drawString(String.format("frontend completion time= %.0f/%.1f", (float) (sa.frontendcompletion),
				1000 / GameSettings.refresh_rate), 10, 110);
		g.drawString(String.format("lookangles= x:%.2f y:%.2f", p.lookanglex, p.lookangley), 10, 140);
	}

}
