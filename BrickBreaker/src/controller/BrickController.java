package controller;

import java.util.ArrayList;

import model.Brick;
import view.InGameOverlays;

public class BrickController {
	
	private final InGameOverlays gameOverlay;
	private final ArrayList<Brick> bricks;
	
	public BrickController(InGameOverlays gameOverlay, ArrayList<Brick> bricks) {
		this.gameOverlay = gameOverlay;
		this.bricks = bricks;
	}
	
	public void update(Brick brick) {
		if(brick.isAlive()) {
			brick.destroy();
			int score = 100 - (bricks.indexOf(brick) / 10 * 20); 
			gameOverlay.updateScore(gameOverlay.getCurrentScore() + score);
		}
    }
	
	public void reset() {
		for(int i = bricks.size() - 1; i >= 0; i--) {
			Brick brick = bricks.get(i);
			brick.setAlive(true);
		}
	}

}
