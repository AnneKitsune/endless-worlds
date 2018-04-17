package net.supercraft.endlessWorlds.utils;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio extends Thread{
    
    
    AudioInputStream audioInputStream = null;
    SourceDataLine line;
    Clip clip;
    FloatControl gainControl;
    String file;
    
    public Audio(String file){
    	this.file = file;
    }
    
    public void run(){
    	File fileToLoad = new File(file);
        
        try {
            audioInputStream = AudioSystem.getAudioInputStream(fileToLoad);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
         
         AudioFormat audioFormat = audioInputStream.getFormat();
         
         DataLine.Info info = new DataLine.Info(SourceDataLine.class,audioFormat);
          
         try {
             line = (SourceDataLine) AudioSystem.getLine(info);
             
                        
             } catch (LineUnavailableException e) {
               e.printStackTrace();
               return;
             }
          
        try {
                line.open(audioFormat);
        } catch (LineUnavailableException e) {
                    e.printStackTrace();
                    return;
        }
        
        FloatControl volume= (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
        //FloatControl volume1= (FloatControl) line.getControl(FloatControl.Type.VOLUME);
        
        
			try {
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(fileToLoad);
		        clip = AudioSystem.getClip();
				clip.open(audioInputStream);
				
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        	
        
        volume.setValue(-3.0f);
        //volume1.setValue(0.0f);
        //volume.set
        
        gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    	gainControl.setValue(0.0f);
    	clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        //line.start();
        
        try {
            byte bytes[] = new byte[1024];
            int bytesRead=0;
            while ((bytesRead = audioInputStream.read(bytes, 0, bytes.length)) != -1) {
                 line.write(bytes, 0, bytesRead);
               }
        } catch (IOException io) {
            io.printStackTrace();
            return;
        }
    }
    
    public void setVolume(float volume){
    	gainControl.setValue(volume);
    }
    
}