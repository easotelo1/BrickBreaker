package view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import model.Paddle;
import model.Screen;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	private Screen screen = Screen.getScreen();
	private int[] mainPanelSize;
	private String currResolution;
	
	private Paddle paddle;
	
	public GamePanel() {
		mainPanelSize = screen.getScreenResolution();

		setBackground(Color.BLACK);
		
		paddle = new Paddle(mainPanelSize[0]/2, mainPanelSize[1]/2, 130, 25); //TODO: Just trying to get a simple drawing right. worry about threading later
	}
	
	public void paintComponent(Graphics g) {
		g.fillRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());
	}
}
