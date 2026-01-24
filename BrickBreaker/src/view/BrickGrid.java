package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import model.Brick;
import model.Screen;

public class BrickGrid {
	private Screen screen = Screen.getScreen();
	private int[] mainPanelSize;
	private int screenWidth;
	private int screenHeight;
	
	private ArrayList<Brick> bricks = new ArrayList<Brick>();
	
	private static final Color[] ROW_COLORS = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE};
	private static final int VERTICAL_PADDING = 5;
	
	public BrickGrid() {
		mainPanelSize = screen.getScreenResolution();
		this.screenWidth = mainPanelSize[0];
		this.screenHeight = mainPanelSize[1];
		
		generateGrid();
	}
	
	private void generateGrid() {
		int brickWidth = (int) (screenWidth * 0.08);
		int brickHeight = (int) (screenHeight * 0.04);
		
		
		int rows = 5;
		int cols = 10;
		int startY = (int) (screenHeight * 0.08);
		
		for(int row = 0; row < rows; row++) {
			Color color = ROW_COLORS[row];
			for(int col = 0; col < cols; col++) {
				int x = col * brickWidth + (screenWidth - cols * brickWidth) / 2;
				int y = startY + row * (brickHeight + VERTICAL_PADDING);
				Brick brick = new Brick(x, y, brickWidth, brickHeight, color);
				bricks.add(brick);
			}
		}
	}
	
	public void draw(Graphics2D g2d) {
        for (Brick brick : bricks) {
            if (brick.isAlive()) {
                g2d.setColor(brick.getColor());
                g2d.fillRect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
                g2d.setColor(Color.BLACK);
                g2d.drawRect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
            }
        }
    }
	
	public ArrayList<Brick> getBricks() {
		return this.bricks;
	}
	
	public void updateSizeAndLayout() {
		System.out.println("updated brick grid size and layout");
		bricks.clear();
        mainPanelSize = screen.getScreenResolution();
		this.screenWidth = mainPanelSize[0];
		this.screenHeight = mainPanelSize[1];
        generateGrid();
    }
}
