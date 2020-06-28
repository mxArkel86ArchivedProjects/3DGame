package render;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import utilities.Polygon;
import utilities.Vector;

public class Game {

	public ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	public GameObject addObject(String path, double scale) {
		// read vectors,
		BufferedReader reader;
		GameObject obj = new GameObject();
		obj.scale = scale;
		ArrayList<String> lines = new ArrayList<String>();
		try {
			reader = new BufferedReader(new FileReader(path));
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (String line : lines) {
			if (line.startsWith("v ")) {

				line = line.substring(1);
				while (line.startsWith(" "))
					line = line.substring(1);
				String[] parts = line.split(" ");
				Vector v = new Vector(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]),
						Double.parseDouble(parts[2]));
				obj.vectors.add(v.multiply(obj.scale));
			}

		}
		for (String line : lines) {
			if (line.startsWith("f ")) {
				line = line.substring(2);
				String[] parts = line.split(" ");

				Polygon poly = new Polygon();
				for (int i = 0; i < parts.length; i++) {
					int vint = Math.abs(Integer.parseInt(parts[i].split("/")[0]) - 1);
					int tint = -1;
					if (parts[i].split("/").length > 1)
						tint = Math.abs(Integer.parseInt(parts[i].split("/")[1]) - 1);
					if (vint < obj.vectors.size()) {
						Vector v = obj.vectors.get(vint);
						if (tint != -1)
							v.textureMap = obj.vectors.get(tint);
						poly.addPolygon(v);
					}

				}
				obj.polygons.add(poly);
			}
		}
		for (Vector v : obj.vectors) {
			obj.center.x += v.x;
			obj.center.y += v.y;
			obj.center.z += v.z;
			if (v.x > obj.size.x)
				obj.size.x = v.x;
			if (v.y > obj.size.y)
				obj.size.y = v.y;
			if (v.z > obj.size.z)
				obj.size.z = v.z;
		}
		obj.center.x /= obj.vectors.size();
		obj.center.y /= obj.vectors.size();
		obj.center.z /= obj.vectors.size();
		gameObjects.add(obj);
		return obj;
	}
}
