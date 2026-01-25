package service;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class AudioLoader {
	
	public static Clip loadClip(String path) {
		try {
			InputStream is = AudioLoader.class.getResourceAsStream(path);
			if(is == null) {
				throw new Exception("Sound file not found: " + path);
			}
			
			InputStream bufferedIn = new BufferedInputStream(is);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
            
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			
			return clip;
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
