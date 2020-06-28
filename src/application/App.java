package application;

import render.GameCycleRender;
import render.GameObject;
import render.CursorUtil;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import gameobject.entities.Player;
import gameobject.utilities.Transform;
import render.Game;

public class App extends JFrame {

	public static App app;
	public static Timer drawTimer;
	public static Timer drawCheckTimer;
	public static Timer gameTimer;
	public static GameCycleRunner cycleRunner;
	public static GameCycleRender cycleRender;
	public static CommandHandler commandHandler;
	public static MousePolling mousePoll;
	private static JPanel drawPanel;
	private static InputHandler inputHandler;
	private static SharedAttributes sharedAttributes;
	public static BufferedImage readyGraphics;

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		app = new App();
		app.initGUI();
		app.initBackend();
		app.setVisible(true);
	}

	void initBackend() {
		cycleRender = new GameCycleRender();
		cycleRunner = new GameCycleRunner();
		mousePoll = new MousePolling();
		commandHandler = new CommandHandler();

		InitializeGlobals();

		InitializeRender();
		InitializeTimers();

		inputHandler.init();
		cycleRunner.init();
		cycleRender.init();
	}

	void initGUI() {
		inputHandler = new InputHandler();
		addMouseListener(inputHandler);
		addKeyListener(inputHandler);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(new Dimension(GameSettings.windowSize.getWidth(), GameSettings.windowSize.getHeight()));
		setResizable(true);
	}

	private void InitializeGlobals() {
		Game game = new Game();
		//GameObject house = game.addObject("./src/res/object5.obj",1);
		GameObject van = game.addObject("./src/res/object.obj",1);
		//house.transformoffset = new Transform(0,0,0.5);

		Player player = new Player();

		sharedAttributes = new SharedAttributes();
		sharedAttributes.game = game;
		sharedAttributes.player = player;

		cycleRender.sa = sharedAttributes;
		cycleRunner.sa = sharedAttributes;
		inputHandler.sa = sharedAttributes;
		mousePoll.sa = sharedAttributes;
		commandHandler.sa = sharedAttributes;
	}

	private void InitializeRender() {
		CursorUtil.setCursorBlank();

		drawPanel = new JPanel() {
			private static final long serialVersionUID = 2L;
			
			@Override
			public void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D)g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
				g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
				g2.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
				
				g.setColor(new Color(150,150,150));
				g.fillRect(0, 0, GameSettings.windowSize.getWidth(), GameSettings.windowSize.getHeight());
				cycleRender.drawComponent(g);
			}
		};
		add(drawPanel);
	}

	void InitializeTimers() {
		gameTimer = new Timer();
		drawTimer = new Timer();
		drawCheckTimer = new Timer();
		gameTimer.scheduleAtFixedRate(cycleRunner, 0l, GameSettings.backend_refresh_rate);
		gameTimer.scheduleAtFixedRate(mousePoll, 0l, GameSettings.mousepoll_rate);
		drawTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				drawPanel.repaint();
			}

		}, 0l, (long) (1000 / GameSettings.refresh_rate));
	}
}