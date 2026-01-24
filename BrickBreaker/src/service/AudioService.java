package service;

public interface AudioService {
	public void playSoundEffect(String name);
	public void playMusic(String name);
	public void stopMusic();
	public void setVolume(double volume);
}
