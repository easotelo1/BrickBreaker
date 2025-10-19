package view;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.Screen;

@SuppressWarnings("serial")
public class GameFrame extends JFrame {
	
	private static final GameFrame gameFrame = new GameFrame();
	
//	private final JFrame frame;
	private static CardLayout cardLayout; //http://stackoverflow.com/questions/21459718/how-do-i-switch-jpanels-inside-a-jframe/21460065#21460065
	private static JPanel mainPanel;
	private static MainMenuView mainMenuPanel; 
	private static SettingsView settingsPanel;
	private static GameView gamePanel;
	private Screen screen = Screen.getScreen();
	private int[] frameSize;
	
	public static GameFrame getGameFrame() {
		return gameFrame;
	}
	
	private GameFrame() {
		frameSize = screen.getScreenResolution();

		setTitle("BrickBreaker");
		setMinimumSize(new Dimension(frameSize[0], frameSize[1]));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		
		mainMenuPanel = new MainMenuView();
		mainPanel.add(mainMenuPanel, "menu");
		settingsPanel = new SettingsView(this);
		mainPanel.add(settingsPanel, "settings");
		gamePanel = new GameView();
		mainPanel.add(gamePanel, "game");
		
		cardLayout.show(mainPanel, "menu");
		
		add(mainPanel);
		
		setUndecorated(true); //TODO: turn on after implementing exit button
		pack();
		setVisible(true);
	}
	
	public static void setView(String panelName) {
		cardLayout.show(mainPanel, panelName);
		if(panelName.equals("game")) {
			SwingUtilities.invokeLater(()-> {
				gamePanel.requestFocusInWindow();
			});
		}
	}

	public void setNewDimensions(int[] newFrameSize) {
		frameSize = screen.getScreenResolution();
		setMinimumSize(new Dimension(frameSize[0], frameSize[1]));
		System.out.println("Resized to " + frameSize[0] + "x" + frameSize[1]);
		
		dispose();
		new GameFrame();
	}
	
}
