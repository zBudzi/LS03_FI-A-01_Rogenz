package view.game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controller.GameController;

public class MouseClickListener extends MouseAdapter {

	private GameController gc;

	public MouseClickListener(GameController gc) {
		this.gc = gc;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		gc.fireShot(e.getX(), e.getY());
	}

}
