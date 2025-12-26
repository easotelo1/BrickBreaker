package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

import controller.BallController;
import controller.BrickBreaker;
import controller.PaddleController;
import model.Ball;
import model.GameState;
import model.Paddle;
import model.Screen;

@SuppressWarnings("serial")
public class GameView extends JPanel {
	
	private Screen screen = Screen.getScreen();
	private int[] mainPanelSize;
	private String currResolution;
	
	private InGameOverlays gameOverlays;
	
	private Paddle paddle;
	private Ball ball;
	
	private final PaddleController paddleController;
	private final BallController ballController;
	
	private static final int PADDLE_SMALL_WIDTH = 130;
	private static final int PADDLE_SMALL_HEIGHT = 25;
	
	private static final int PADDLE_MID_WIDTH = 200;
	private static final int PADDLE_MID_HEIGHT = 38;

	private static final int PADDLE_LARGE_WIDTH = 300;
	private static final int PADDLE_LARGE_HEIGHT = 50;
	
	private static final int BALL_SMALL_WIDTH = 25;
	private static final int BALL_SMALL_HEIGHT = 25;
	
	private JPanel pushToStartPanel;
    
	public GameView() {
		mainPanelSize = screen.getScreenResolution();
		currResolution =  mainPanelSize[0] + "x" + mainPanelSize[1];
		setFocusable(true);
		
		gameOverlays = new InGameOverlays();
		pushToStartPanel = gameOverlays.getPushToStartPanel();
		
		int paddleWidth = PADDLE_SMALL_WIDTH;
		int paddleHeight = PADDLE_SMALL_HEIGHT;
		int paddleX = (mainPanelSize[0] - paddleWidth) / 2;
		int paddleY = mainPanelSize[1] - paddleHeight - 100;
		this.paddle = new Paddle(paddleX, paddleY, paddleWidth, paddleHeight);
		this.paddleController = new PaddleController(paddle);
		
		int ballWidth = BALL_SMALL_WIDTH;
		int ballHeight = BALL_SMALL_HEIGHT;
		int ballX = (mainPanelSize[0] - ballWidth) / 2;
//		int ballY = mainPanelSize[1] - ballHeight - 130;
		int ballY = paddleY - BALL_SMALL_HEIGHT - 10;
		this.ball = new Ball(ballX, ballY, ballWidth, ballHeight);
		this.ballController = new BallController(ball);
		
		addInGameKeyboardInput();
		addInGameOverlays();

	}
	
	private void addInGameOverlays() {
//		add(gameOverlays.showPushToStartPanel());
		setLayout(new OverlayLayout(this));
		JPanel gameOverlaysPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gameOverlaysPanel.add(pushToStartPanel, gbc);
		gameOverlaysPanel.setOpaque(false);
		gameOverlaysPanel.setBorder(BorderFactory.createLineBorder(Color.green, 4));
		
		add(gameOverlaysPanel);
	}
	
	private void addInGameKeyboardInput() {
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
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_SPACE) {
					BrickBreaker brickBreaker = BrickBreaker.getInstance();
					if(brickBreaker.getCurrentState() == GameState.INGAME_NOT_PLAYING) {
						brickBreaker.startPlaying();
						pushToStartPanel.setVisible(false);
					}
				}
			}
		});

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
		g.setColor(Color.WHITE);
		g.fillOval(ball.getX(), ball.getY(), BALL_SMALL_WIDTH, BALL_SMALL_HEIGHT);
		
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
		this.paddleController.setPaddle(paddle);
		System.out.println("paddleWidth = " + paddleWidth);
	}
	
	// This method is called by the BrickBreaker controller's Timer loop.
	public void updateGameLogic(double timeDeltaSeconds) {
		this.paddleController.update(timeDeltaSeconds, mainPanelSize[0]);
		BrickBreaker brickBreaker = BrickBreaker.getInstance();
		
		if(brickBreaker.getCurrentState() == GameState.INGAME_NOT_PLAYING) {
			this.ballController.moveWithPaddle(paddle.getX() + (paddle.getWidth()/2 - ball.getWidth()/2));
		}
		else {
			this.ballController.update(timeDeltaSeconds, mainPanelSize[0], mainPanelSize[1]);
		}
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
