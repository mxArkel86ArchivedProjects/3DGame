package application;

import java.util.ArrayList;

import utilities.KeyInput;
import utilities.Vector;
import utilities.Voxel;

public class SharedAttributes {
	public double rotationx = 0;
	public double rotationy = 0;
	
	public double lookanglex = 0;
	public double lookangley = 0;
	public long backendcompletion = 0;
	public long frontendcompletion = 0;
	public int fps = 0;
	
	public KeyInput keyInput = new KeyInput();
	public ArrayList<Vector> vectors = new ArrayList<Vector>();
	public ArrayList<Voxel> voxels = new ArrayList<Voxel>();
	public Vector center = new Vector();
}
