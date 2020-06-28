package application;

import java.util.ArrayList;

import gameobject.utilities.Rotation;
import gameobject.utilities.Transform;

public class CommandHandler {
	public SharedAttributes sa;

	public boolean executeCommand(String command, ArrayList<String> args) {
		switch (command) {
		case "tp":
			double x = Double.parseDouble(args.get(0));
			double y = Double.parseDouble(args.get(1));
			double z = Double.parseDouble(args.get(2));
			sa.player.transform = new Transform(x, y, z);
			return true;
		case "rot":
			double xrot = Double.parseDouble(args.get(0));
			double yrot = Double.parseDouble(args.get(1));
			sa.player.rotation = new Rotation(xrot, yrot);
			return true;
		}
		return false;
	}
}
