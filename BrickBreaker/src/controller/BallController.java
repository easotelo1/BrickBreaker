package controller;

import java.util.Random;

import model.Ball;

public class BallController {
	
	private Ball ball;
	private final Random rand = new Random();
	
	private double velocityX = 0;
	private double velocityY = 0;
	private static final double SPEED_RATIO = 0.5;
	
	private static final double LAUNCH_CONE_ANGLE_DEGREES = 65.0;  // Total cone width: +/- 20 degrees from straight up
	
	private boolean isLaunched = false;
	
	private double speed;
	
	private boolean alive;
	
	public BallController(Ball ball) {
		this.ball = ball;
		this.speed = 0;
		this.alive = true;
	}
	
	public boolean update(double timeDeltaSeconds, int screenWidth, int screenHeight) {
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

        if(ball.getY() >= screenHeight) {
        	this.alive = false;
        }

        return this.alive;
		
	}
	
	
	public void launch(int screenWidth) {
		if(this.isLaunched) {
			return;
		}
		
		this.isLaunched = true;
		
		this.speed = screenWidth * SPEED_RATIO;
		
		//straight up is 180 degrees.
		//I want a random number between 115 to 245 degrees. This makes 180 +/- 65 (LAUNCH_CONE_ANGLE_DEGREES)
		double randomOffset = rand.nextDouble(LAUNCH_CONE_ANGLE_DEGREES) * (rand.nextBoolean() ? -1 : 1);
        double launchAngleDegrees = 180 + randomOffset;  // 115 to 245 degrees
        double angleRadians = Math.toRadians(launchAngleDegrees);
        
        System.out.println("Ball launching to launchAngleDegrees = " + launchAngleDegrees);

        //java Math.sin and Math.cos always expect angles in radians.
        this.velocityX = this.speed * Math.sin(angleRadians);
        this.velocityY = this.speed * Math.cos(angleRadians);
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

	    double maxBounceAngle = Math.PI / 4; // +/- 45 degrees max angle

	    double bounceAngleOffset = relativeHit * maxBounceAngle; 		//calculates angle based on where the ball landed * max angle
	    	    
	    // speed is preserved using pythagoreans theorem : sqrt(vx^2 + vy^2)
	    double speed = Math.hypot(velocityX, velocityY);

	    // simple pythagoreans trig 
	    double newVx = speed * Math.sin(bounceAngleOffset);			//horizontal angle
	    double newVy = -speed * Math.cos(bounceAngleOffset); 		//vertical angle

	    this.velocityX = newVx;
	    this.velocityY = newVy;
	    
	    //at one point the ball clipped into the right wall and wouldn't unclip. hopefully this works
	    ball.setY(paddleY - ball.getHeight() - 1);
        if (ball.getX() <= 0 || ball.getX() + ball.getWidth() >= screenWidth) {
        	this.velocityX = -(this.velocityX);
        }
	}
	
	public void bounceOffBrick(int brickX, int brickWidth) {
		boolean hitLeftSide = (ball.getX() + ball.getWidth() > brickX) && (ball.getX() + ball.getWidth() < brickX + 10);  // Within 10px of left edge
	    boolean hitRightSide = (ball.getX() < brickX + brickWidth) && (ball.getX() > brickX + brickWidth - 10);  // Within 10px of right edge
	    
	    if (hitLeftSide || hitRightSide) {
	        velocityX = -velocityX;  // Flip X to unstick from side
	    }
	    else {
	    	velocityY = -velocityY;
	    }
	    
	    //edgeCase Unstuck
	    if (hitLeftSide) {
	        ball.setX(brickX - ball.getWidth());
	    } else if (hitRightSide) {
	        ball.setX(brickX + brickWidth);
	    }
	}
	
	public void reset() {
		this.isLaunched = false;
		this.velocityX = 0;
		this.velocityY = 0;
		this.alive = true;
	}
	
	public void setBall(Ball ball) {
		this.ball = ball;
	}
	
	public boolean getAlive() {
		return this.alive;
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}

}
