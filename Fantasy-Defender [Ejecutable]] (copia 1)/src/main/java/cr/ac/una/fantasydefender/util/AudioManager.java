package cr.ac.una.fantasydefender.util;
import cr.ac.una.fantasydefender.App;

import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;



public class AudioManager {

    private Map<String, Clip> sounds = new HashMap<>();
    private final static String PATH_AUDIO_FILE = "/cr/ac/una/fantasydefender/resource/audio/";

    public AudioManager(){ 
        this.sounds = new HashMap<>();
    }
    
    public void loadSound( String key, String fileName) {
        try{
            AudioInputStream audio  = AudioSystem.getAudioInputStream(App.class.getResource( PATH_AUDIO_FILE + fileName ));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);        
            
            sounds.put(key, clip);
            
        }catch(IOException e){
            e.printStackTrace();
        }
        catch(UnsupportedAudioFileException e){
            e.printStackTrace();
        }
        catch(LineUnavailableException e){
            e. printStackTrace();
        }
        
    }
    
    public void reproduce(String key){
        Clip audio = sounds.get(key);
        
        if(audio == null){
            System.out.println("[AudioManager]  Invalid Key");
            return;
        }
        
        if(audio.isRunning())
            audio.stop();
        
        audio.setFramePosition(0);
        audio.start();
        
    }
    
    public void reproduceSecuence(List<String> keys) {
        for (String key : keys) {
            Clip audio = sounds.get(key);

            if (audio == null) {
                System.out.println("[AudioManager] Invalid Key : " + key);
                return;
            }

            if (audio.isRunning()) audio.stop();
            audio.setFramePosition(0);
            audio.start();
            audio.drain();

            long durationMs = audio.getMicrosecondLength() / 1000;
            
            try {
                Thread.sleep(durationMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
    
    
}
