package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import view.GameFrame;
import view.GameView;

public final class BrickBreaker implements ActionListener {
	
    private static final int FRAME_RATE = 60; // Target frames per second
    private static final int DELAY = 1000 / FRAME_RATE; // Delay in milliseconds for the Timer
    private static final double UPDATE_INTERVAL_SEC = 1.0 / FRAME_RATE; 
	
    private Timer gameLoopTimer;
	private GameView gameView;
	
	public BrickBreaker() {
		GameFrame.getGameFrame();
		this.gameView = GameFrame.getGamePanel();
        
		// Initialize and start the Timer on the EDT (Application ensures we are on EDT)
        this.gameLoopTimer = new Timer(DELAY, this);
        this.gameLoopTimer.start();
        
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gameView.updateGameLogic(UPDATE_INTERVAL_SEC);
		gameView.repaint();
	}
	
}
