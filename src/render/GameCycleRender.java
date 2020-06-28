package render;

import application.GameSettings;
import application.SharedAttributes;
import java.awt.Color;
import java.awt.Font;
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

		drawPolygons(g);
		drawGUI(g);
		long end = System.currentTimeMillis();
		sa.frontendcompletion = end - start;
	}

	private void drawPolygons(Graphics g) {
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
				drawpolyg.addPoint((int) (v.out.x), (int) (v.out.y));// add vector pos to polygon
				if (new Point(v.out.x, v.out.y).within(GameSettings.windowSize))
					within = true;
			}
			if (!within) {
				polyCount--;
				continue;
			}
			if (poly.c == null)// decide polygon color
				poly.c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
			g.setColor(poly.c);
			g.fillPolygon(drawpolyg);// draw polygon
		}
	}

	void drawGUI(Graphics g) {
		// draw look angles
		g.setColor(Color.RED);

		int scale1 = 80;
		double ang1 = -p.rotation.x + p.camera_fov;
		double ang2 = -p.rotation.x - p.camera_fov;
		double ang1y = -p.rotation.y + p.camera_fov;
		double ang2y = -p.rotation.y - p.camera_fov;
		Point ppos = new Point(70 + sa.player.transform.x * scale1, 300 - sa.player.transform.y * scale1);
		Point objpos = new Point(60, 290);
		// draw camera angles
		g.drawLine(70, 70, (int) (70 + Math.cos(p.rotation.x) * scale1 / 2),
				(int) (70 + Math.sin(p.rotation.x) * -scale1/2));
		g.drawLine(70, 180, (int) (70 + Math.cos(p.rotation.y) * scale1 / 2),
				(int) (180 + Math.sin(p.rotation.y) * -scale1 / 2));
		g.drawOval(20, 130, 100, 100);
		g.drawOval(20, 20, 100, 100);

		GameObject o = sa.game.gameObjects.get(0);
		g.setFont(new Font("Arial", Font.BOLD, 10));
		// draw object
		g.setColor(Color.BLUE);
		g.drawOval(objpos.getX() - 10, objpos.getY() - 10, 20, 20);// 70, 300
		g.setColor(Color.GREEN);
		g.drawLine(ppos.getX(), ppos.getY(), objpos.getX(), ppos.getY());
		g.drawString("y=" + o.diff.y, objpos.getX() + 5,
				(objpos.getY() + ppos.getY()) / 2 + g.getFontMetrics().getAscent() / 2);
		g.setColor(Color.ORANGE);
		g.drawLine(objpos.getX(), objpos.getY(), objpos.getX(), ppos.getY());
		g.drawString("x=" + o.diff.x,
				(int) ((-objpos.getX() + ppos.getX()) / 2
						+ g.getFontMetrics().getStringBounds("x=" + (int) o.diff.x, g).getWidth() / 2) + 5,
				ppos.getY() + g.getFontMetrics().getAscent() + 5);
		// draw player
		g.setColor(Color.BLUE);
		g.drawOval(ppos.getX() - 5, ppos.getY() - 5, 10, 10);

		Point obj1 = new Point(60, 650);

		Point ppos2 = new Point((int) (obj1.getX() + o.dist2d * scale1), (int) (obj1.getY() + o.diff.z * scale1));
		// draw z plane
		g.drawOval(obj1.getX() - 10, obj1.getY() - 10, 20, 20);// 70, 300
		g.setColor(Color.GREEN);
		g.drawLine(obj1.getX(), (int) (obj1.getY() + o.diff.z * scale1), ppos2.getX(), ppos2.getY());
		g.drawString("z=" + o.diff.z, obj1.getX() + 5,
				(obj1.getY() + ppos2.getY()) / 2 + g.getFontMetrics().getAscent() / 2);
		g.setColor(Color.ORANGE);
		g.drawLine(obj1.getX(), obj1.getY(), obj1.getX(), (int) (obj1.getY() + o.diff.z * scale1));
		g.drawString("dist=" + o.dist2d,
				(int) ((-obj1.getX() + ppos2.getX()) / 2
						+ g.getFontMetrics().getStringBounds("x=" + (int) o.dist2d, g).getWidth() / 2) + 5,
				ppos2.getY() + g.getFontMetrics().getAscent() + 5);
		g.setColor(Color.BLUE);
		g.drawOval(ppos2.getX() - 5, ppos2.getY() - 5, 10, 10);

		// draw player fov indicators
		g.setColor(Color.YELLOW);
		g.drawLine(ppos.getX(), ppos.getY(), ppos.getX() + (int) (Math.cos(ang1) * scale1 / 2),
				ppos.getY() + (int) (Math.sin(ang1) * scale1 / 2));
		g.drawLine(ppos.getX(), ppos.getY(), ppos.getX() + (int) (Math.cos(ang2) * scale1 / 2),
				ppos.getY() + (int) (Math.sin(ang2) * scale1 / 2));

		g.drawLine(ppos2.getX(), ppos2.getY(), ppos2.getX() + (int) (Math.cos(ang1y) * scale1 / 2),
				ppos2.getY() + (int) (Math.sin(ang1y) * scale1 / 2));
		g.drawLine(ppos2.getX(), ppos2.getY(), ppos2.getX() + (int) (Math.cos(ang2y) * scale1 / 2),
				ppos2.getY() + (int) (Math.sin(ang2y) * scale1 / 2));

		// draw debug messages
		g.setFont(new Font("Arial", Font.BOLD, 18));
		g.setColor(Color.BLACK);
		g.drawString(String.format("playerpos= %.1f | %.1f | %.1f", p.transform.x, p.transform.y, p.transform.z), 10,
				20);
		g.drawString(String.format("backend completion time= %.0f/%d", (float) (sa.backendcompletion),
				GameSettings.backend_refresh_rate), 10, 80);
		g.drawString(String.format("frontend completion time= %.0f/%.1f", (float) (sa.frontendcompletion),
				1000 / GameSettings.refresh_rate), 10, 110);
		g.drawString(String.format("lookangles= x:%.2f y:%.2f", p.rotation.x, p.rotation.y), 10, 140);
		g.drawString("polyCount=" + polyCount, 10, 170);
		g.drawString("CFS=" + sa.cfs, 10, 200);
		g.drawString(String.format("object rotations=%.2f y:%.2f", o.relative_rotation_out.x, o.relative_rotation_out.y), 10, 230);

		if (sa.consoleup) {
			g.setFont(new Font("Arial", Font.ITALIC, 20));
			int consoleh = 100;
			g.setColor(new Color(0,0,0,170));
			g.fillRect(0, GameSettings.windowSize.getHeight() - consoleh, GameSettings.windowSize.getWidth(), consoleh);
			g.setColor(new Color(255,255,255,170));
			g.drawString(sa.consoleLine, 10,
					GameSettings.windowSize.getHeight() - consoleh + g.getFontMetrics().getAscent()+10);
		}
	}

	ArrayList<DepthPolygon> orderPolygons(ArrayList<Polygon> polygons) {
		ArrayList<DepthPolygon> dpolys = new ArrayList<DepthPolygon>();

		for (Polygon p : polygons) {// convert
			dpolys.add(new DepthPolygon(p));
		}
		// sort
		try {
			Collections.sort(dpolys);
		} catch (Exception e) {

		}

		return dpolys;
	}

}
