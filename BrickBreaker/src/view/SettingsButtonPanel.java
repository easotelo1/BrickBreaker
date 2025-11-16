package view;

import java.awt.Color;
import java.awt.Dimension;
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
			Dimension buttonDim;

			switch(currResolution) {
	            case "1280x720":
	                buttonDim = new Dimension(mainPanelSize[0]/25, mainPanelSize[1]/18);
	                break;
	            case "1920x1080":
	                buttonDim = new Dimension(mainPanelSize[0]/30, mainPanelSize[1]/20);
	                break;
	            case "2560x1440":
	                buttonDim = new Dimension(mainPanelSize[0]/40, mainPanelSize[1]/24);
	                break;
	            default:
	                buttonDim = new Dimension(mainPanelSize[0]/20, mainPanelSize[1]/13);
			}
			
			scaledImg = img.getScaledInstance(buttonDim.width, buttonDim.height, java.awt.Image.SCALE_SMOOTH);
			
			JButton settingsButton = new JButton(new ImageIcon(scaledImg));
			settingsButton.setBackground(Color.BLACK);
			settingsButton.setFocusPainted(false);
			settingsButton.setBorderPainted(false);
			
	        settingsButton.setPreferredSize(buttonDim);
	        settingsButton.setMinimumSize(buttonDim);
	        settingsButton.setMaximumSize(buttonDim);
			
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
