package controller;

import model.Ball;

public class BallController {
	
	private Ball ball;
	
	private double velocityX = 0;
	private double velocityY = 0;
	private static final double BALL_SPEED = 0; //TODO: update later
	
	public BallController(Ball ball) {
		this.ball = ball;
	}

}
