package application;

import java.awt.event.FocusEvent;
import utilities.KeyInput;
import java.awt.event.KeyEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class InputHandler implements MouseListener, KeyListener, FocusListener {
	public SharedAttributes sa;
	KeyInput i;
	
	public InputHandler() {

	}

	public void init() {
		i = sa.keyInput;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(sa.consoleup)
			consoleInput(e);
		else
			keyInput(e, true);
	}

	private void consoleInput(KeyEvent e) {	
		switch(e.getKeyCode()) {
		case KeyEvent.VK_ENTER:
			ArrayList<String> parts = new ArrayList<String>(Arrays.asList(sa.consoleLine.split(" ")));
			String subject = parts.get(0);
			if(parts.size()<=1)
				System.out.println("command without arguments");
			else {
				parts.remove(0);
				boolean b = App.commandHandler.executeCommand(subject, parts);
				if(!b)
					System.out.print("command failed");
				
				
			}
			sa.consoleLine = "";
			sa.consoleup = false;
			return;
		case KeyEvent.VK_DOWN:
			sa.consoleup = false;
			sa.consoleLine = "";
			return;
		case KeyEvent.VK_BACK_SPACE:
			if(sa.consoleLine.length()>0)
				sa.consoleLine = sa.consoleLine.substring(0, sa.consoleLine.length()-1);
			return;
		}
		sa.consoleLine+=e.getKeyChar();
	}

	private void keyInput(KeyEvent e, boolean b) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_D:
			i.d = b;
			break;
		case KeyEvent.VK_A:
			i.a = b;
			break;
		case KeyEvent.VK_W:
			i.w = b;
			break;
		case KeyEvent.VK_S:
			i.s = b;
			break;
		case KeyEvent.VK_SPACE:
			i.space = b;
			break;
		case KeyEvent.VK_C:
			i.c = b;
			break;
		case KeyEvent.VK_UP:
			i.tilde = b;
			if(b)
				sa.consoleup = true;
			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(!sa.consoleup)
			keyInput(e, false);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void focusGained(FocusEvent arg0) {
		i.windowFocused = true;
	}

	@Override
	public void focusLost(FocusEvent e) {
		i.windowFocused = false;
	}
}
