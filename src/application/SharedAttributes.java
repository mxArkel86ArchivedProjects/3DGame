package application;

import gameobject.entities.Player;
import render.Game;
import utilities.KeyInput;

public class SharedAttributes implements Cloneable {

	public long backendcompletion = 0;
	public long frontendcompletion = 0;

	public Game game;
	public Player player;

	public KeyInput keyInput = new KeyInput();

	protected Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }

}
