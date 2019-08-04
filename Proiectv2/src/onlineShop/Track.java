package onlineShop;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Track 
{
     private final String name;
     //private final Clip audio;
     private final long startMicroseconds;
     private final String path;

    public Track(String name, long startMicroseconds, String path) 
    {
        this.name = name;
        this.startMicroseconds = startMicroseconds;
        this.path = path;
        //audio = AudioSystem.getClip();
        //AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));
        //audio.open(ais);
        //audio.setMicrosecondPosition(startMicroseconds);   
    }

    public long getStartMicroseconds() 
    {
        return startMicroseconds;
    }

    public String getPath() 
    {
        return path;
    }
    
    
     
    /*public void play()
    {
        audio.start();
        audio.setMicrosecondPosition(startMicroseconds);
    }
     
    public void stop()
    {
        if(audio.isActive())
            audio.stop();
    }*/

    public String getName() 
    {
        return name;
    }
    
}
