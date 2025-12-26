package view;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Screen;

public class InGameOverlays {
	
	private Screen screen = Screen.getScreen();
	private String currResolution;
	private int[] mainPanelSize;
	
	private JPanel pushToStartPanel;
	
	public InGameOverlays() {
		mainPanelSize = screen.getScreenResolution();
		currResolution =  mainPanelSize[0] + "x" + mainPanelSize[1];
		
		pushToStartPanel = createPushToStartPanel();
	}
	
	private JPanel createPushToStartPanel() {
		FlowLayout pushToStartPanelFlowLayout = new FlowLayout(FlowLayout.CENTER, 30, 30);
		JPanel pushToStartPanel = new JPanel(pushToStartPanelFlowLayout);
		pushToStartPanel.setBackground(Color.BLACK);
		
		JLabel pushToStartText = new JLabel("Push Space to Start Game!");
		pushToStartText.setForeground(Color.WHITE);
		pushToStartText.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 48));
	    // Optional glow/shadow effect
		pushToStartText.setOpaque(false);
		pushToStartPanel.add(pushToStartText);
		return pushToStartPanel;
	}
	
	public JPanel getPushToStartPanel() {
		return this.pushToStartPanel;
	}
	
}
