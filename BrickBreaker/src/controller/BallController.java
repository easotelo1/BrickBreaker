package controller;

import java.util.Random;

import model.Ball;

public class BallController {
	
	private Ball ball;
	private final Random rand = new Random();
	
	private double velocityX = 0;
	private double velocityY = 0;
	private static final double BALL_SPEED = 500; //TODO: update later
	
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
	
	public void bounceOffPaddle(int paddleX, int paddleY, int paddleWidth, int screenWidth) {
		if(this.velocityY <= 0 ) {
			return;
		}
	    // Calculate relative hit position (0.0 = left edge, 1.0 = right edge)
	    double ballCenterX = ball.getX() + (ball.getWidth() / 2.0);
	    double paddleCenterX = paddleX + (paddleWidth / 2.0);
	    double relativeHit = (ballCenterX - paddleCenterX) / (paddleWidth / 2.0);  // -1.0 to +1.0
	    
	    //clamps down on a [-1,1] relative hit range in extreme edge cases
	    relativeHit = Math.max(-1.0, Math.min(1.0, relativeHit));

//	    System.out.println("ballCenterX = " + ballCenterX);
//	    System.out.println("paddleCenterX = " + paddleCenterX);
//	    System.out.println("relativeHit = " + relativeHit);


	    double maxBounceAngle = Math.PI / 4; // +/- 45 degrees max angle
//	    System.out.println("maxBounceAngle = " + maxBounceAngle);

	    double bounceAngleOffset = relativeHit * maxBounceAngle; 		//calculates angle based on where the ball landed * max angle
//	    System.out.println("bounceAngleOffset = " + bounceAngleOffset);
	    	    
	    // speed is preserved using pythagoreans theorem : sqrt(vx^2 + vy^2)
	    double speed = Math.hypot(velocityX, velocityY);
//	    System.out.println("speed = " + speed);

	    // simple pythagoreans trig 
	    double newVx = speed * Math.sin(bounceAngleOffset);			//horizontal angle
	    double newVy = -speed * Math.cos(bounceAngleOffset); 		//vertical angle
	    
//	    System.out.println("oldVx = " + this.velocityX);
//	    System.out.println("oldVy = " + this.velocityY);
//
//	    System.out.println("newVx = " + newVx);
//	    System.out.println("newVy = " + newVy);


	    this.velocityX = newVx;
	    this.velocityY = newVy;
	    
	    //at one point the ball clipped into the right wall and wouldn't unclip. hopefully this works
	    ball.setY(paddleY - ball.getHeight() - 1);
        if (ball.getX() <= 0 || ball.getX() + ball.getWidth() >= screenWidth) {
        	this.velocityX = -(this.velocityX);
        }
	    
	    //debug sleep when paddle hits
//	    try {
//			Thread.sleep(200000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public void reset(int xPos, int yPos) {
		ball.setX(xPos);
		ball.setY(yPos);
		this.isLaunched = false;
		this.velocityX = 0;
		this.velocityY = 0;
	}

}
