package cr.ac.una.fantasydefender.model;

import javafx.geometry.Rectangle2D;

/**
 *
 * @author TakkaSama
 */
public class Animation {
    private double animationDuration; // duracion de la animacion por frame
    private Rectangle2D coordinates[]; // posiciones de los sprites del conjunto de charactersheet (x,y,heigh, width)
    
    public Animation(){
        this.animationDuration = 1;
    }
    
    public Animation(double duration, Rectangle2D coordinates[]){
        this.animationDuration = duration;
        this.coordinates = coordinates;
    }

    public double getAnimationDuration() {
        return animationDuration;
    }

    public void setAnimationDuration(double animationDuration) {
        this.animationDuration = animationDuration;
    }

    public Rectangle2D[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Rectangle2D[] coordinates) {
        this.coordinates = coordinates;
    }

   
    
    public Rectangle2D getCurrentFrame(double time){
        int totalFrames = coordinates.length;
        int frame = (int) (time%(totalFrames*animationDuration)/animationDuration);
        frame = Math.min(frame, totalFrames - 1); // se agrego en el caso de que se obtenga un 5.9 y sea redondeado a 6 , donde deberia ser 5, es decir apra evitar un sprite en blanco 
        return coordinates[frame];
    }
}
