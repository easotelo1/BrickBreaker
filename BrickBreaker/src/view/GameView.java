package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import controller.BallController;
import controller.BrickBreaker;
import controller.BrickController;
import controller.PaddleController;
import model.Ball;
import model.Brick;
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
	private BrickGrid brickGrid;
	
	private final PaddleController paddleController;
	private final BallController ballController;
	private final BrickController brickController;
	
	private static final int PADDLE_SMALL_WIDTH = 130;
	private static final int PADDLE_SMALL_HEIGHT = 25;
	
	private static final int PADDLE_MID_WIDTH = 200;
	private static final int PADDLE_MID_HEIGHT = 38;

	private static final int PADDLE_LARGE_WIDTH = 300;
	private static final int PADDLE_LARGE_HEIGHT = 50;
	
	private static final int BALL_SMALL_WIDTH = 25;
	private static final int BALL_SMALL_HEIGHT = 25;
	
	private static final double BALL_WIDTH_RATIO = 0.019;  // 1.9% of screen width
	private static final int BALL_PADDLE_HEIGHT_GAP = 10;  //10 pixels
		    
	private BufferedImage brickImage = null;
	
	public GameView() {
		mainPanelSize = screen.getScreenResolution();
		currResolution =  mainPanelSize[0] + "x" + mainPanelSize[1];
		setFocusable(true);
		
		gameOverlays = new InGameOverlays();
		
		int paddleWidth = PADDLE_SMALL_WIDTH;
		int paddleHeight = PADDLE_SMALL_HEIGHT;
		int paddleX = (mainPanelSize[0] - paddleWidth) / 2;
		int paddleY = mainPanelSize[1] - paddleHeight - 100;
		this.paddle = new Paddle(paddleX, paddleY, paddleWidth, paddleHeight);
		this.paddleController = new PaddleController(paddle);
		
		int ballWidth = BALL_SMALL_WIDTH;
		int ballHeight = BALL_SMALL_HEIGHT;
		int ballX = (mainPanelSize[0] - ballWidth) / 2;
		int ballY = paddleY - ballHeight - BALL_PADDLE_HEIGHT_GAP;		//about 10 pixel gap between paddle and ball
		this.ball = new Ball(ballX, ballY, ballWidth, ballHeight);
		this.ballController = new BallController(ball);
		
		brickGrid = new BrickGrid();
		generateBrickImage();
		
		this.brickController = new BrickController(gameOverlays, brickGrid.getBricks());
		
		addInGameKeyboardInput();
		
	}
	
	//only draw image when needed so that cpu doesn't get overloaded 
	private void generateBrickImage() {
	    brickImage = new BufferedImage(mainPanelSize[0], mainPanelSize[1], BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2d = brickImage.createGraphics();
	    brickGrid.draw(g2d);
	    g2d.dispose();
	}
	
	private void addInGameKeyboardInput() {
		addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyPressed(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
		        	BrickBreaker brickBreaker = BrickBreaker.getInstance();
		        	brickBreaker.pauseGame();
		        	paddleController.stop();
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
						gameOverlays.hideLaunchOverlay();
						ballController.launch(mainPanelSize[0]); 
					}
				}
			}
		});
		
		addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyPressed(KeyEvent e) {
		    	BrickBreaker brickBreaker = BrickBreaker.getInstance();
		    	
		        if (gameOverlays.getCurrentLives() <= 0) {  // Only when Game Over is visible
		            if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
		                gameOverlays.toggleYesNoSelection();  // Switch to Yes
		            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
		                gameOverlays.toggleYesNoSelection();  // Switch to No
		            } else if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE) {
		                if (gameOverlays.isYesSelected()) {
		                	System.out.println("Play Again Selected!");
		                	resetGame();
		                    brickBreaker.startGame();  // Reset & restart
		                } else {
		                	System.out.println("Back to main menu selected!");
		                	gameOverlays.toggleYesNoSelection(); //resets default selection back to yes
		                    brickBreaker.exitToMenu();
		                }
		                gameOverlays.hideGameOver();
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
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.fillRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());
		g.setColor(Color.WHITE);
		g.fillOval(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());
		
		gameOverlays.draw(g2d);
		
		if (brickImage != null) {
		    g2d.drawImage(brickImage, 0, 0, null);
		}
		
		
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
		this.paddle = new Paddle(paddleX, paddleY, paddleWidth, paddleHeight);
		this.paddleController.setPaddle(paddle);
		System.out.println("paddleWidth = " + paddleWidth);
	}
	
	private void updateInitialBallPosition() {
		int newBallSize = (int) (mainPanelSize[0] * BALL_WIDTH_RATIO);
		
		// Center on paddle horizontally
	    int ballX = paddle.getX() + (paddle.getWidth() - newBallSize) / 2;
	    int ballY = paddle.getY() - newBallSize - BALL_PADDLE_HEIGHT_GAP;  // Above paddle
	    this.ball = new Ball(ballX, ballY, newBallSize, newBallSize);
		this.ballController.setBall(ball);
		
	}
	
	//AABB Collision Detection
	private boolean intersects() {
		
		//X-axis overlaps
		boolean ballLeftEdgeLeftOfPaddleRightEdge = ball.getX() < paddle.getX() + paddle.getWidth();
		boolean ballRightEdgeRightOfPaddleLeftEdge = ball.getX() + ball.getWidth() > paddle.getX();
		
		//Y-axis overlaps
		boolean ballTopAboveBottomOfPaddle = ball.getY() < paddle.getY() + paddle.getHeight();
		boolean ballBottomBelowTopOfPaddle = ball.getY() + ball.getHeight() > paddle.getY();
		
		boolean intersects = ballLeftEdgeLeftOfPaddleRightEdge && 
							 ballRightEdgeRightOfPaddleLeftEdge &&
							 ballTopAboveBottomOfPaddle &&
							 ballBottomBelowTopOfPaddle;
		
		return intersects;
		
	}
	
	//AABB Collision Detection between ball and brick. Same as above just less variables lol
	private boolean intersectsBallBrick(Brick brick) {
	    return ball.getX() < brick.getX() + brick.getWidth() &&
	           ball.getX() + ball.getWidth() > brick.getX() &&
	           ball.getY() < brick.getY() + brick.getHeight() &&
	           ball.getY() + ball.getHeight() > brick.getY();
	}
		
	// This method is called by the BrickBreaker controller's Timer loop.
	public void updateGameLogic(double timeDeltaSeconds) {
		BrickBreaker brickBreaker = BrickBreaker.getInstance();
		
		this.paddleController.update(timeDeltaSeconds, mainPanelSize[0]);
		
		if(brickBreaker.getCurrentState() == GameState.INGAME_NOT_PLAYING) {
			this.ballController.moveWithPaddle(paddle.getX() + (paddle.getWidth()/2 - ball.getWidth()/2));
		}
		else {
			//ball movement
			boolean alive = this.ballController.update(timeDeltaSeconds, mainPanelSize[0], mainPanelSize[1]);
			
			//paddle collision 
			if(intersects()) {
				ballController.bounceOffPaddle(paddle.getX(), paddle.getY(), paddle.getWidth(), mainPanelSize[0]);
			}
			
			//brick collision
			for (int i = brickGrid.getBricks().size() - 1; i >= 0; i--) {
			    Brick brick = brickGrid.getBricks().get(i);
			    if (intersectsBallBrick(brick) && brick.isAlive()) {
			    	brickController.update(brick);
			    	generateBrickImage();
			    	ballController.bounceOffBrick(brick.getX(), brick.getWidth());
			        break;
			    }
			}
			
			if(!alive) {
				playerDeath();
			}
		}
		
    }
	
    public void updateSizeAndLayout() {
        mainPanelSize = screen.getScreenResolution();
        currResolution =  mainPanelSize[0] + "x" + mainPanelSize[1];
        
        setPreferredSize(new Dimension(mainPanelSize[0], mainPanelSize[1]));
        
        gameOverlays.showLaunchOverlay();
        gameOverlays.updateSizeAndLayout();
        updateInitialPaddlePosition();
        updateInitialBallPosition();
        gameOverlays.resetHUD();
        
        brickGrid.updateSizeAndLayout();
        generateBrickImage();
        
        revalidate();
        repaint();
    }
    
    public void resetGame() {        
    	mainPanelSize = screen.getScreenResolution();
        currResolution =  mainPanelSize[0] + "x" + mainPanelSize[1];
    	paddleController.stop();
    	ballController.reset();
        updateInitialPaddlePosition();
        updateInitialBallPosition();
    	gameOverlays.showLaunchOverlay();
    	gameOverlays.updateSizeAndLayout();
    	if(BrickBreaker.getInstance().getCurrentState() == GameState.GAME_OVER) {
    		gameOverlays.resetHUD();
    		brickController.reset();
    		generateBrickImage();
    	}
    }
    
    public void playerDeath() {
    	BrickBreaker brickBreaker = BrickBreaker.getInstance();
    	
    	System.out.println("You died");
		gameOverlays.decreaseLives();
		
        if(gameOverlays.getCurrentLives() <= 0) {
        	System.out.println("Game Over");
        	brickBreaker.setCurrentState(GameState.GAME_OVER);
        	gameOverlays.showGameOver();
        	paddleController.stop();
        } 
        else { 
        	brickBreaker.playerDied();
        	resetGame();
        }
        
		revalidate();
		repaint();
    }
       
}
