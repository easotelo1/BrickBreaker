package controller;
import model.GameState;
import view.GameFrame;
import view.GameView;

public final class BrickBreaker {
	
	private static final BrickBreaker INSTANCE = new BrickBreaker();
	
	public static BrickBreaker getInstance() {
		return INSTANCE;
	}
    
	private Thread gameThread;
    private static final double timeDeltaSeconds = 1.0 / 60.0; // 60 frames per second
    private double accumulatedTime = 0.0;

    private volatile boolean running = false;
    private volatile boolean paused = false;
    
	private GameView gameView;
	private GameState currentState;
	private GameState previousState;
	
	public BrickBreaker() {
		GameFrame.getGameFrame();
		currentState = GameState.MENU;
		previousState = GameState.MENU;
		
		this.gameView = GameFrame.getGamePanel();
        
	}
	
	private void gameLoop() {
        long lastTime = System.nanoTime();

        while (running) {
            long now = System.nanoTime();
            double deltaSeconds = (now - lastTime) / 1_000_000_000.0;
            lastTime = now;

            accumulatedTime += deltaSeconds;

            if (!paused) {
                while (accumulatedTime >= timeDeltaSeconds) {
                    gameView.updateGameLogic(timeDeltaSeconds);
                    accumulatedTime -= timeDeltaSeconds;
                }
            }

            gameView.repaint();

            // Small sleep to prevent 100% CPU usage
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
	
	public void startGame() {
		currentState = GameState.INGAME_NOT_PLAYING;
		GameFrame.setView("game");
		gameView.grabFocus();
		running = true;
        paused = false;
        gameThread = new Thread(this::gameLoop);
        gameThread.start();
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
            paused = true;
            GameFrame.setView("pause");
    		System.out.println("GameState updated to " + currentState);
        }
	}
	
	public void resumeGame() {
        if (currentState == GameState.PAUSED) {
            currentState = previousState;
            GameFrame.setView("game");
            gameView.grabFocus();
            paused = false;
    		System.out.println("GameState updated to " + currentState);
        }
    }
	
	public void exitToMenu() {
		running = false;
		if (gameThread != null && gameThread.isAlive()) {
            try {
                gameThread.join(1000); // Wait briefly for clean shutdown
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        currentState = GameState.MENU;
		gameView.updateSizeAndLayout();
		gameView.resetGame();
        GameFrame.setView("menu");
		System.out.println("GameState updated to " + currentState);
    }
	
	public GameState getCurrentState() {
		return this.currentState;
	}
	
}
