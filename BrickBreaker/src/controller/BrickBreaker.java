package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import model.GameState;
import view.GameFrame;
import view.GameView;

public final class BrickBreaker implements ActionListener {
	
	private static final BrickBreaker INSTANCE = new BrickBreaker();
	
	public static BrickBreaker getInstance() {
		return INSTANCE;
	}
	
    private static final int FRAME_RATE = 60; // Target frames per second
    private static final int DELAY = 1000 / FRAME_RATE; // Delay in milliseconds for the Timer
    private static final double UPDATE_INTERVAL_SEC = 1.0 / FRAME_RATE; 
	
    private Timer gameLoopTimer;
	private GameView gameView;
	private GameState currentState;
	
	public BrickBreaker() {
		GameFrame.getGameFrame();
		currentState = GameState.MENU;
		
		this.gameView = GameFrame.getGamePanel();
		
		// Initialize Timer. start the Timer when game starts on the EDT (Application ensures we are on EDT)
        this.gameLoopTimer = new Timer(DELAY, this);
	}
	
	public void startGame() {
		currentState = GameState.PLAYING;
		GameFrame.setView("game");
		gameView.grabFocus();
		gameLoopTimer.start();
		System.out.println("GameState updated to " + currentState);
	}
	
	public void pauseGame() {
		if (currentState == GameState.PLAYING) {
            currentState = GameState.PAUSED;
            gameLoopTimer.stop();
            GameFrame.setView("pause");
    		System.out.println("GameState updated to " + currentState);

        }
	}
	
	public void resumeGame() {
        if (currentState == GameState.PAUSED) {
            currentState = GameState.PLAYING;
            GameFrame.setView("game");
            gameView.grabFocus();
            gameLoopTimer.start();
    		System.out.println("GameState updated to " + currentState);
        }
    }
	
	public void exitToMenu() {
        currentState = GameState.MENU;
        gameLoopTimer.stop();
        GameFrame.setView("menu");
		System.out.println("GameState updated to " + currentState);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if(currentState == GameState.PLAYING) {
			gameView.updateGameLogic(UPDATE_INTERVAL_SEC);
			gameView.repaint();
		}
	}
	
	public GameState getCurrentState() {
		return this.currentState;
	}
	
}
