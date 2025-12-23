package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import model.Screen;

@SuppressWarnings("serial")
public class SettingsView extends JPanel {
	
	private Screen screen = Screen.getScreen();
	private int[] mainPanelSize;
	private final String[] resolutions = {"1280x720", "1920x1080", "2560x1440"};
	
	private String currResolution;
	private String resolutionSelection = "1280x720";;
	private JFrame gameFrame;
	
    private JPanel backButtonPanel;
    private JPanel settingsOptionsPanel;
    private JPanel applyButtonPanel;
    private JComboBox<String> resolutionComboBox;
	
	public SettingsView(JFrame gameFrame) {
		this.gameFrame = gameFrame;
		mainPanelSize = screen.getScreenResolution();
		currResolution =  mainPanelSize[0] + "x" + mainPanelSize[1];
		setBackground(Color.BLACK);
		setLayout(new GridBagLayout());
		
		setFocusable(true);
		addKeyListeners();
        setupComponents();
	}
	
	private void setupComponents() {
        this.removeAll(); // Clear existing components

		GridBagConstraints gbcBackButton = new GridBagConstraints();
		gbcBackButton.anchor = GridBagConstraints.NORTHWEST;
		gbcBackButton.gridwidth = GridBagConstraints.REMAINDER;
		backButtonPanel = createBackButtonPanel();
		gbcBackButton.weightx = 1;
		gbcBackButton.weighty = 0.4;
		add(backButtonPanel, gbcBackButton);

		GridBagConstraints gbcSettingsButtons = new GridBagConstraints();
		gbcSettingsButtons.anchor = GridBagConstraints.PAGE_START;
		gbcSettingsButtons.weighty = 0.4;
		gbcSettingsButtons.gridwidth = GridBagConstraints.REMAINDER;
		
		settingsOptionsPanel = createSettingsOptionsPanel();
		add(settingsOptionsPanel, gbcSettingsButtons);
		
		GridBagConstraints gbcApplyButton = new GridBagConstraints();;
		gbcApplyButton.anchor = GridBagConstraints.PAGE_START;
		gbcApplyButton.weighty = 1;
		gbcApplyButton.gridwidth = GridBagConstraints.REMAINDER;
		gbcApplyButton.gridheight = GridBagConstraints.REMAINDER;
		applyButtonPanel = createApplyButtonPanel();
		add(applyButtonPanel, gbcApplyButton);
    }
	
	private JPanel createApplyButtonPanel() {
		JPanel applyButtonPanel = new JPanel();
		applyButtonPanel.setBackground(Color.black);
		JButton applyButton = new JButton("Apply");
		applyButton.setBackground(Color.DARK_GRAY);
		applyButton.setForeground(Color.WHITE);
		applyButton.setBorder(null);
		applyButton.setFocusPainted(false);
		Dimension applyButtonDimension;
		switch(currResolution) {
			case "1280x720":
				applyButtonDimension = new Dimension(mainPanelSize[0]/6, mainPanelSize[1]/15);
				break;
			case "1920x1080":
				applyButtonDimension = new Dimension(mainPanelSize[0]/7, mainPanelSize[1]/20);
				break;
			case "2560x1440":
				applyButtonDimension = new Dimension(mainPanelSize[0]/8, mainPanelSize[1]/25);
				break;
			default:
				applyButtonDimension = new Dimension(mainPanelSize[0]/6, mainPanelSize[1]/15);
		}
		applyButton.setPreferredSize(applyButtonDimension);
		applyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				screen.setScreenResolution(resolutionSelection);
				System.out.println("Restarting with new resolution of " + screen.toString());
				((GameFrame) gameFrame).setNewDimensions(mainPanelSize);
			}
			
		});
		applyButtonPanel.add(applyButton);
		return applyButtonPanel;
	}

	private JPanel createSettingsOptionsPanel() {
		JPanel settingsOptionsPanel = new JPanel(new GridLayout(1,0,-40,0));
		settingsOptionsPanel.setBackground(Color.BLACK);
		JLabel resolutionLabel = new JLabel(" Resolution | ", SwingConstants.CENTER);
		resolutionLabel.setFont(new Font("Serif", Font.PLAIN, 48));
		settingsOptionsPanel.add(resolutionLabel);
				
		JPanel wrapper = new JPanel(new GridBagLayout());
		GridBagConstraints wrapperConstraints = new GridBagConstraints();
		wrapperConstraints.anchor = GridBagConstraints.LINE_START;
		
		resolutionComboBox = new JComboBox<String>(resolutions);
		Dimension preferredSize = resolutionComboBox.getPreferredSize();
		preferredSize.height = 45;
		preferredSize.width = 200;
		resolutionComboBox.setPreferredSize(preferredSize);
		resolutionComboBox.setFont(new Font("Serif", Font.BOLD, 18));
		resolutionComboBox.setBackground(Color.DARK_GRAY);
		resolutionComboBox.setForeground(Color.WHITE);
		resolutionComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					resolutionSelection = (String) e.getItem();
					System.out.println("Currently selecting " + resolutionSelection);
				}
			}
		});
		//removes JComboBox Border
		for (int i = 0; i < resolutionComboBox.getComponentCount(); i++) {
		    if (resolutionComboBox.getComponent(i) instanceof JComponent) {
		        ((JComponent) resolutionComboBox.getComponent(i)).setBorder(new EmptyBorder(0,0,0,0));
		    }
		}
		((JLabel)resolutionComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		wrapper.add(resolutionComboBox);
		wrapper.setBackground(Color.BLACK);
		settingsOptionsPanel.add(wrapper, wrapperConstraints);

		return settingsOptionsPanel;
	}

	private JPanel createBackButtonPanel() {
		FlowLayout settingsPanelFlowLayout;
		switch(currResolution) {
			case "1280x720":
				settingsPanelFlowLayout = new FlowLayout(FlowLayout.LEFT, 15, 25);
				break;
			case "1920x1080":
				settingsPanelFlowLayout = new FlowLayout(FlowLayout.LEFT, 20, 40);
				break;
			case "2560x1440":
				settingsPanelFlowLayout = new FlowLayout(FlowLayout.LEFT, 30, 65);
				break;
			default:
				settingsPanelFlowLayout = new FlowLayout(FlowLayout.LEFT, 10, 25);
		}
			
		JPanel settingsPanel = new JPanel(settingsPanelFlowLayout);
		settingsPanel.setBackground(Color.BLACK);
		settingsPanel.add(createBackButton());
		return settingsPanel;
	}

	private JButton createBackButton() {
		try {
			Image img = ImageIO.read(getClass().getResource("../resources/back-button.png"));
			Image scaledImg;
			Dimension buttonDim;
			
			switch(currResolution) {
	            case "1280x720":
	                buttonDim = new Dimension(mainPanelSize[0]/20, mainPanelSize[1]/13);
	                break;
	            case "1920x1080":
	                buttonDim = new Dimension(mainPanelSize[0]/25, mainPanelSize[1]/18);
	                break;
	            case "2560x1440":
	                buttonDim = new Dimension(mainPanelSize[0]/30, mainPanelSize[1]/22);
	                break;
	            default:
	                buttonDim = new Dimension(mainPanelSize[0]/20, mainPanelSize[1]/13);
			}
			
			scaledImg = img.getScaledInstance(buttonDim.width, buttonDim.height, java.awt.Image.SCALE_SMOOTH);
			
//			Image scaledImg = img.getScaledInstance(mainPanelSize[0]/20, mainPanelSize[1]/13, java.awt.Image.SCALE_SMOOTH);
			JButton settingsButton = new JButton(new ImageIcon(scaledImg));
			settingsButton.setBackground(Color.BLACK);
			settingsButton.setFocusPainted(false);
			settingsButton.setBorderPainted(false);
			
	        settingsButton.setPreferredSize(buttonDim);
	        settingsButton.setMinimumSize(buttonDim);
	        settingsButton.setMaximumSize(buttonDim);
	        
			settingsButton.addActionListener(e -> GameFrame.setView("menu")); 
			return settingsButton;
		} catch(Exception ex) {
			System.out.println(ex);
			
		}
		return null;
	}
	
	private void addKeyListeners() {
		addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
            	if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            		GameFrame.setView("menu");
            	}
            }
        });
	}
	
	public void updateSizeAndLayout() {
        mainPanelSize = screen.getScreenResolution();
        currResolution =  mainPanelSize[0] + "x" + mainPanelSize[1];
        resolutionSelection = "1280x720"; //default resolution selection
        System.out.println("in SettingsView, currResolution = " + currResolution);

        setupComponents();
        
        revalidate();
        repaint();
    }

}
