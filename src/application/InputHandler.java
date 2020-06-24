package application;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import utilities.Point;

public class InputHandler implements MouseListener, KeyListener, MouseMotionListener, ComponentListener {
	public SharedAttributes sa;
	
	public InputHandler() {
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		keyInput(e, true);
	}

	private void keyInput(KeyEvent e, boolean b) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_D:
			sa.keyInput.d = b;
			break;
		case KeyEvent.VK_A:
			sa.keyInput.a = b;
			break;
		case KeyEvent.VK_W:
			sa.keyInput.w = b;
			break;
		case KeyEvent.VK_S:
			sa.keyInput.s = b;
			break;
		case KeyEvent.VK_SPACE:
			sa.keyInput.space = b;
			break;
		case KeyEvent.VK_C:
			sa.keyInput.c = b;
			break;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
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
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent e) {
		
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
