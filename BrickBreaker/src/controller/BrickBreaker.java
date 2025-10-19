package controller;
import view.GameFrame;

public final class BrickBreaker implements Runnable {
	
//	private GameFrame gameFrame;
	
	public BrickBreaker() {

		Thread thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		while(true) {
			GameFrame.getGameFrame();

		}
	}
	
//	private void setUp() {
//		GameFrame gm = new GameFrame();
//		this.gameFrame = gm.getFrame();
//		System.out.println("Hello World");
//	}
}
