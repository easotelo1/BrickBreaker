package model;

public class Screen {
	
	private static Screen screen = new Screen();
	
	private int[] resolution = new int[2];
	
	private int width;
	private int height;
	private PanelView currentPanel;

	//default resolution
	private Screen() {
		width = 1280;
		height = 720;
		resolution[0] = width;
		resolution[1] = height;
		currentPanel = PanelView.MAINMENU;
	}
	
	public static Screen getScreen() {
		return screen;
	}
	
	public int[] getScreenResolution() {
		return resolution;
	}
	
	public void setScreenResolution(String newResolution) {
		String[] splitResolution = newResolution.split("x");
		width = Integer.parseInt(splitResolution[0]);
		height = Integer.parseInt(splitResolution[1]);
		resolution[0] = Integer.parseInt(splitResolution[0]);
		resolution[1] = Integer.parseInt(splitResolution[1]);
	}
	
	public PanelView getCurrentPanel() {
		return this.currentPanel;
	}
	
	public void setCurrentPanel(PanelView panelView) {
		this.currentPanel = panelView;
	}
	
	public String toString() {
		return "Current resolution = " + resolution[0] + "x" + resolution[1];
	}
}
