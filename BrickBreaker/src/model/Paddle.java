package model;

public class Paddle {
	
	public int x, y; 
	public int width, height;
	
	private double velocityX = 0;
	
    private static final int PADDLE_SPEED = 500; //in pixels
	private static final double PADDLE_LEFT_BOUNDS = 0.1;
	private static final double PADDLE_RIGHT_BOUNDS = 1.1;
	
	public Paddle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
    public void update(double timeDeltaSeconds, int screenWidth) {
        // Calculate the proposed movement amount
        int moveAmount = (int) (this.velocityX * timeDeltaSeconds);
        int newXPos = this.x + moveAmount;
        
        // Calculate the actual pixel bounds based on the screen width/paddle width ratios
        int minX = (int) (this.width * PADDLE_LEFT_BOUNDS);
        int maxX = screenWidth - this.width - (int) (this.width * (PADDLE_RIGHT_BOUNDS - 1.0));

        if (newXPos < minX) {
            newXPos = minX;
        } else if (newXPos > maxX) {
            newXPos = maxX;
        }

        this.x = newXPos;
    }

    public void moveLeft() {
        this.velocityX = -PADDLE_SPEED;
    }
    
    public void moveRight() {
        this.velocityX = PADDLE_SPEED;
    }
    
    public void stop() {
        this.velocityX = 0;
    }
	
	
	public int getX() {
		return this.x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
}
