package controller;

import java.util.Random;

import model.Ball;

public class BallController {
	
	private Ball ball;
	private final Random rand = new Random();
	
	private double velocityX = 0;
	private double velocityY = 0;
	private static final double BALL_SPEED = 500; //TODO: update later
	private static final double BALL_LEFT_AND_UPPER_BOUNDS  = 0.1;
	private static final double BALL_RIGHT_AND_LOWER_BOUNDS = 1.1;
	
	private static final double LAUNCH_CONE_ANGLE_DEGREES = 65.0;  // Total cone width: ±20° from straight up
	
	private boolean isLaunched = false;
	
	public BallController(Ball ball) {
		this.ball = ball;
	}
	
	public void update(double timeDeltaSeconds, int screenWidth, int screenHeight) {
		int moveXAmount = (int) (this.velocityX * timeDeltaSeconds);
		int moveYAmount = (int) (this.velocityY * timeDeltaSeconds);
		
		int newXPos = ball.getX() + moveXAmount;
		int newYPos = ball.getY() + moveYAmount;
		
		ball.setX(newXPos);
        ball.setY(newYPos);
		
		// Wall collisions (left/right/top)
        if (ball.getX() <= 0 || ball.getX() + ball.getWidth() >= screenWidth) {
        	this.velocityX = -(this.velocityX);
        }
        if (ball.getY() <= 0) {
        	this.velocityY = -(this.velocityY);
        }
		
	}
	
	
	public void launch() {
		if(isLaunched) {
			return;
		}
		
		this.isLaunched = true;
		
		//straight up is 180 degrees.
		//I want a random number between 115 to 245 degrees. This makes 180 +/- 65 (LAUNCH_CONE_ANGLE_DEGREES)
		double randomOffset = rand.nextDouble(LAUNCH_CONE_ANGLE_DEGREES) * (rand.nextBoolean() ? -1 : 1);
        double launchAngleDegrees = 180 + randomOffset;  // 115° to 245°
        double angleRadians = Math.toRadians(launchAngleDegrees);
        
        System.out.println("Ball launching to launchAngleDegrees = " + launchAngleDegrees);

        //java Math.sin and Math.cos always expect angles in radians.
        this.velocityX = BALL_SPEED * Math.sin(angleRadians);
        this.velocityY = BALL_SPEED * Math.cos(angleRadians);
		
	}
	
	public void moveWithPaddle(int newXPos) {
		ball.setX(newXPos);
	}
	
	public void reset() {
		this.isLaunched = false;
		this.velocityX = 0;
		this.velocityY = 0;
	}

}
