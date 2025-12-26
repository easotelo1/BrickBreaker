package controller;

import model.Ball;

public class BallController {
	
	private Ball ball;
	
	private double velocityX = 0;
	private double velocityY = 0;
	private static final double BALL_SPEED = 500; //TODO: update later
	private static final double BALL_LEFT_AND_UPPER_BOUNDS  = 0.1;
	private static final double BALL_RIGHT_AND_LOWER_BOUNDS = 1.1;
	
	public BallController(Ball ball) {
		this.ball = ball;
	}
	
	public void update(double timeDeltaSeconds, int screenWidth, int screenHeight) {
		//TODO: placeholder
	}
	
//	public void update(double timeDeltaSeconds, int screenWidth) {
//        // Calculate the proposed movement amount
//        int moveAmount = (int) (this.velocityX * timeDeltaSeconds);
//        int newXPos = ball.getX() + moveAmount;
//        
//        // Calculate the actual pixel bounds based on the screen width/paddle width ratios
//        int minX = (int) (ball.getWidth() * BALL_LEFT_AND_UPPER_BOUNDS);
//        int maxX = screenWidth - ball.getWidth() - (int) (ball.getWidth() * (BALL_RIGHT_AND_LOWER_BOUNDS - 1.0));
//
//        if (newXPos < minX) {
//            newXPos = minX;
//        } else if (newXPos > maxX) {
//            newXPos = maxX;
//        }
//
//        ball.setX(newXPos);
//	}
	
	public void moveWithPaddle(int newXPos) {
		ball.setX(newXPos);
	}

}
