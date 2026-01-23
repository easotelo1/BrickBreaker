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
	private boolean showGameOver;
	private boolean yesSelected;
	private boolean gameWon;
	
	//cached fontmetrics and text sizes
	private Font hudFont;
	private Font launchFont;
	private Font gameWonFont;
	private Font gameOverFont;
	private Font playAgainFont;
	private Font yesNoFont;
	
	private FontMetrics launchMetrics;
	private FontMetrics gameWonMetrics;
	private FontMetrics gameOverMetrics;
	private FontMetrics playAgainMetrics;
	private FontMetrics yesNoMetrics;
	
	private final String launchText = "Push SPACE to launch!";
	private final String gameWonText = "YOU WIN";
	private final String gameOverText = "GAME OVER";
	private final String playAgainText = "Play Again?";
	private final String yesText = "Yes";
	private final String noText= "No";
	
	private int launchTextWidth;
	private int launchTextHeight;
	private int launchTextAscent;
	private int launchCenterX;
	private int launchCenterY;
	
	private int gameWonTextWidth;
	private int gameWonTextHeight;
	private int gameWonTextAscent;
	private int gameWonCenterX;
	private int gameWonCenterY;
	
	private int gameOverTextWidth;
	private int gameOverTextHeight;
	private int gameOverTextAscent;
	private int gameOverCenterX;
	private int gameOverCenterY;
	
	private int playAgainTextWidth;
	private int playAgainTextHeight;
	private int playAgainCenterX;
	private int playAgainCenterY;
	
	private int yesTextWidth;
	private int noTextWidth;
	private int yesCenterX;
	private int yesCenterY;
	private int noCenterX;
	private int noCenterY;

	public InGameOverlays() {
		mainPanelSize = screen.getScreenResolution();

		this.currentScore = 0;
		this.currentLives = 3;
		this.showLaunchOverlay = true;
		this.showGameOver = false;
		this.yesSelected = true;
		
		this.gameWon = false;
		
		setOpaque(false);
		setFocusable(false);
		
		updateFontMetrics();
	}
	
	private void updateFontMetrics() {
		int screenWidth = mainPanelSize[0];
		int screenHeight = mainPanelSize[1];
		
		int hudSize = (int) (screenWidth * 0.02); //2% of screen width
		hudFont = new Font("Arial", Font.BOLD, hudSize);
		
		int launchSize = (int) (screenWidth * 0.04);
		launchFont = new Font("Arial", Font.BOLD, launchSize);
		
		int gameWonSize = (int) (screenWidth * 0.035);
		gameWonFont = new Font("Arial", Font.BOLD, gameWonSize);
		
		int gameOverSize = (int) (screenWidth * 0.035);
		gameOverFont = new Font("Arial", Font.BOLD, gameOverSize);
		
		int playAgainSize = (int) (screenWidth * 0.04);
		playAgainFont = new Font("Arial", Font.BOLD, playAgainSize);
		
		int yesNoSize = (int) (screenWidth * 0.03);
		yesNoFont = new Font("Arial", Font.BOLD, yesNoSize);
		
		BufferedImage dummy = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = dummy.createGraphics();
        
        launchMetrics = g2d.getFontMetrics(launchFont);
        launchTextWidth = launchMetrics.stringWidth(launchText);
        launchTextHeight = launchMetrics.getHeight();
        launchTextAscent = launchMetrics.getAscent();
        launchCenterX = (screenWidth - launchTextWidth) / 2;
        launchCenterY = (screenHeight - launchTextHeight) / 2 + launchTextAscent;
        
        gameOverMetrics = g2d.getFontMetrics(gameOverFont);
        gameOverTextWidth = gameOverMetrics.stringWidth(gameOverText);
        gameOverTextHeight = gameOverMetrics.getHeight();
        gameOverTextAscent = gameOverMetrics.getAscent();
        gameOverCenterX = (screenWidth - gameOverTextWidth) / 2;
        gameOverCenterY = (screenHeight / 2) - (int) (screenHeight * 0.2) + gameOverTextAscent;

        gameWonMetrics = g2d.getFontMetrics(gameWonFont);
        gameWonTextWidth = gameWonMetrics.stringWidth(gameWonText);
        gameWonTextHeight = gameWonMetrics.getHeight();
        gameWonTextAscent = gameWonMetrics.getAscent();
        gameWonCenterX = (screenWidth - gameWonTextWidth) / 2;
        gameWonCenterY = (screenHeight / 2) - (int) (screenHeight * 0.2) + gameWonTextAscent;
        
        playAgainMetrics = g2d.getFontMetrics(playAgainFont);
        playAgainTextWidth = playAgainMetrics.stringWidth(playAgainText);
        playAgainTextHeight = playAgainMetrics.getHeight();
        playAgainCenterX = (screenWidth - playAgainTextWidth) / 2;
        playAgainCenterY = gameOverCenterY + gameOverTextHeight + 20;

        yesNoMetrics = g2d.getFontMetrics(yesNoFont);
        yesTextWidth = yesNoMetrics.stringWidth(yesText);
        noTextWidth = yesNoMetrics.stringWidth(noText);
        
        int totalYesNoWidth = yesTextWidth + noTextWidth + 60;

        yesCenterX = (screenWidth - totalYesNoWidth) / 2;
        yesCenterY = playAgainCenterY + playAgainTextHeight + 20;
        
        noCenterX = ((screenWidth - totalYesNoWidth) / 2) + yesTextWidth + 60;
        noCenterY = yesCenterY;
        
        g2d.dispose();
	}
	
	protected void draw(Graphics2D g2d) {	
		int screenHeight = mainPanelSize[1];
		
		/////////HUD SECTION ///////////////
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
		
		///////////PUSH SPACE TO LAUNCH///////////
		if (showLaunchOverlay) {
			g2d.setFont(launchFont);
			g2d.drawString(launchText, launchCenterX, launchCenterY);
		}
		
		///////////GAME OVER///////////
		if(showGameOver || gameWon) {
			
			setFocusable(true);

			if(gameWon) {
				g2d.setFont(gameWonFont);
				g2d.drawString(gameWonText, gameWonCenterX, gameWonCenterY);
				playAgainCenterY = gameOverCenterY + gameWonTextHeight + 20;
			}
			else {
				g2d.setFont(gameOverFont);
				g2d.drawString(gameOverText, gameOverCenterX, gameOverCenterY);
			}
			
			g2d.setFont(playAgainFont);
			g2d.drawString(playAgainText, playAgainCenterX, playAgainCenterY);
			
			g2d.setFont(yesNoFont);
			g2d.setColor(yesSelected ? Color.LIGHT_GRAY : Color.DARK_GRAY);
			g2d.drawString(yesText, yesCenterX, yesCenterY);
			
			g2d.setColor(!yesSelected ? Color.LIGHT_GRAY : Color.DARK_GRAY);
			g2d.drawString(noText, noCenterX, noCenterY);
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
	
	public void showGameOver() {
        showGameOver = true;
        repaint();
    }
	
	public void hideGameOver() {
        showGameOver = false;
        repaint();
    }
	
	public void decreaseLives() {
		this.currentLives--;
	}
	
	public int getCurrentScore() {
		return this.currentScore;
	}
	
	public void updateScore(int newScore) {
		this.currentScore = newScore;
	}
	
	public int getCurrentLives() {
		return this.currentLives;
	}
	
	public void toggleYesNoSelection() {
        yesSelected = !yesSelected;
        repaint();
    }
	
	public boolean isYesSelected() {
        return yesSelected;
    }
	
	public void winGame() {
		this.gameWon = true;
		repaint();
	}
	
	public boolean getGameWon() {
		return this.gameWon;
	}
	
	public void updateSizeAndLayout() {
		mainPanelSize = screen.getScreenResolution();
		updateFontMetrics();
		this.gameWon = false;
		repaint();
	}
	
	public void resetHUD() {
		this.currentLives = 3;
		this.currentScore = 0;
		this.showLaunchOverlay = true;
		this.showGameOver = false;
		this.gameWon = false;
		repaint();
	}
	

}