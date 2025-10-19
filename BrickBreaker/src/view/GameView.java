package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import model.Paddle;
import model.Screen;

@SuppressWarnings("serial")
public class GameView extends JPanel {
	private Screen screen = Screen.getScreen();
	private int[] mainPanelSize;
	private String currResolution;
	
	private Paddle paddle;
	
	public GameView() {
		mainPanelSize = screen.getScreenResolution();
		currResolution =  mainPanelSize[0] + "x" + mainPanelSize[1];
		setFocusable(true);
		
		//TODO: TEMPORARY. Remove after adding pause function
		GridBagConstraints gbcSettings = new GridBagConstraints();
        SettingsButtonPanel settingsButtonPanel = new SettingsButtonPanel();
        gbcSettings.anchor = GridBagConstraints.NORTHEAST;
        gbcSettings.gridwidth = GridBagConstraints.REMAINDER;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 1;
        add(settingsButtonPanel.getPanel(), gbcSettings);

		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(mainPanelSize[0], mainPanelSize[1]));
		updateInitialPaddlePosition();
	
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				updateInitialPaddlePosition();
			}
		});
		
		//TODO: Movement works. fix collision for different resolutions
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				System.out.println("Key Pressed = " + e.getKeyCode());
				if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
					int newXPos = paddle.getX()-20;
					if(newXPos < 0+10) {
						newXPos = 0+10;
					}
					paddle.setX(newXPos);
				}
				else if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
					int newXPos = paddle.getX()+20;
					if(newXPos > mainPanelSize[0] - paddle.getWidth()) {
						newXPos = mainPanelSize[0]- paddle.getWidth()-10;
					}
					paddle.setX(newXPos);
				}
				repaint();
				System.out.println("Paddle X: " + paddle.getX());
			}
			
		});
	
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.fillRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());
	}
	
	private void updateInitialPaddlePosition() {
		int paddleWidth;
		int paddleHeight;
		System.out.println("currResolution = " + currResolution);
		switch(currResolution) {
			case "1280x720":
				paddleWidth = 130;
				paddleHeight = 25;
				break;
			case "1920x1080":
				paddleWidth = 200;
				paddleHeight = 38;
				break;
			case "2560x1440":
				paddleWidth = 300;
				paddleHeight = 50;
				break;
			default:
				paddleWidth = 130;
				paddleHeight = 25;
		}

		int paddleX = (mainPanelSize[0] - paddleWidth) / 2;
		int paddleY = mainPanelSize[1] - paddleHeight - 100;
		paddle = new Paddle(paddleX, paddleY, paddleWidth, paddleHeight);
		
		System.out.println("paddleWidth = " + paddleWidth);
	}
}
