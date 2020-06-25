package application;

import gameobject.entities.Player;
import render.Game;
import utilities.KeyInput;

public class SharedAttributes {
	

	public long backendcompletion = 0;
	public long frontendcompletion = 0;
	public int fps = 0;
	
	public Game game;
	
	public Player player;
	
	public KeyInput keyInput = new KeyInput();

}
