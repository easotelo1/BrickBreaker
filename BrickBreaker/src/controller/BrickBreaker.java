package controller;
import view.GameFrame;

public final class BrickBreaker implements Runnable {
	
//	private GameFrame gameFrame;
	
	public BrickBreaker() {
		GameFrame.getGameFrame();
		Thread thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		while(true) {
			
//			System.out.println("running");
		}
	}
	
//	private void setUp() {
//		GameFrame gm = new GameFrame();
//		this.gameFrame = gm.getFrame();
//		System.out.println("Hello World");
//	}
}
