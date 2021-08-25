package gioco;

import javax.sound.sampled.AudioInputStream; 
import java.io.IOException;
import java.io.File;
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException; 

public class AudioPlayer {
	Clip audio;
	String path;
	AudioInputStream audioInputStream;
	
	public AudioPlayer(String path) throws UnsupportedAudioFileException, 
    IOException, LineUnavailableException {
		this.path = path;
		audioInputStream = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
		audio = AudioSystem.getClip();
		audio.open(audioInputStream);
	}
	
	public void play() {
		audio.start();
		audio.setMicrosecondPosition(0);
	}
	
	public void pause() {
		audio.stop();
	}
	
	public void loop() {
		audio.loop(Clip.LOOP_CONTINUOUSLY);
	}
}
