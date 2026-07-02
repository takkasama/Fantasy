package cr.ac.una.fantasydefender.service;

import cr.ac.una.fantasydefender.util.AnimationManager;
import cr.ac.una.fantasydefender.model.Animation;
import cr.ac.una.fantasydefender.util.AudioManager;
import cr.ac.una.fantasydefender.App;
import java.util.HashMap;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

/**
 *LA PRESENTE CLASE MANEJARA EL CARGADO DE LOS RESCURSOS DE ANIMACION DE EL CASTILLO,LA BALLESTA
 * BARRAS DE VIDA, MANA Y PROGRESO 
 * 
 *  
 * @author Takkasama
 */

public class CastleAnimationManager {
    
    private static CastleAnimationManager instance;
    
    private final static double TIME_BY_FRAME = 0.20;
    
    private final AnimationManager  animationManager;
    private final AudioManager audioManager;
  
    private final HashMap<String ,Image> spriteSheetImages;
    
    private final static String PATH_SPRITES = "/cr/ac/una/fantasydefender/resource/sprites/";
    
    private CastleAnimationManager(){
        this.animationManager = new AnimationManager();
        this.audioManager = new AudioManager();
        this.spriteSheetImages = new HashMap<>();
        loadResources();
    }
    
    public static CastleAnimationManager getInstance(){
        if(instance == null){
            synchronized (CastleAnimationManager.class) {
                if(instance == null) instance = new CastleAnimationManager();
            }
        }
        return instance;
    }
     
    private void loadResources(){
        loadSpriteSheets();
        loadCrossbow1Animation();
        loadCastleAnimation();
        loadLifeBarAnimation();
        loadElixerBarAnimation();
      }

    private void loadSpriteSheets() {
       
        //cargado de los image para runtime 
        this.spriteSheetImages.put("ctlCastle", new Image(App.class.getResource( PATH_SPRITES+ "castle.png").toExternalForm()));
        this.spriteSheetImages.put("ctlCrossbow1",new Image( App.class.getResource(PATH_SPRITES + "crossbow.png").toExternalForm()));
        this.spriteSheetImages.put("ctlLifeBar", new Image(App.class.getResource(PATH_SPRITES + "lifeBar.png").toExternalForm()));
        this.spriteSheetImages.put("ctlManaBar", new Image(App.class.getResource(PATH_SPRITES + "manaBar.png").toExternalForm()));
    }

//    private void loadCastleAnimation() {
//        //No tiene animaciones 
//    }

    private void loadCrossbow1Animation() {
        Rectangle2D ctlCrossbow[] = {
            new Rectangle2D(17,  35,  170, 227),
            new Rectangle2D(17,  315, 170, 227),
            new Rectangle2D(267, 315, 170, 227),
            new Rectangle2D(17,  595, 187, 227),
            new Rectangle2D(267, 595, 170, 227),
            new Rectangle2D(17,  875, 170, 227),
            new Rectangle2D(267, 875, 170, 227),
            new Rectangle2D(17,  1155,170, 227),
            new Rectangle2D(267, 1155,170, 227),
            new Rectangle2D(17,  1435,170, 227),
        };
        
        animationManager.addAnimation("ctlCrossbow1Shoot", new Animation(TIME_BY_FRAME, ctlCrossbow));
     
        Rectangle2D ctlCrossbowStill[] = {
            new Rectangle2D(17,  35,  170, 227),
            new Rectangle2D(17,  315, 170, 227)
        };
        
        animationManager.addAnimation("ctlCrossbow1Still", new Animation(TIME_BY_FRAME, ctlCrossbowStill));
        
                Rectangle2D ctlArrow[] = {
            new Rectangle2D(540, 137,  137, 32),
            new Rectangle2D(540, 410,  137, 34),
            new Rectangle2D(540, 695,  137, 27),
            new Rectangle2D(540, 967,  137, 32),
            new Rectangle2D(535, 1258, 142, 22),
        };
        animationManager.addAnimation("ctlArrow1", new Animation(TIME_BY_FRAME, ctlArrow));    
    
        Rectangle2D[] ctlImpactArrow = {
            new Rectangle2D(882, 122, 27, 27),
            new Rectangle2D(877, 387, 22, 45),
            new Rectangle2D(865, 672, 42, 51),
            new Rectangle2D(875, 960, 30, 48),
        };
        animationManager.addAnimation("ctlImpact1", new Animation(0.20, ctlImpactArrow));
    }
    
    
    private void loadCastleAnimation(){
        
        Rectangle2D[] ctlCastle1 = {
            new Rectangle2D(0,    0, 203, 720)
        };        
        animationManager.addAnimation("castle1", 
                new Animation(TIME_BY_FRAME, ctlCastle1));
        
         Rectangle2D[] ctlCastle2 = {
             new Rectangle2D(405,  0, 203, 720)
         };
      animationManager.addAnimation("castle2", 
                new Animation(TIME_BY_FRAME, ctlCastle2));
      
         Rectangle2D[] ctlCastle3 = {
            new Rectangle2D(810,  0, 203, 720)
        };       
      animationManager.addAnimation("castle3", 
                new Animation(TIME_BY_FRAME, ctlCastle3));
      
      Rectangle2D[] ctlCastle4 = {
            new Rectangle2D(1215, 0, 203, 720)
        };    
      animationManager.addAnimation("castle4", 
                new Animation(TIME_BY_FRAME, ctlCastle4));
      
      
         Rectangle2D[] ctlCastle5 = {
            new Rectangle2D(1620, 0, 202, 720)
        };       
      animationManager.addAnimation("castle5", 
                new Animation(TIME_BY_FRAME, ctlCastle5));         
      
    }
    
    private void loadLifeBarAnimation(){
        // 55 frames totales, leídos columna por columna (top→bottom, luego siguiente columna)
        // col1(x=5): 19 frames | col2(x=545): 20 frames | col3(x=1085): 16 frames
        // lifeBar10 = vida completa, lifeBar0 = vida vacía

        Rectangle2D[] lifeBar10 = {
            new Rectangle2D(   5,   12, 332, 74),
            new Rectangle2D(   5,  266, 332, 72),
            new Rectangle2D(   5,  393, 332, 72),
            new Rectangle2D(   5,  522, 332, 72),
            new Rectangle2D(   5,  649, 332, 72)
        };
        animationManager.addAnimation("lifeBar10",
                new Animation(TIME_BY_FRAME, lifeBar10));

        Rectangle2D[] lifeBar9 = {
            new Rectangle2D(   5,  776, 332, 72),
            new Rectangle2D(   5,  901, 332, 72),
            new Rectangle2D(   5, 1028, 332, 72),
            new Rectangle2D(   5, 1157, 332, 72),
            new Rectangle2D(   5, 1284, 332, 72)
        };
        animationManager.addAnimation("lifeBar9",
                new Animation(TIME_BY_FRAME, lifeBar9));

        Rectangle2D[] lifeBar8 = {
            new Rectangle2D(   5, 1411, 332, 72),
            new Rectangle2D(   5, 1536, 332, 72),
            new Rectangle2D(   5, 1663, 332, 72),
            new Rectangle2D(   5, 1790, 332, 74),
            new Rectangle2D(   5, 1917, 332, 74)
        };
        animationManager.addAnimation("lifeBar8",
                new Animation(TIME_BY_FRAME, lifeBar8));

        Rectangle2D[] lifeBar7 = {
            new Rectangle2D(   5, 2046, 332, 72),
            new Rectangle2D(   5, 2171, 332, 72),
            new Rectangle2D(   5, 2298, 332, 72),
            new Rectangle2D(   5, 2427, 332, 72),
            new Rectangle2D( 545,   12, 332, 74)
        };
        animationManager.addAnimation("lifeBar7",
                new Animation(TIME_BY_FRAME, lifeBar7));

        Rectangle2D[] lifeBar6 = {
            new Rectangle2D( 545,  141, 332, 72),
            new Rectangle2D( 545,  266, 332, 72),
            new Rectangle2D( 545,  393, 332, 72),
            new Rectangle2D( 545,  522, 332, 72),
            new Rectangle2D( 545,  649, 332, 72)
        };
        animationManager.addAnimation("lifeBar6",
                new Animation(TIME_BY_FRAME, lifeBar6));

        Rectangle2D[] lifeBar5 = {
            new Rectangle2D( 545,  776, 332, 72),
            new Rectangle2D( 545,  901, 332, 72),
            new Rectangle2D( 545, 1028, 332, 72),
            new Rectangle2D( 545, 1157, 332, 72),
            new Rectangle2D( 545, 1284, 332, 72)
        };
        animationManager.addAnimation("lifeBar5",
                new Animation(TIME_BY_FRAME, lifeBar5));

        Rectangle2D[] lifeBar4 = {
            new Rectangle2D( 545, 1411, 332, 72),
            new Rectangle2D( 545, 1536, 332, 72),
            new Rectangle2D( 545, 1663, 332, 72),
            new Rectangle2D( 545, 1790, 332, 74),
            new Rectangle2D( 545, 1917, 332, 74)
        };
        animationManager.addAnimation("lifeBar4",
                new Animation(TIME_BY_FRAME, lifeBar4));

        Rectangle2D[] lifeBar3 = {
            new Rectangle2D( 545, 2046, 332, 72),
            new Rectangle2D( 545, 2171, 332, 72),
            new Rectangle2D( 545, 2298, 332, 72),
            new Rectangle2D( 545, 2427, 332, 72),
            new Rectangle2D(1085,   12, 332, 74)
        };
        animationManager.addAnimation("lifeBar3",
                new Animation(TIME_BY_FRAME, lifeBar3));

        Rectangle2D[] lifeBar2 = {
            new Rectangle2D(1085,  141, 332, 72),
            new Rectangle2D(1085,  266, 332, 72),
            new Rectangle2D(1085,  393, 332, 72),
            new Rectangle2D(1085,  522, 332, 72),
            new Rectangle2D(1085,  649, 332, 72)
        };
        animationManager.addAnimation("lifeBar2",
                new Animation(TIME_BY_FRAME, lifeBar2));

        Rectangle2D[] lifeBar1 = {
            new Rectangle2D(1085,  776, 332, 72),
            new Rectangle2D(1085,  901, 332, 72),
            new Rectangle2D(1085, 1028, 332, 72),
            new Rectangle2D(1085, 1157, 332, 72),
            new Rectangle2D(1085, 1284, 332, 72)
        };
        animationManager.addAnimation("lifeBar1",
                new Animation(TIME_BY_FRAME, lifeBar1));

        // lifeBar0 — vida vacía
        Rectangle2D[] lifeBar0 = {
            new Rectangle2D(1085, 1411, 332, 72),
            new Rectangle2D(1085, 1536, 332, 72),
            new Rectangle2D(1085, 1663, 332, 72),
            new Rectangle2D(1085, 1790, 332, 74),
            new Rectangle2D(1085, 1917, 332, 74)
        };
        animationManager.addAnimation("lifeBar0",
                new Animation(TIME_BY_FRAME, lifeBar0));
    }

    private void loadElixerBarAnimation(){
        // ElixerBar10 (antes ElixerBar0) — mana completo
        Rectangle2D[] ElixerBar10 = {
            new Rectangle2D(5,   12,  332, 76),
            new Rectangle2D(5,  139,  332, 76)
        };
        animationManager.addAnimation("ElixerBar10",
                new Animation(TIME_BY_FRAME, ElixerBar10));

        Rectangle2D[] ElixerBar9 = {
            new Rectangle2D(5,  266,  332, 76), new Rectangle2D(5,  393,  332, 76),
            new Rectangle2D(5,  520,  332, 76)
        };
        animationManager.addAnimation("ElixerBar9",
                new Animation(TIME_BY_FRAME, ElixerBar9));

        Rectangle2D[] ElixerBar8 = {
            new Rectangle2D(5,  647,  332, 76), new Rectangle2D(5,  774,  332, 76),
            new Rectangle2D(5,  901,  332, 76)
        };
        animationManager.addAnimation("ElixerBar8",
                new Animation(TIME_BY_FRAME, ElixerBar8));

        Rectangle2D[] ElixerBar7 = {
            new Rectangle2D(365,  12,  332, 76), new Rectangle2D(365, 139,  332, 76),
            new Rectangle2D(365, 266,  332, 76)
        };
        animationManager.addAnimation("ElixerBar7",
                new Animation(TIME_BY_FRAME, ElixerBar7));

        Rectangle2D[] ElixerBar6 = {
            new Rectangle2D(365, 393,  332, 76), new Rectangle2D(365, 520,  332, 76),
            new Rectangle2D(365, 647,  332, 76)
        };
        animationManager.addAnimation("ElixerBar6",
                new Animation(TIME_BY_FRAME, ElixerBar6));

        Rectangle2D[] ElixerBar5 = {
            new Rectangle2D(365, 774,  332, 76), new Rectangle2D(365, 901,  332, 76),
            new Rectangle2D(725,  12,  332, 76)
        };
        animationManager.addAnimation("ElixerBar5",
                new Animation(TIME_BY_FRAME, ElixerBar5));

        Rectangle2D[] ElixerBar4 = {
            new Rectangle2D(725, 139,  332, 76), new Rectangle2D(725, 266,  332, 76),
            new Rectangle2D(725, 393,  332, 76)
        };
        animationManager.addAnimation("ElixerBar4",
                new Animation(TIME_BY_FRAME, ElixerBar4));

        Rectangle2D[] ElixerBar3 = {
            new Rectangle2D(725, 520,  332, 76), new Rectangle2D(725, 647,  332, 76),
            new Rectangle2D(725, 774,  332, 76)
        };
        animationManager.addAnimation("ElixerBar3",
                new Animation(TIME_BY_FRAME, ElixerBar3));

        Rectangle2D[] ElixerBar2 = {
            new Rectangle2D(725,  901, 332, 76), new Rectangle2D(1085,  12, 332, 76),
            new Rectangle2D(1085, 139, 332, 76)
        };
        animationManager.addAnimation("ElixerBar2",
                new Animation(TIME_BY_FRAME, ElixerBar2));

        Rectangle2D[] ElixerBar1 = {
            new Rectangle2D(1085, 266, 332, 76), new Rectangle2D(1085, 393, 332, 76),
            new Rectangle2D(1085, 520, 332, 76)
        };
        animationManager.addAnimation("ElixerBar1",
                new Animation(TIME_BY_FRAME, ElixerBar1));

        // ElixerBar0 (antes ElixerBar10) — mana vacío
        Rectangle2D[] ElixerBar0 = {
            new Rectangle2D(1085, 647, 332, 76),
            new Rectangle2D(1085, 774, 332, 76)
        };
        animationManager.addAnimation("ElixerBar0",
                new Animation(TIME_BY_FRAME, ElixerBar0));
    }
    
    
    /**
     *  
    * @param key Name of spriteSheet values: ( ctlCastle , ctlCrosssbow1 , ctlArrow1 , ctlImpact1 , ctlCrossbow2 , ctlArrow2 , ctlimpact2 )
    * @return Complete image type of spriteSheet
     */
    public Image getimageOfSpriteSheet(String key){ return spriteSheetImages.get(key); }

    /**
     *  
     * @return animation manager of CastelAnimationManager
     */
    public AnimationManager getAnimationManager() { return this.animationManager; }
    

    
        
}