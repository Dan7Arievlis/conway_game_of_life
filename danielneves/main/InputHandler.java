package com.danielneves.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class InputHandler implements KeyListener , MouseListener {
	public class Key {
		public int presses, absorbs;
		public boolean down, clicked;

		public Key() {
			keys.add(this);
		}

		public void toggle(boolean pressed) {
			if (pressed != down) {
				down = pressed;
			}
			if (pressed) {
				presses++;
			}
		}

		public void tick() {
			if (absorbs < presses) {
				absorbs++;
				clicked = true;
			} else {
				clicked = false;
			}
		}
	}

	public class Mouse {
		public int presses, absorbs;
		public boolean down, clicked;

		public void toggle(boolean pressed) {
			if (pressed != down) {
				down = pressed;
			}
			if (pressed) {
				presses++;
			}
		}

		public void tick() {
			if (absorbs < presses) {
				absorbs++;
				clicked = true;
			} else {
				clicked = false;
			}
		}
	}
	
	public List<Key> keys = new ArrayList<Key>();

	public Key start = new Key();
	
	public List<Mouse> buttons = new ArrayList<Mouse>();
	public int mx;
	public int my;

	public Mouse button1 = new Mouse();
	
	public void releaseAll() {
		for (int i = 0; i < keys.size(); i++) {
			keys.get(i).down = false;
		}
		
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).down = false;
		}
	}

	public void tick() {
		for (int i = 0; i < keys.size(); i++) {
			keys.get(i).tick();
		}
		
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).tick();
		}
	}

	public InputHandler(Game game) {
		game.addKeyListener(this);
		game.addMouseListener(this);
	}

	public void keyPressed(KeyEvent ke) {
		toggle(ke, true);
	}

	public void keyReleased(KeyEvent ke) {
		toggle(ke, false);
	}

	private void toggle(KeyEvent ke, boolean pressed) {
		if (ke.getKeyCode() == KeyEvent.VK_ENTER) start.toggle(pressed);
	}

	public void keyTyped(KeyEvent ke) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		toggle(e, true);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		toggle(e, false);
	}
	
	private void toggle(MouseEvent e, boolean pressed) {
		if (e.getButton() == MouseEvent.BUTTON1) button1.toggle(pressed);
		
		mx = e.getX();
		my = e.getY();
	}
}
