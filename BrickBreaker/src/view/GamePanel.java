package view;

import java.awt.Color;

import javax.swing.JPanel;

import model.Screen;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	private Screen screen = Screen.getScreen();
	private int[] mainPanelSize;
	private String currResolution;
	
	public GamePanel() {
		setBackground(Color.BLACK);
	}
}
