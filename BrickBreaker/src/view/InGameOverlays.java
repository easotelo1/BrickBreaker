package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import model.Screen;

@SuppressWarnings("serial")
public class InGameOverlays extends JPanel {
	private Screen screen = Screen.getScreen();
	private int[] mainPanelSize;

	private int currentScore;
	private int currentLives;
	private boolean showLaunchOverlay;
	
	//cached fontmetrics and text sizes
	private Font hudFont;
	private Font launchFont;
	private FontMetrics launchMetrics;
	private final String launchText = "Push SPACE to launch!";
	private int launchTextWidth;
	private int launchTextHeight;
	private int launchTextAscent;
	private int launchCenterX;
	private int launchCenterY;

	public InGameOverlays() {
		mainPanelSize = screen.getScreenResolution();

		this.currentScore = 0;
		this.currentLives = 3;
		this.showLaunchOverlay = true;
		
		updateFontMetrics();
	}
	
	private void updateFontMetrics() {
		int screenWidth = mainPanelSize[0];
		int screenHeight = mainPanelSize[1];
		
		int hudSize = (int) (screenWidth * 0.02); //2% of screen width
		hudFont = new Font("Arial", Font.BOLD, hudSize);
		
		int launchSize = (int) (screenWidth * 0.04);
		launchFont = new Font("Arial", Font.BOLD, launchSize);
		
		BufferedImage dummy = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = dummy.createGraphics();
        launchMetrics = g2d.getFontMetrics(launchFont);
        launchTextWidth = launchMetrics.stringWidth(launchText);
        launchTextHeight = launchMetrics.getHeight();
        launchTextAscent = launchMetrics.getAscent();
        launchCenterX = (screenWidth - launchTextWidth) / 2;
        launchCenterY = (screenHeight - launchTextHeight) / 2 + launchTextAscent;
        g2d.dispose();
	}
	
	protected void draw(Graphics2D g2d) {	
		int screenHeight = mainPanelSize[1];
		
		g2d.setFont(hudFont);
		g2d.setColor(Color.DARK_GRAY);
		
		int x = 20;
		int y = (int) (screenHeight * 0.055);
		
		String scoreText = "Score: " + this.currentScore;
		FontMetrics fm = g2d.getFontMetrics();
		
		g2d.drawString("Score: " + this.currentScore, x, y);
		
		int scoreWidth = fm.stringWidth(scoreText);
		int gap = (int) (mainPanelSize[0] * 0.015);
		int livesX = x + scoreWidth + gap;
		
		g2d.drawString("Lives: " + this.currentLives, livesX, y);
		
		if (showLaunchOverlay) {
			g2d.setFont(launchFont);
			g2d.drawString(launchText, launchCenterX, launchCenterY);
		}
	}

	public void showLaunchOverlay() {
		showLaunchOverlay = true;
		repaint();
	}

	public void hideLaunchOverlay() {
		showLaunchOverlay = false;
		repaint();
	}
	
	public void updateSizeAndLayout() {
        mainPanelSize = screen.getScreenResolution();
        updateFontMetrics();
    }

}