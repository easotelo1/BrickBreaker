package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Screen;

//one panel to handle frame per second events
//multiple panels for event based games
@SuppressWarnings("serial")
public class MainMenuPanel extends JPanel {
	
	private Screen screen = Screen.getScreen();
	private int[] mainPanelSize;
	private String currResolution;

//	private final JFrame mainGameFrame;
//	private static JPanel mainMenuPanel;
	
	//https://stackoverflow.com/questions/42964669/placing-button-panel-in-center-java-swing
	public MainMenuPanel() {
		mainPanelSize = screen.getScreenResolution();
		currResolution =  mainPanelSize[0] + "x" + mainPanelSize[1];

		//default resolution of 1280x720
		
		setBackground(Color.BLACK);
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.anchor = GridBagConstraints.NORTH;
        gbcLabel.gridwidth = GridBagConstraints.REMAINDER;
        gbcLabel.insets = new Insets(100, 20, 100, 20);
        JLabel title = new JLabel("<html><h1><strong><i><span style='font-size:40px'>Brick Breaker</i></span></strong></h1><hr></html>");
        add(title, gbcLabel);
        
        GridBagConstraints gbcMainButtons = new GridBagConstraints();
        gbcMainButtons.anchor = GridBagConstraints.CENTER;
        gbcMainButtons.fill = GridBagConstraints.HORIZONTAL;
        
        gbcMainButtons.insets = new Insets(10, 0, 10, 0);
        JPanel buttonsPanel = createMainButtonsPanel(gbcMainButtons);
        
//        double weighty;
//        switch(currResolution) {
//			case "1280x720":
//				settingsPanelFlowLayout = new FlowLayout(FlowLayout.RIGHT, 30, 20);
//				break;
//			case "1920x1080":
//				settingsPanelFlowLayout = new FlowLayout(FlowLayout.RIGHT, 30, 20);
//				break;
//			case "2560x1440":
//				settingsPanelFlowLayout = new FlowLayout(FlowLayout.RIGHT, 150, 60);
//				break;
//			default:
//				settingsPanelFlowLayout = new FlowLayout(FlowLayout.RIGHT, 30, 20);
//		}

        add(buttonsPanel, gbcMainButtons);
        
        GridBagConstraints gbcSettings = new GridBagConstraints();
        JPanel settingsPanel = createSettingsPanel();
        gbcSettings.anchor = GridBagConstraints.FIRST_LINE_END;
        gbcSettings.gridy = 0;
        gbcSettings.insets = new Insets(30,50,100,20);
        add(settingsPanel, gbcSettings);
//        gbcSettings.gridwidth = GridBagConstraints
        
	}
	
	private JPanel createMainButtonsPanel(GridBagConstraints gbc) {
        JPanel buttonsPanel = new JPanel(new GridLayout(0,1,0,20));
        buttonsPanel.setBackground(Color.BLACK);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(mainPanelSize[1]/6, mainPanelSize[0]/4, mainPanelSize[1]/6, mainPanelSize[0]/4));
        buttonsPanel.add(createButton("Play"), gbc);
        buttonsPanel.add(createButton("Exit"), gbc);
        return buttonsPanel;
	}
	
	private JButton createButton(String name) {
		JButton button = new JButton(name);
		button.setBackground(Color.DARK_GRAY);
		button.setForeground(Color.WHITE);
		button.setBorder(null);
		button.setFocusPainted(false);
		button.setPreferredSize(new Dimension(mainPanelSize[0]/6, mainPanelSize[1]/15));
		button.addActionListener(name.equals("Exit") ? e -> System.exit(0) : null);
		return button;
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

}
