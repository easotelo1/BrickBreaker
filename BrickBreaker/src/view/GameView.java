package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import model.Paddle;
import model.Screen;

@SuppressWarnings("serial")
public class GameView extends JPanel {
	
	private Screen screen = Screen.getScreen();
	private int[] mainPanelSize;
	private String currResolution;
	
	private Paddle paddle;
	
    // Set to keep track of currently pressed keys for smooth input
    private final Set<Integer> pressedKeys = new HashSet<>();
    
	public GameView() {
		mainPanelSize = screen.getScreenResolution();
		currResolution =  mainPanelSize[0] + "x" + mainPanelSize[1];
		setFocusable(true);
		
		//TODO: TEMPORARY. Remove after adding pause function
		GridBagConstraints gbcSettings = new GridBagConstraints();
        SettingsButtonPanel settingsButtonPanel = new SettingsButtonPanel();
        gbcSettings.anchor = GridBagConstraints.NORTHEAST;
        gbcSettings.gridwidth = GridBagConstraints.REMAINDER;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 1;
        add(settingsButtonPanel.getPanel(), gbcSettings);

		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(mainPanelSize[0], mainPanelSize[1]));
		updateInitialPaddlePosition();
	
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				updateInitialPaddlePosition();
			}
		});
		
		addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                pressedKeys.add(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                pressedKeys.remove(e.getKeyCode());
            }
        });
	
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.fillRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());
	}
	
	private void updateInitialPaddlePosition() {
		int paddleWidth;
		int paddleHeight;
		System.out.println("currResolution = " + currResolution);
		switch(currResolution) {
			case "1280x720":
				paddleWidth = 130;
				paddleHeight = 25;
				break;
			case "1920x1080":
				paddleWidth = 200;
				paddleHeight = 38;
				break;
			case "2560x1440":
				paddleWidth = 300;
				paddleHeight = 50;
				break;
			default:
				paddleWidth = 130;
				paddleHeight = 25;
		}

		int paddleX = (mainPanelSize[0] - paddleWidth) / 2;
		int paddleY = mainPanelSize[1] - paddleHeight - 100;
		paddle = new Paddle(paddleX, paddleY, paddleWidth, paddleHeight);
		
		System.out.println("paddleWidth = " + paddleWidth);
	}
	
	// This method is called by the BrickBreaker controller's Timer loop.
	public void updateGameLogic(double timeDeltaSeconds) {
        if (pressedKeys.contains(KeyEvent.VK_LEFT) || pressedKeys.contains(KeyEvent.VK_A)) {
            paddle.moveLeft();
        } else if (pressedKeys.contains(KeyEvent.VK_RIGHT) || pressedKeys.contains(KeyEvent.VK_D)) {
            paddle.moveRight();
        } else {
            paddle.stop();
        }

        // Tell the paddle to update its position using the time delta from the controller
        paddle.update(timeDeltaSeconds, mainPanelSize[0]); 
    }
	
    public void updateSizeAndLayout() {
        mainPanelSize = screen.getScreenResolution();
        currResolution =  mainPanelSize[0] + "x" + mainPanelSize[1];
        
        setPreferredSize(new Dimension(mainPanelSize[0], mainPanelSize[1]));
        
        updateInitialPaddlePosition();
        
        revalidate();
        repaint();

    }
}
