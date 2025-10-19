package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import model.Screen;

public class SettingsButtonPanel {
	
	private Screen screen = Screen.getScreen();
	private String currResolution;
	private int[] mainPanelSize;
	
	private JPanel settingsButtonPanel;
	
	public SettingsButtonPanel() {
		mainPanelSize = screen.getScreenResolution();
		currResolution =  mainPanelSize[0] + "x" + mainPanelSize[1];
		
		this.settingsButtonPanel = createSettingsPanel();
		
		
	}
	
	private JPanel createSettingsPanel() {
		FlowLayout settingsPanelFlowLayout;
		switch(currResolution) {
			case "1280x720":
				settingsPanelFlowLayout = new FlowLayout(FlowLayout.RIGHT, 30, 20);
				break;
			case "1920x1080":
				settingsPanelFlowLayout = new FlowLayout(FlowLayout.RIGHT, 30, 20);
				break;
			case "2560x1440":
				settingsPanelFlowLayout = new FlowLayout(FlowLayout.RIGHT, 150, 60);
				break;
			default:
				settingsPanelFlowLayout = new FlowLayout(FlowLayout.RIGHT, 30, 20);
		}
		JPanel settingsPanel = new JPanel(settingsPanelFlowLayout); // default constructor is a FlowLayout
		settingsPanel.setBackground(Color.BLACK);
//		JLabel testLabel = new JLabel("Hello World");
//		settingsPanel.add(testLabel);
		settingsPanel.add(createSettingsButton());
		return settingsPanel;
	}
	
	private JButton createSettingsButton() {
		try {
			Image img = ImageIO.read(getClass().getResource("../resources/settings-512.png"));
			Image scaledImg;
			switch(currResolution) {
				case "1280x720":
					scaledImg = img.getScaledInstance(mainPanelSize[0]/25, mainPanelSize[1]/18, java.awt.Image.SCALE_SMOOTH);
					break;
				case "1920x1080":
					scaledImg = img.getScaledInstance(mainPanelSize[0]/30, mainPanelSize[1]/20, java.awt.Image.SCALE_SMOOTH);
					break;
				case "2560x1440":
					scaledImg = img.getScaledInstance(mainPanelSize[0]/40, mainPanelSize[1]/24, java.awt.Image.SCALE_SMOOTH);
					break;
				default:
					scaledImg = img.getScaledInstance(mainPanelSize[0]/20, mainPanelSize[1]/13, java.awt.Image.SCALE_SMOOTH);
			}
//			Image scaledImg = img.getScaledInstance(mainPanelSize[0]/25, mainPanelSize[1]/18, java.awt.Image.SCALE_SMOOTH);
			JButton settingsButton = new JButton(new ImageIcon(scaledImg));
			settingsButton.setBackground(Color.BLACK);
			settingsButton.setFocusPainted(false);
			settingsButton.setBorderPainted(false);
			settingsButton.addActionListener(e -> GameFrame.setView("settings")); 
			return settingsButton;
		} catch(Exception ex) {
			System.out.println(ex);
			
		}
		return null;
	}
	
	public JPanel getPanel() {
		return this.settingsButtonPanel;
	}

}
