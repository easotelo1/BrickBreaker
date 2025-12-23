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

import controller.BrickBreaker;
import model.Screen;

//one panel to handle frame per second events
//multiple panels for event based games
@SuppressWarnings("serial")
public class MainMenuView extends JPanel {
	
//	private BrickBreaker brickBreaker = BrickBreaker.getInstance();
	
	private Screen screen = Screen.getScreen();
	private int[] mainPanelSize;
	private String currResolution;
	
    private JLabel titleLabel;
    private JPanel buttonsPanel;
    private JPanel settingsButtonPanel;
    private GridBagConstraints gbcLabel;
    private GridBagConstraints gbcMainButtons;
    private GridBagConstraints gbcSettings;
		
	//https://stackoverflow.com/questions/42964669/placing-button-panel-in-center-java-swing
	public MainMenuView() {
		mainPanelSize = screen.getScreenResolution();
		currResolution =  mainPanelSize[0] + "x" + mainPanelSize[1];
		
		setBackground(Color.BLACK);
        setLayout(new GridBagLayout());
        
        // Initialize GBCs
        gbcSettings = new GridBagConstraints();
        gbcLabel = new GridBagConstraints();
        gbcMainButtons = new GridBagConstraints();

        setupComponents();
	}
	
	private void setupComponents() {
        this.removeAll();

        // --- Settings Button Panel ---
        settingsButtonPanel = createSettingsPanel();
        gbcSettings.anchor = GridBagConstraints.NORTHEAST;
        gbcSettings.gridwidth = GridBagConstraints.REMAINDER;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 1;
        add(settingsButtonPanel, gbcSettings);
        
        // --- Title Label ---
        gbcLabel.anchor = GridBagConstraints.NORTH;
        gbcLabel.gridwidth = GridBagConstraints.REMAINDER;
        gbcLabel.weighty = 0.5;
        
        // Set Insets and Title text based on resolution
        switch(currResolution) {
			case "1280x720":
				gbcLabel.insets = new Insets(-50, 20, 100, 20);
                titleLabel = new JLabel("<html><h1><strong><i><span style='font-size:40px'>Brick Breaker</i></span></strong></h1><hr></html>");
				break;
			case "1920x1080":
				gbcLabel.insets = new Insets(-200, 20, 100, 20);
                titleLabel = new JLabel("<html><h1><strong><i><span style='font-size:60px'>Brick Breaker</i></span></strong></h1><hr></html>");
				break;
			case "2560x1440":
				gbcLabel.insets = new Insets(-300, 20, 100, 20);
                titleLabel = new JLabel("<html><h1><strong><i><span style='font-size:80px'>Brick Breaker</i></span></strong></h1><hr></html>");
				break;
			default:
				gbcLabel.insets = new Insets(-50, 20, 100, 20);
                titleLabel = new JLabel("<html><h1><strong><i><span style='font-size:40px'>Brick Breaker</i></span></strong></h1><hr></html>");
		}
        add(titleLabel, gbcLabel);
        
        // --- Main Buttons Panel ---
        gbcMainButtons.anchor = GridBagConstraints.CENTER;
        gbcMainButtons.weighty = 1;
        
        switch(currResolution) {
			case "1280x720":
				gbcMainButtons.insets = new Insets(-50, 20, 0, 20);
				break;
			case "1920x1080":
				gbcMainButtons.insets = new Insets(-100, 20, 0, 20);
				break;
			case "2560x1440":
				gbcMainButtons.insets = new Insets(100, 20, -50, 20);
				break;
			default:
				gbcMainButtons.insets = new Insets(-50, 20, 0, 20);
		}
        
        buttonsPanel = createMainButtonsPanel();
        add(buttonsPanel, gbcMainButtons);
    }
	
	private JPanel createMainButtonsPanel() {
        JPanel panel = new JPanel(new GridLayout(0,1,0,20));

        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createEmptyBorder(mainPanelSize[1]/6, mainPanelSize[0]/4, mainPanelSize[1]/6, mainPanelSize[0]/4));
        panel.add(createButton("Play"));
        panel.add(createButton("Exit"));
        return panel;
	}
	
	
	private JButton createButton(String name) {
		JButton button = new JButton(name);
		button.setBackground(Color.DARK_GRAY);
		button.setForeground(Color.WHITE);
		button.setBorder(null);
		button.setFocusPainted(false);
		
		Dimension newDimension;
		switch(currResolution) {
			case "1280x720":
				newDimension = new Dimension(mainPanelSize[0]/6, mainPanelSize[1]/15);
				break;
			case "1920x1080":
				newDimension = new Dimension(mainPanelSize[0]/6, mainPanelSize[1]/20);
				break;
			case "2560x1440":
				newDimension = new Dimension(mainPanelSize[0]/6, mainPanelSize[1]/25);
				break;
			default:
				newDimension = new Dimension(mainPanelSize[0]/6, mainPanelSize[1]/15);
		}
		button.setPreferredSize(newDimension);
		button.addActionListener(name.equals("Exit") ? e -> System.exit(0) : null);
		button.addActionListener(name.equals("Play") ? e -> BrickBreaker.getInstance().startGame() : null);
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
	
    public void updateSizeAndLayout() {
        mainPanelSize = screen.getScreenResolution();
        currResolution =  mainPanelSize[0] + "x" + mainPanelSize[1];
        System.out.println("in MainMenuView, currResolution =- " + currResolution);
        
        setupComponents();
        
        revalidate();
        repaint();
    }

}
