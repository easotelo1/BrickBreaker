package service;

import javax.sound.sampled.Clip;

public interface AudioService {
	public void playSoundEffect(String name);
	public void playMusic(String name);
	public void stopMusic();
	public void setVolume(Clip clip, float volume);
}
