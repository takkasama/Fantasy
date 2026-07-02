package cr.ac.una.fantasydefender.service;

import cr.ac.una.fantasydefender.App;
import cr.ac.una.fantasydefender.model.Animation;
import cr.ac.una.fantasydefender.util.AnimationManager;
import cr.ac.una.fantasydefender.util.AudioManager;
import java.util.HashMap;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

/**
 *
 * @author TakkaSama
 */
public class EnemyAnimationManager {
    
    private static EnemyAnimationManager instance;
    
    private final static double TIME_BY_FRAME = 0.17;
    
    private final AnimationManager animationManager;

    private final AudioManager audioManager;
    
    private final HashMap<String, Image> spriteSheetImages; 
    
    private final static String PATH_SPRITES = "/cr/ac/una/fantasydefender/resource/sprites/";
    
    
    private EnemyAnimationManager(){
        this.spriteSheetImages = new HashMap<>();
        this.animationManager = new AnimationManager();
        this.audioManager = new AudioManager();
        loadResources();
    }
    
    public static EnemyAnimationManager getInstance(){
        if(instance == null)
            synchronized (EnemyAnimationManager.class) {
                if(instance == null) instance = new EnemyAnimationManager();
            }
        return instance;
    }
    
    
    /*
     NOTA DE GUIA PARA LA ELABORACION DE LA CLASE: 
        
        PARA LE GUARDADO DE CADA SPRITESHEET O ANIMACION SE TENDRA EL ESTANDAR DE :
            
                [ABREVIATURA DEL TIPO DE ENEMIGO ] + [RUN - ATTACK - DEAD - ICE - METEOR ]
                
                EN CASO DE QUE SE TENGA OTRA ACCION SE MANEJARA DE LA MISMA FORMA 
                
        EJEMPLOS: 
        
            skRun - skAttack - skDead - skDeadIce - skMeteor    
    */

    private void loadResources(){
        loadSpriteSheet();
        loadAnimations();
    }
    
    private void loadSpriteSheet(){
        
        this.spriteSheetImages.put("skAttack",  new Image(App.class.getResource(PATH_SPRITES + "skeletonAttack.png").toExternalForm()));
        this.spriteSheetImages.put("skRun",     new Image(App.class.getResource(PATH_SPRITES + "skeletonRun.png").toExternalForm()));
        
        this.spriteSheetImages.put("skShAttack",new Image(App.class.getResource(PATH_SPRITES + "skeletonShieldAttack.png").toExternalForm()));
        this.spriteSheetImages.put("skShRun",   new Image(App.class.getResource(PATH_SPRITES + "skeletonShieldRun.png").toExternalForm()));        
        
        this.spriteSheetImages.put("slAttack", new Image( App.class.getResource(PATH_SPRITES + "slimeAttack.png").toExternalForm()));
        this.spriteSheetImages.put("slRun", new Image(App.class.getResource(PATH_SPRITES + "slimeRun.png").toExternalForm()));
        this.spriteSheetImages.put("slDead", new Image(App.class.getResource(PATH_SPRITES + "slimeDead.png").toExternalForm()));
        
        this.spriteSheetImages.put("mgAttack", new Image(App.class.getResource(PATH_SPRITES + "skMageAttack.png").toExternalForm()));
        this.spriteSheetImages.put("mgRun", new Image(App.class.getResource(PATH_SPRITES + "skMageRun.png").toExternalForm()));
        this.spriteSheetImages.put("mgDead", new Image(App.class.getResource(PATH_SPRITES + "skMageDead.png").toExternalForm()));
        
        this.spriteSheetImages.put("owAttack", new Image(App.class.getResource(PATH_SPRITES + "orcWolfAttack.png").toExternalForm()));
        this.spriteSheetImages.put("owRun", new Image(App.class.getResource(PATH_SPRITES + "orcWolfRun.png").toExternalForm()));
        this.spriteSheetImages.put("owDead", new Image(App.class.getResource(PATH_SPRITES + "orcWolfDead.png").toExternalForm()));
    }
        
    private void loadAnimations(){
        loadAttackAnimation();
        loadDeadAnimation();
        loadRunAnimation();
    }

    private void loadAttackAnimation() {
        Rectangle2D[] skAttack = {
            new Rectangle2D(75,   54, 126, 141),
            new Rectangle2D(378,  51, 159, 156),
            new Rectangle2D(666,  54, 156, 153),
            new Rectangle2D(879,   6, 198, 189),
            new Rectangle2D(1164, 12, 198, 183),
            new Rectangle2D(1461, 51, 153, 144),
            new Rectangle2D(1749, 63, 156, 141),
            new Rectangle2D(2070, 54, 129, 141),
            new Rectangle2D(2355, 51, 132, 144),
            new Rectangle2D(2640, 48, 132, 147),
            new Rectangle2D(2925, 48, 132, 147),
            new Rectangle2D(3210, 48, 129, 147)
        };
        animationManager.addAnimation("skAttack",
                new Animation(TIME_BY_FRAME,  skAttack));


        Rectangle2D[] skShAttack = {
            new Rectangle2D(72,   51, 150, 144),
            new Rectangle2D(357,  51, 150, 144),
            new Rectangle2D(642,  51, 150, 144),
            new Rectangle2D(927,  51, 150, 144),
            new Rectangle2D(1212, 51, 150, 144),
            new Rectangle2D(1497, 51, 150, 144),
            new Rectangle2D(1782, 51, 150, 144),
            new Rectangle2D(2067, 51, 150, 144),
            new Rectangle2D(2352, 51, 150, 144),
            new Rectangle2D(2637, 51, 150, 144),
            new Rectangle2D(2922, 51, 150, 144),
            new Rectangle2D(3207, 51, 150, 144),
        };
         animationManager.addAnimation("skShAttack",
                new Animation(TIME_BY_FRAME, skShAttack ));
    
        Rectangle2D[] slAttack = {
            new Rectangle2D(73,  105, 90,  53),
            new Rectangle2D(328, 105, 100, 53),
            new Rectangle2D(73,  300, 90,  53),
            new Rectangle2D(260, 300, 135, 53),
            new Rectangle2D(73,  495, 90,  53),
            new Rectangle2D(240, 495, 148, 53),
            new Rectangle2D(83,  690, 97,  53),
        };
        animationManager.addAnimation("slAttack",
                new Animation(TIME_BY_FRAME, slAttack));


         Rectangle2D[] mgAttack = {
            new Rectangle2D(243, 18,    3, 216),
            new Rectangle2D(249, 18,  147, 216),
            new Rectangle2D(699, 18,  174, 216),
            new Rectangle2D(1203,18,  147, 216),
            new Rectangle2D(249, 309, 150, 216),
            new Rectangle2D(699, 309, 174, 216),
            new Rectangle2D(1203,309, 147, 216),
            new Rectangle2D(231, 594,   9, 222),
            new Rectangle2D(249, 594, 150, 222),
            new Rectangle2D(696, 594, 180, 222),
            new Rectangle2D(225, 882,  21, 213),
            new Rectangle2D(249, 882, 159, 213),
            new Rectangle2D(498, 882, 378, 213),
            new Rectangle2D(225, 1170,186, 213),
            new Rectangle2D(726, 1170,159, 213),
            new Rectangle2D(222, 1461,189, 213),
            new Rectangle2D(726, 1461,162, 213),
            new Rectangle2D(222, 1752,186, 216),
            new Rectangle2D(726, 1752,162, 216),
            new Rectangle2D(222, 2049,177, 213),
            new Rectangle2D(726, 2049,159, 213),
            new Rectangle2D(222, 2343,174, 213),
            new Rectangle2D(726, 2343,150, 213),
            new Rectangle2D(222, 2637,174, 213),
            new Rectangle2D(726, 2637,147, 213),
        };
        animationManager.addAnimation("mgAttack", 
                new Animation(0.12, mgAttack));
        
        Rectangle2D[] owAttack = {
            new Rectangle2D(172,  132, 260, 260),
            new Rectangle2D(776,  128, 272, 264),
            new Rectangle2D(1376, 120, 280, 276),
            new Rectangle2D(172,  608, 260, 264),
            new Rectangle2D(820,  508, 340, 364),
            new Rectangle2D(172,  1084, 260, 268),
            new Rectangle2D(848,  988, 312, 364),
            new Rectangle2D(172,  1568, 260, 264),
            new Rectangle2D(672,  1484, 440, 352),
            new Rectangle2D(140,  2048, 276, 264),
            new Rectangle2D(660,  1988, 368, 328)
        };
        animationManager.addAnimation("owAttack",
                new Animation(TIME_BY_FRAME, owAttack));

    }

    private void loadDeadAnimation() {
        Rectangle2D[] skDead = {
            new Rectangle2D(93,   54,  84, 141),
            new Rectangle2D(390,  54,  87, 141),
            new Rectangle2D(678,  66,  93, 129),
            new Rectangle2D(912,  63, 117, 132),
            new Rectangle2D(1182, 96, 168, 117),
            new Rectangle2D(1455,147, 123,  48),
            new Rectangle2D(1581,120,  72,  93),
            new Rectangle2D(1917,120,  63,  90),
            new Rectangle2D(2217,129,  60,  63),
            new Rectangle2D(2502,129,  60,  63)
        };
         animationManager.addAnimation("skDead",
                new Animation(0.18,  skDead));
         
        Rectangle2D[] skShDead = {
            new Rectangle2D(93,   54,  84, 141),
            new Rectangle2D(390,  54,  87, 141),
            new Rectangle2D(678,  66,  93, 129),
            new Rectangle2D(912,  63, 117, 132),
            new Rectangle2D(1182, 96, 168, 117),
            new Rectangle2D(1455,147, 123,  48),
            new Rectangle2D(1581,120,  72,  93),
            new Rectangle2D(1917,120,  63,  90),
            new Rectangle2D(2217,129,  60,  63),
            new Rectangle2D(2502,129,  60,  63)
        };
         animationManager.addAnimation("skShDead",
                new Animation(0.18,  skShDead));
                  
        Rectangle2D[] slDead = {
            new Rectangle2D(73,  105, 90, 53),
            new Rectangle2D(318, 105, 87, 53),
            new Rectangle2D(73,  300, 90, 53),
            new Rectangle2D(73,  495, 90, 53),
            new Rectangle2D(73,  690, 90, 53),
            new Rectangle2D(93,  885, 50, 53),
            new Rectangle2D(93,  1080, 50, 53),
            new Rectangle2D(93,  1275, 50, 53),
            new Rectangle2D(83,  1495, 62, 28),
        };
        animationManager.addAnimation("slDead",
                new Animation(0.18, slDead)); 


        Rectangle2D[] mgDead = {
            new Rectangle2D(57,  93,   147, 237),
            new Rectangle2D(57,  471,  147, 240),
            new Rectangle2D(51,  849,  174, 240),
            new Rectangle2D(48,  1230, 207, 237),
            new Rectangle2D(45,  1623, 159, 222),
            new Rectangle2D(45,  2004, 159, 204),
            new Rectangle2D(45,  2382, 159, 168),
            new Rectangle2D(330, 93,   237, 237),
            new Rectangle2D(330, 471,  237, 240),
            new Rectangle2D(381, 849,  186, 240),
            new Rectangle2D(381, 1230, 186, 237)
        };
        animationManager.addAnimation("mgDead", 
                new Animation(0.18, mgDead));
        
        Rectangle2D[] owDead = {
            new Rectangle2D(192, 36,   122, 238),
            new Rectangle2D(216, 408,  122, 252),
            new Rectangle2D(268, 792,  102, 256),
            new Rectangle2D(284, 1176, 120, 259),
            new Rectangle2D(288, 1564, 122, 259),
            new Rectangle2D(272, 1984, 116, 238),
            new Rectangle2D(248, 2404, 148, 248),
            new Rectangle2D(216, 2824, 168, 220),
            new Rectangle2D(192, 3212, 166, 216),
            new Rectangle2D(180, 3600, 182, 230),
            new Rectangle2D(152, 3988, 176, 212),
            new Rectangle2D(136, 4376, 174, 198)
        };
        animationManager.addAnimation("owDead",
                new Animation(TIME_BY_FRAME, owDead));

}
    
    private void loadRunAnimation() {
        Rectangle2D[] skRun = {
            new Rectangle2D(90,   51, 132, 144),
            new Rectangle2D(375,  51, 132, 144),
            new Rectangle2D(660,  51, 132, 144),
            new Rectangle2D(945,  51, 132, 144),
            new Rectangle2D(1230, 51, 132, 144),
            new Rectangle2D(1515, 51, 132, 144),
        };
        animationManager.addAnimation("skRun",
                new Animation(TIME_BY_FRAME, skRun));
        

        Rectangle2D[] skShRun = {
            new Rectangle2D(72,   51, 150, 144),
            new Rectangle2D(357,  51, 150, 144),
            new Rectangle2D(642,  51, 150, 144),
            new Rectangle2D(927,  51, 150, 144),
            new Rectangle2D(1212, 51, 150, 144),
            new Rectangle2D(1497, 51, 150, 144),
        };

        animationManager.addAnimation("skShRun",
                new Animation(TIME_BY_FRAME, skShRun));    
        
        Rectangle2D[] slRun = {
            new Rectangle2D(202, 105,  90,  53),
            new Rectangle2D(506,  82,  89,  82),
            new Rectangle2D(871, 107,  89,  53),
            new Rectangle2D(194, 303, 103,  50),
            new Rectangle2D(506, 300,  89,  52),
            new Rectangle2D(187, 500, 120,  48),
            new Rectangle2D(501, 497, 102,  50),
            new Rectangle2D(199, 693,  95,  50),
            new Rectangle2D(496, 695, 119,  47),
            new Rectangle2D(202, 885,  90,  53),
            new Rectangle2D(506, 887,  94,  50),
            new Rectangle2D(202, 1065,  90,  68),
            new Rectangle2D(506, 1080,  89,  52),
            new Rectangle2D(149, 1212,  90,  70),
            new Rectangle2D(504, 1260,  89,  67)
        };
        animationManager.addAnimation("slRun",
                new Animation(TIME_BY_FRAME, slRun));

        Rectangle2D[] mgRun = {
            new Rectangle2D(57,  21,   147, 213),
            new Rectangle2D(342, 21,   162, 213),
            new Rectangle2D(57,  312,  150, 213),
            new Rectangle2D(342, 312,  159, 213),
            new Rectangle2D(57,  597,  150, 210),
            new Rectangle2D(342, 597,  150, 210),
            new Rectangle2D(57,  879,  159, 222),
            new Rectangle2D(342, 879,  147, 222),
            new Rectangle2D(57,  1167, 162, 228),
            new Rectangle2D(342, 1167, 147, 228),
        };
        animationManager.addAnimation("mgRun", 
                new Animation(0.14, mgRun));
        
         Rectangle2D[] owRun = {
            new Rectangle2D(172, 40,   130, 234),
            new Rectangle2D(808, 40,   122, 238),
            new Rectangle2D(172, 416,  130, 245),
            new Rectangle2D(808, 420,  122, 241),
            new Rectangle2D(168, 800,  136, 245),
            new Rectangle2D(808, 804,  128, 245),
            new Rectangle2D(152, 1180, 146, 256),
            new Rectangle2D(808, 1188, 122, 245),
            new Rectangle2D(148, 1572, 150, 256),
            new Rectangle2D(812, 1576, 120, 248),
            new Rectangle2D(148, 1980, 150, 238),
            new Rectangle2D(820, 1968, 118, 245),
            new Rectangle2D(156, 2388, 138, 220),
            new Rectangle2D(828, 2360, 116, 241),
            new Rectangle2D(152, 2780, 140, 216),
            new Rectangle2D(828, 2756, 120, 234)
        };
        animationManager.addAnimation("owRun",
                new Animation(TIME_BY_FRAME, owRun));
    }
    
    
    
    
    
    public AnimationManager getAnimationManager() { return animationManager; }
    
    
     /**
     *  
    * @param key Name of spriteSheet values: ( skRun , skAttack , skDead , skDeadIce , skIce , skShRun , skSkAttack )
    * @return Complete image type of spriteSheet
     */
      public Image getimageOfSpriteSheet(String key){ return spriteSheetImages.get(key); }
}

