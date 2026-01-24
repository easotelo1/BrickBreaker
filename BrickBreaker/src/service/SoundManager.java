package service;

import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.Clip;

public class SoundManager implements AudioService {
	
	private final Map<String, Clip> soundMap = new HashMap<>();
    private Clip currentMusic;
	
	public SoundManager() {
		loadSound("bounce", "../resources/sounds/bounce.wav");
	}
	
	private void loadSound(String name, String path) {
		Clip clip = AudioLoader.loadClip(path);
		if(clip != null) {
			soundMap.put(name, clip);
		}
	}

	@Override
	public void playSoundEffect(String name) {
		Clip clip = soundMap.get(name);
		if(clip != null) {
			clip.setFramePosition(0);
			clip.start();
		}

	}

	@Override
	public void playMusic(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stopMusic() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setVolume(double volume) {
		// TODO Auto-generated method stub

	}
	

}
