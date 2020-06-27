package render;

import application.GameSettings;
import application.SharedAttributes;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import gameobject.entities.Player;
import gameobject.utilities.DepthPolygon;
import render.GameObject;
import utilities.Point;
import utilities.Polygon;
import utilities.Vector;

public class GameCycleRender {
	public SharedAttributes sa;
	public boolean draw = false;
	Player p;
	Random r = new Random();
	
	
	
	int polyCount;

	public void init() {
		p = sa.player;
		draw = true;
	}

	public void drawComponent(Graphics g) {
		if (!draw)
			return;
		long start = System.currentTimeMillis();
		
		//add polygons to be processed based on average depth
		ArrayList<Polygon> polygonsIn = new ArrayList<Polygon>();
		for (GameObject obj : sa.game.gameObjects) {
			polygonsIn.addAll(obj.polygons);
		}
		polyCount = polygonsIn.size();
		ArrayList<DepthPolygon> polygons = orderPolygons(polygonsIn);
		for (int i = 0; i < polygons.size(); i++) {
			DepthPolygon dpoly = polygons.get(i);
			Polygon poly = dpoly.p;

			java.awt.Polygon drawpolyg = new java.awt.Polygon();
			boolean within = false;
			for (Vector v : poly.vectors) {
				drawpolyg.addPoint((int) (v.out.x), (int) (v.out.y));//add vector pos to polygon
				if(new Point(v.out.x, v.out.y).within(GameSettings.windowSize))
					within = true;
			}
			if(!within) {
				polyCount--;
				continue;
			}
			if (poly.c == null)//decide polygon color
				poly.c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
			g.setColor(poly.c);
			g.fillPolygon(drawpolyg);//draw polygon
		}
	

	drawGUI(g);
	long end = System.currentTimeMillis();
	sa.frontendcompletion = end - start;
	}
	void drawGUI(Graphics g){
		//draw look angles
		g.setColor(Color.RED);
		g.drawOval(20, 20, 100, 100);
		g.drawLine(70, 70, (int) (70 + Math.cos(p.rotation.x) * 50), (int) (70 + Math.sin(p.rotation.x) * -50));

		g.drawOval(20, 130, 100, 100);
		g.drawLine(70, 180, (int) (70 + Math.cos(p.rotation.y) * 50), (int) (180 + Math.sin(p.rotation.y) * -50));

		//draw player positioning and camera angles
		g.drawOval(60, 290, 20, 20);// 70, 300
		Point ppos = new Point(70 + sa.player.transform.x * 50, 300 - sa.player.transform.y * 50);
		g.drawOval(ppos.getX() - 5, ppos.getY() - 5, 10, 10);
		double ang1 = -p.rotation.x + p.camera_fov;
		double ang2 = -p.rotation.x - p.camera_fov;
		g.fillRect(10, 180, 50, 5);
		g.setColor(Color.BLUE);
		g.fillRect(10, 180+(int)((p.transform.z-sa.game.gameObjects.get(0).transform.z)*10), 50, 5);
		g.setColor(Color.GREEN);
		g.drawLine(ppos.getX(), ppos.getY(), ppos.getX() + (int) (Math.cos(ang1) * 50),
				ppos.getY() + (int) (Math.sin(ang1) * 50));
		g.drawLine(ppos.getX(), ppos.getY(), ppos.getX() + (int) (Math.cos(ang2) * 50),
				ppos.getY() + (int) (Math.sin(ang2) * 50));


		//draw debug messages
		g.setColor(Color.BLACK);
		g.drawString(String.format("playerpos= %.1f | %.1f | %.1f", p.transform.x, p.transform.y, p.transform.z), 10, 20);
		g.drawString(String.format("backend completion time= %.0f/%d", (float) (sa.backendcompletion),
				GameSettings.backend_refresh_rate), 10, 80);
		g.drawString(String.format("frontend completion time= %.0f/%.1f", (float) (sa.frontendcompletion),
				1000 / GameSettings.refresh_rate), 10, 110);
		g.drawString(String.format("lookangles= x:%.2f y:%.2f", p.rotation.x, p.rotation.y), 10, 140);
		g.drawString("polyCount=" + polyCount, 10, 170);
		g.drawString("CFS=" + sa.cfs, 10, 200);
		
	}

	 ArrayList<DepthPolygon> orderPolygons(ArrayList<Polygon> polygons) {
		ArrayList<DepthPolygon> dpolys = new ArrayList<DepthPolygon>();

		for (Polygon p : polygons) {//convert
			dpolys.add(new DepthPolygon(p));
		}
		//sort
		try {
		Collections.sort(dpolys);
		}catch(Exception e){
			
		}

		return dpolys;
	}

}
