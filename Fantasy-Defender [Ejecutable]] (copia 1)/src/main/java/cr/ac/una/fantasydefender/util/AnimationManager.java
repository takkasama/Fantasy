package cr.ac.una.fantasydefender.util;

import cr.ac.una.fantasydefender.model.Animation;
import java.util.HashMap;
import javafx.geometry.Rectangle2D;


/**
 * @author Takkasama
 */
public class AnimationManager {

    HashMap<String, Animation> animations;
   
    public AnimationManager() {
        this.animations = new HashMap<>();
    }
    
    
    
    public void addAnimation(String name , Animation animation){
        
        if(existAnimation(name)) return;
        
        this.animations.put(name, animation);
    }
    
    public void removeAnimation(String name){
    
        if(!existAnimation(name)) return;
        
        this.animations.remove(name);
        
    }
    
    public boolean existAnimation(String name){
    
        if (name == null || name.isBlank()) return false;
        
        return animations.containsKey(name);
       
    }
    
    public Animation  getAnimation(String name){
        
        if(!existAnimation(name)) return null;
        
        return animations.get(name);
    }
    
    
    public Rectangle2D getFrame(Double time, String name){
        
        if(!existAnimation(name) || time < 0) return null; 
        
        return animations.get(name).getCurrentFrame(time);
    }
}
