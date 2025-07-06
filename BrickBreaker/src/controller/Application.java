package controller;

import java.awt.Color;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Application {

	public static void main(String[] args) {
		try {
            // Set cross-platform Java L&F (also called "Metal")
	    	UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
	    	UIManager.put("Button.select", Color.DARK_GRAY.brighter());
	    } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
				new BrickBreaker();
            }
        });
	}

}
