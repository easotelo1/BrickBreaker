package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.BrickBreaker;
import model.Screen;

@SuppressWarnings("serial")
public class PauseView extends JPanel{
	private Screen screen = Screen.getScreen();
	private int[] mainPanelSize;
	private String currResolution;
	
	public PauseView() {
		mainPanelSize = screen.getScreenResolution();
		currResolution = mainPanelSize[0] + "x" + mainPanelSize[1];
		setFocusable(true);
		
		// Semi-transparent dark overlay
        setBackground(new Color(0, 0, 0, 180));
        setLayout(new GridBagLayout());
        
        addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
        			BrickBreaker brickBreaker = BrickBreaker.getInstance();
        			brickBreaker.resumeGame();
        		}
        	}
        });
        
        setupComponents();
	}
	
	private void setupComponents() {
		this.removeAll();

		int panelWidth = (int)(mainPanelSize[0] * 0.32);
		int panelHeight = (int)(mainPanelSize[1] * 0.42);
		
		JPanel pauseBox = new JPanel();
		pauseBox.setBackground(new Color(30, 30, 50));
		pauseBox.setBackground(Color.BLACK);
        pauseBox.setBorder(BorderFactory.createLineBorder(null, 4));
        pauseBox.setPreferredSize(new Dimension(panelWidth, panelHeight));
        pauseBox.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 40, 15, 40);
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        JLabel title = new JLabel("PAUSED");
        title.setFont(new Font("Arial", Font.BOLD, 60));
        title.setForeground(new Color(115, 87, 92));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        pauseBox.add(title, gbc);

        JButton resumeButton = createStyledButton("RESUME");
        resumeButton.addActionListener(e -> BrickBreaker.getInstance().resumeGame());
        pauseBox.add(resumeButton, gbc);

        gbc.insets = new Insets(10, 40, 25, 40);
        JButton exitButton = createStyledButton("EXIT TO MENU");
        exitButton.addActionListener(e -> BrickBreaker.getInstance().exitToMenu());
        pauseBox.add(exitButton, gbc);

        add(pauseBox);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        switch(currResolution) {
			case "1280x720":
				button.setFont(new Font("Arial", Font.BOLD, 25));
				break;
			case "1920x1080":
				button.setFont(new Font("Arial", Font.BOLD, 30));
				break;
			case "2560x1440":
				button.setFont(new Font("Arial", Font.BOLD, 35));
				break;
			default:
				button.setFont(new Font("Arial", Font.BOLD, 25));
		}
        button.setForeground(Color.DARK_GRAY);
        button.setBackground(new Color(115, 87, 92));
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(mainPanelSize[0]/6, mainPanelSize[1]/15));
        button.setBorder(BorderFactory.createEmptyBorder(6, 20, 6, 20)); // top, left, bottom, right
        button.setOpaque(true);

        int buttonWidth = (int) (mainPanelSize[0] * 0.22);
        int buttonHeight = (int) (mainPanelSize[1] * 0.06);
        button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        
        //Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(115, 67, 62));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(115, 87, 92));
            }
        });

        return button;
    }
    
	public void updateSizeAndLayout() {
        mainPanelSize = screen.getScreenResolution();
        currResolution =  mainPanelSize[0] + "x" + mainPanelSize[1];
        System.out.println("in PauseView, currResolution = " + currResolution);

        setupComponents();
        
        revalidate();
        repaint();
    }
}
