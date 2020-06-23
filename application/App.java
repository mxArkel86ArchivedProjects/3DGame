package application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class App extends JFrame {

	public static App app;
	public static Timer drawTimer;
	public static Timer gameTimer;
	public static GameCycleRunner cycleRunner;
	public static GameCycleRender cycleRender;
	private static JPanel drawPanel;
	private static InputHandler inputHandler;
	private static SharedAttributes sharedAttributes;

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
		
		cycleRunner = new GameCycleRunner();
		cycleRender = new GameCycleRender();
		
		InitializeGlobals();
		InitializeRender();
		InitializeTimers();
	}

	private void InitializeGlobals() {
		sharedAttributes = new SharedAttributes();
		
		cycleRender.sa = sharedAttributes;
		cycleRunner.sa = sharedAttributes;
		inputHandler.sa = sharedAttributes;
	}

	private void InitializeRender() {
		
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

	public void InitializeTimers(){
		gameTimer = new Timer();
		drawTimer = new Timer();
		drawTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				drawPanel.repaint();
			}

		}, 0l, (long) (1000/GameSettings.refresh_rate));
		gameTimer.scheduleAtFixedRate(cycleRunner, 0l, 10l);
	}
}

