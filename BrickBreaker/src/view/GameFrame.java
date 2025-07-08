package view;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Screen;

@SuppressWarnings("serial")
public class GameFrame extends JFrame {
	
	private static GameFrame gameFrame = new GameFrame();
	
//	private final JFrame frame;
	private static CardLayout cardLayout; //http://stackoverflow.com/questions/21459718/how-do-i-switch-jpanels-inside-a-jframe/21460065#21460065
	private static JPanel mainPanel;
	private static MainMenuPanel mainMenuPanel; 
	private static SettingsPanel settingsPanel;
	private Screen screen = Screen.getScreen();
	private int[] frameSize;
	
	public static GameFrame getGameFrame() {
		return gameFrame;
	}
	
	private GameFrame() {
		frameSize = screen.getScreenResolution();
		
		setTitle("BrickBreakerMain");
		setMinimumSize(new Dimension(frameSize[0], frameSize[1]));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		
		mainMenuPanel = new MainMenuPanel();
		mainPanel.add(mainMenuPanel, "menu");
		settingsPanel = new SettingsPanel(this);
		mainPanel.add(settingsPanel, "settings");
		
		cardLayout.show(mainPanel, "menu");
		
		add(mainPanel);
		
//		setUndecorated(true); //TODO: turn on after implementing exit button
		pack();
		setVisible(true);
	}
	
	public static void setView(String panelName) {
		cardLayout.show(mainPanel, panelName);
	}
	
	
	public void setNewDimensions(int[] newFrameSize) {
		frameSize = screen.getScreenResolution();
		setMinimumSize(new Dimension(frameSize[0], frameSize[1]));
		System.out.println("Resized to " + frameSize[0] + "x" + frameSize[1]);
		
		dispose();
		new GameFrame();
	}
	
}
