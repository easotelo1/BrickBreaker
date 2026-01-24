package model;

import java.awt.Color;

public class Brick {
	private int x, y;
	private int width, height;
	private boolean alive;
	private Color color;
	
	public Brick(int x, int y, int width, int height, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		
		this.alive = true;
	}
	
	public void destroy() {
		this.alive = false;
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
	
	public boolean isAlive() {
		return this.alive;
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	// AABB intersection (for collision)
    public boolean intersects(int ballX, int ballY, int ballW, int ballH) {
        return ballX < x + width && ballX + ballW > x &&
               ballY < y + height && ballY + ballH > y;
    }
   
}
