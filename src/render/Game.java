package render;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import utilities.Polygon;
import utilities.Vector;

public class Game {

	public Vector center = new Vector();
	public ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	
	public void addObject(String path, double scale) {
		BufferedReader reader;
		GameObject obj = new GameObject();
		obj.scale = scale;
		try {
			reader = new BufferedReader(new FileReader(path));
			String line = reader.readLine();
			while (line != null) {
				if (line.startsWith("v ")) {

					line = line.substring(1);
					while(line.startsWith(" "))
						line = line.substring(1);
					String[] parts = line.split(" ");
					Vector v = new Vector(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]),
							Double.parseDouble(parts[2]));
					obj.vectors.add(v.multiply(obj.scale));
				}
				if (line.startsWith("f ")) {
					line = line.substring(2);
					String[] parts = line.split(" ");

					Polygon poly = new Polygon();
					for (int i = 0; i < parts.length; i++) {
						int vint = Math.abs(Integer.parseInt(parts[i].split("/")[0]) - 1);
						if (vint < obj.vectors.size()) {
							Vector v = obj.vectors.get(vint);
							poly.addPolygon(v);
						}

					}
					obj.polygons.add(poly);
				}
				// ----
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (Vector v : obj.vectors) {
			obj.center.x += v.x;
			obj.center.y += v.y;
			obj.center.z += v.z;
			if(v.x>obj.size.x)
				obj.size.x=v.x;
			if(v.y>obj.size.y)
				obj.size.y=v.y;
			if(v.z>obj.size.z)
				obj.size.z=v.z;
		}
		obj.center.x /= obj.vectors.size();
		obj.center.y /= obj.vectors.size();
		obj.center.z /= obj.vectors.size();
		gameObjects.add(obj);
	}
}
