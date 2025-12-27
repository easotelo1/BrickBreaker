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
	private GameState previousState;

	public BrickBreaker() {
		GameFrame.getGameFrame();
		currentState = GameState.MENU;
		previousState = GameState.MENU;
		
		this.gameView = GameFrame.getGamePanel();
		
		// Initialize Timer. start the Timer when game starts on the EDT (Application ensures we are on EDT)
        this.gameLoopTimer = new Timer(DELAY, this);
	}
	
	public void startGame() {
		currentState = GameState.INGAME_NOT_PLAYING;
		GameFrame.setView("game");
		gameView.grabFocus();
		gameLoopTimer.start();
		System.out.println("GameState updated to " + currentState);
	}
	
	public void startPlaying() {
		currentState = GameState.INGAME_PLAYING;
		System.out.println("GameState updated to " + currentState);
	}
	
	public void pauseGame() {
		if (currentState == GameState.INGAME_PLAYING || currentState == GameState.INGAME_NOT_PLAYING) {
			previousState = currentState;
            currentState = GameState.PAUSED;
            gameLoopTimer.stop();
            GameFrame.setView("pause");
    		System.out.println("GameState updated to " + currentState);
        }
	}
	
	public void resumeGame() {
        if (currentState == GameState.PAUSED) {
            currentState = previousState;
            GameFrame.setView("game");
            gameView.grabFocus();
            gameLoopTimer.start();
    		System.out.println("GameState updated to " + currentState);
        }
    }
	
	public void exitToMenu() {
        currentState = GameState.MENU;
		gameView.updateSizeAndLayout();
		gameView.clearInputs();
        gameLoopTimer.stop();
        GameFrame.setView("menu");
		System.out.println("GameState updated to " + currentState);
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(currentState == GameState.INGAME_PLAYING || currentState == GameState.INGAME_NOT_PLAYING) {
			gameView.updateGameLogic(UPDATE_INTERVAL_SEC);
			gameView.repaint();
		}
	}
	
	public GameState getCurrentState() {
		return this.currentState;
	}
	
}
