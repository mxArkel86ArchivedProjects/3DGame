package application;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import gameobject.entities.Player;
import render.Game;

public class App extends JFrame {

	public static App app;
	public static Timer drawTimer;
	public static Timer gameTimer;
	public static GameCycleRunner cycleRunner;
	public static GameCycleRender cycleRender;
	public static MousePolling mousePoll;
	private static Game game;
	private static JPanel drawPanel;
	private static InputHandler inputHandler;
	private static SharedAttributes sharedAttributes;
	public static boolean drawReady = false;

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
        app = new App();
        app.initGUI();
        app.ShowApp();
        
    }

	public void ShowApp() {
		setVisible(true);
	}

	public void initGUI() {
		inputHandler = new InputHandler();
		addMouseListener(inputHandler);
		addKeyListener(inputHandler);
		addMouseMotionListener(inputHandler);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(new Dimension(GameSettings.windowSize.getWidth(),GameSettings.windowSize.getHeight()));
		setResizable(true);
		
		
		cycleRender = new GameCycleRender();
		cycleRunner = new GameCycleRunner();
		mousePoll = new MousePolling();
		DefineWorld();
		InitializeGlobals();
		
		InitializeTimers();
		InitializeRender();
		
		
	}

	private void DefineWorld() {
		game = new Game();
		game.addObject("./src/res/object5.obj", 5);
		//game.addObject("./src/res/object3.obj", 100);
	}

	private void InitializeGlobals() {
		sharedAttributes = new SharedAttributes();
		sharedAttributes.game = game;
		sharedAttributes.player = new Player();
		
		cycleRender.sa = sharedAttributes;
		cycleRunner.sa = sharedAttributes;
		inputHandler.sa = sharedAttributes;
		mousePoll.sa = sharedAttributes;
	}

	private void InitializeRender() {
		DefineCursor();
		drawPanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, GameSettings.windowSize.getWidth(),GameSettings.windowSize.getHeight());
				
				cycleRender.drawComponent(g);
			}
		};
		add(drawPanel);
	}

	private void DefineCursor() {
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
		    cursorImg, new java.awt.Point(0, 0), "blank cursor");

		// Set the blank cursor to the JFrame.
		getContentPane().setCursor(blankCursor);
	}

	public void InitializeTimers(){
		gameTimer = new Timer();
		drawTimer = new Timer();
		gameTimer.scheduleAtFixedRate(cycleRunner, 0l, GameSettings.backend_refresh_rate);
		gameTimer.scheduleAtFixedRate(mousePoll, 0l, GameSettings.mousepoll_rate);
		drawTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if(drawPanel!=null)
				drawPanel.repaint();
			}

		}, 0l, (long) (1000/GameSettings.refresh_rate));
	}
}

