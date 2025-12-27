package controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import model.Paddle;

public class PaddleController extends KeyAdapter {
	
	private Paddle paddle;
	private final Set<Integer> pressedKeys = new HashSet<>();
	
	private double velocityX = 0;
    private static final double PADDLE_SPEED = 500; //in pixels
	private static final double PADDLE_LEFT_BOUNDS = 0.1;
	private static final double PADDLE_RIGHT_BOUNDS = 1.1;
	
	public PaddleController(Paddle paddle) {
		this.paddle = paddle;
	}

    public void update(double timeDeltaSeconds, int screenWidth) {
        // Calculate the proposed movement amount
        int moveAmount = (int) (this.velocityX * timeDeltaSeconds);
        int newXPos = paddle.getX() + moveAmount;
        
        // Calculate the actual pixel bounds based on the screen width/paddle width ratios
        int minX = (int) (paddle.getWidth() * PADDLE_LEFT_BOUNDS);
        int maxX = screenWidth - paddle.getWidth() - (int) (paddle.getWidth() * (PADDLE_RIGHT_BOUNDS - 1.0));

        if (newXPos < minX) {
            newXPos = minX;
        } else if (newXPos > maxX) {
            newXPos = maxX;
        }
        
        paddle.setX(newXPos);
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (pressedKeys.add(e.getKeyCode())) {
            evaluateInput();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (pressedKeys.remove(e.getKeyCode())) {
            evaluateInput();
        }
    }
    
    private void evaluateInput() {
    	if (pressedKeys.contains(KeyEvent.VK_LEFT) || pressedKeys.contains(KeyEvent.VK_A)) {
            moveLeft();
        } else if (pressedKeys.contains(KeyEvent.VK_RIGHT) || pressedKeys.contains(KeyEvent.VK_D)) {
            moveRight();
        } else {
            stop();
        }
    }

    public void moveLeft() {
        this.velocityX = -PADDLE_SPEED;
    }
    
    public void moveRight() {
        this.velocityX = PADDLE_SPEED;
    }
    
    public void stop() {
    	pressedKeys.clear();
        this.velocityX = 0;
    }
    
    public void setPaddle(Paddle paddle) {
    	this.paddle = paddle;
    }
	
}
