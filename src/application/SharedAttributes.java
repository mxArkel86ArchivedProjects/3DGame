package application;

import gameobject.entities.Player;
import render.Game;
import utilities.KeyInput;

public class SharedAttributes implements Cloneable {

	public long backendcompletion = 0;
	public long frontendcompletion = 0;
	public boolean consoleup = false;
	public String consoleLine = "";

	public Game game;
	public Player player;

	public KeyInput keyInput = new KeyInput();
	public double cfs;//completion frame score (how well performance is doing compared to set fps)

	protected Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }

}
