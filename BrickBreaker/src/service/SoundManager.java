package service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundManager implements AudioService {
	
	private final String pathPrefix = "../resources/sounds/";
	private final String initClip = "menuTheme";
	
	private HashMap<String, Clip> soundMap;
	
	//that way sounds can overlap if there are multiple triggers in succession  
	private ArrayList<Clip> destroyPool;
	private ArrayList<Clip> selectPool;
	
	private int destroyPoolIndex = 0;
	private int selectPoolIndex = 0;
	
    private Clip currentMusic;
	
	public SoundManager() {
		this.soundMap = new HashMap<>();
		//sfx
		loadSound("bounce", pathPrefix + "bounce.wav");
		loadSound("death", pathPrefix + "death.wav");
		loadSound("win", pathPrefix + "game-clear.wav");
		loadDestroySounds(pathPrefix + "brick-destroy.wav");
		loadSelectSounds(pathPrefix + "select.wav");
		
		//music
		loadSound("menuTheme", pathPrefix + "menu-theme.wav");
		loadSound("gameTheme", pathPrefix + "game-theme.wav");
		loadSound("gameOverTheme", pathPrefix + "game-over.wav");
		
		playMusic(initClip);
	}
	
	private void loadSound(String clipName, String path) {
		Clip clip = AudioLoader.loadClip(path);
		if(clip != null) {
			soundMap.put(clipName, clip);
		}
	}
	
	private void loadDestroySounds(String path) {
		destroyPool = new ArrayList<Clip>();
		for (int i = 0; i < 10; i++) {
			Clip clip = AudioLoader.loadClip(path);
			destroyPool.add(clip);
		}
	}
	
	private void loadSelectSounds(String path) {
		selectPool = new ArrayList<Clip>();
		for(int i = 0; i < 10; i++) {
			Clip clip = AudioLoader.loadClip(path);
			selectPool.add(clip);
		}
	}

	@Override
	public void playSoundEffect(String clipName) {
		Clip clip = soundMap.get(clipName);
		if(clip != null) {
			if (clip.isRunning()) {
				clip.stop();
			}
			clip.setFramePosition(0);
			
			switch(clipName) {
				case "bounce":
					setVolume(clip, 0.85f);
					break;
				case "win":
					setVolume(clip, 0.75f);
					break;
				default:
					setVolume(clip, 1.0f);
					break;
					
			}
			clip.start();
		}

	}
	
	public void playSelect() {
		Clip clip = selectPool.get(selectPoolIndex);
		if (clip != null) {
			if (clip.isRunning()) {
				clip.stop();
			}
			clip.setFramePosition(0);
			setVolume(clip, 0.65f);
			clip.start();
			selectPoolIndex = (selectPoolIndex + 1) % selectPool.size();
		}
		
	}
	
	public void playBrickDestroy() {
		Clip clip = destroyPool.get(destroyPoolIndex);
		if (clip != null) {
			if (clip.isRunning()) {
				clip.stop();
			}
			clip.setFramePosition(0);
			setVolume(clip, 0.75f);
			clip.start();
			destroyPoolIndex = (destroyPoolIndex + 1) % destroyPool.size();
		}
		
	}
	
	public void playDeathSound(Runnable onComplete) {
	    Clip clip = soundMap.get("death");
	    if (clip != null) {
	        clip.setFramePosition(0);
	        setVolume(clip, 0.85f);
	        clip.start();

	        long clipDurationMs = clip.getMicrosecondLength() / 1000;
	        
	        new Thread(() -> {
	            try {
	                Thread.sleep(clipDurationMs );
	                onComplete.run();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }).start();
	    }
	}

	@Override
	public void playMusic(String clipName) {
		//no need to restart song if play again
		if(soundMap.get(clipName).equals(currentMusic)) {
			return; 
		}
		
		if (currentMusic != null) {
			currentMusic.stop();
		}
		
        currentMusic = soundMap.get(clipName);
        
        if (currentMusic != null) {
            currentMusic.setFramePosition(0);
            switch(clipName) {
            	case "menuTheme":
				case "gameTheme":
					setVolume(currentMusic, 0.85f);
					break;
				case "gameOverTheme":
					setVolume(currentMusic, 0.75f);
					break;
				default:
					setVolume(currentMusic, 1.0f);
					break;	
            }
            setVolume(currentMusic, 0.75f);
            currentMusic.loop(Clip.LOOP_CONTINUOUSLY);
        }
	}

	@Override
	public void stopMusic() {
        if (currentMusic != null) {
        	currentMusic.stop();
        	currentMusic = null;
        }
        
        for(Clip clip : soundMap.values()) {
        	clip.stop();
        }

	}

	//0.0f means no sound, 1.0f means full audio
	//https://stackoverflow.com/questions/40514910/set-volume-of-java-clip
	@Override
	public void setVolume(Clip clip, float volume) {
		FloatControl fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		float range = fc.getMaximum() - fc.getMinimum();
		float gain = (range * volume) + fc.getMinimum();
		
		fc.setValue(gain);
	}
	
	public boolean isSFXPlaying(String name) {
		Clip clip = soundMap.get(name);
		return clip.isRunning();
	}
	

}
