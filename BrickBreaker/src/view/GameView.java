package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import controller.BrickBreaker;
import controller.PaddleController;
import model.Paddle;
import model.Screen;

@SuppressWarnings("serial")
public class GameView extends JPanel {
	
	private Screen screen = Screen.getScreen();
	private int[] mainPanelSize;
	private String currResolution;
	private Paddle paddle;
	private final PaddleController paddleController;
	
	private static final int PADDLE_SMALL_WIDTH = 130;
	private static final int PADDLE_SMALL_HEIGHT = 25;
	
	private static final int PADDLE_MID_WIDTH = 200;
	private static final int PADDLE_MID_HEIGHT = 38;

	private static final int PADDLE_LARGE_WIDTH = 300;
	private static final int PADDLE_LARGE_HEIGHT = 50;
	
	private static final int BALL_SMALL_WIDTH = 10;
	private static final int BALL_SMALL_HEIGHT = 10;
    
	public GameView() {
		mainPanelSize = screen.getScreenResolution();
		currResolution =  mainPanelSize[0] + "x" + mainPanelSize[1];
		setFocusable(true);
		
		int paddleWidth = PADDLE_SMALL_WIDTH;
		int paddleHeight = PADDLE_SMALL_HEIGHT;
		int paddleX = (mainPanelSize[0] - paddleWidth) / 2;
		int paddleY = mainPanelSize[1] - paddleHeight - 100;
		this.paddle = new Paddle(paddleX, paddleY, paddleWidth, paddleHeight);
		this.paddleController = new PaddleController(paddle);
		
		addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyPressed(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
		        	BrickBreaker brickBreaker = BrickBreaker.getInstance();
		        	brickBreaker.pauseGame();
		        }
		    }
		});
		
		addKeyListener(paddleController);

		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(mainPanelSize[0], mainPanelSize[1]));
	
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				updateInitialPaddlePosition();
			}
		});

	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.fillRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());
	}
	
	private void updateInitialPaddlePosition() {
		int paddleWidth;
		int paddleHeight;
		System.out.println("currResolution = " + currResolution);
		switch(currResolution) {
			case "1280x720":
				paddleWidth = PADDLE_SMALL_WIDTH;
				paddleHeight = PADDLE_SMALL_HEIGHT;
				break;
			case "1920x1080":
				paddleWidth = PADDLE_MID_WIDTH;
				paddleHeight = PADDLE_MID_HEIGHT;
				break;
			case "2560x1440":
				paddleWidth = PADDLE_LARGE_WIDTH;
				paddleHeight = PADDLE_LARGE_HEIGHT;
				break;
			default:
				paddleWidth = PADDLE_SMALL_WIDTH;
				paddleHeight = PADDLE_SMALL_HEIGHT;
		}

		int paddleX = (mainPanelSize[0] - paddleWidth) / 2;
		int paddleY = mainPanelSize[1] - paddleHeight - 100;
		paddle = new Paddle(paddleX, paddleY, paddleWidth, paddleHeight);
//		this.paddleController = new PaddleController(paddle);
		this.paddleController.setPaddle(paddle);
		System.out.println("paddleWidth = " + paddleWidth);
	}
	
	// This method is called by the BrickBreaker controller's Timer loop.
	public void updateGameLogic(double timeDeltaSeconds) {
		this.paddleController.update(timeDeltaSeconds, mainPanelSize[0]);
    }
	
    public void updateSizeAndLayout() {
        mainPanelSize = screen.getScreenResolution();
        currResolution =  mainPanelSize[0] + "x" + mainPanelSize[1];
        
        setPreferredSize(new Dimension(mainPanelSize[0], mainPanelSize[1]));
        
        updateInitialPaddlePosition();
        
        revalidate();
        repaint();

    }
}
