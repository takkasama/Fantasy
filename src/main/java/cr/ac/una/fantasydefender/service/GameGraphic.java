package cr.ac.una.fantasydefender.service;

import cr.ac.una.fantasydefender.model.CastleDTO;
import cr.ac.una.fantasydefender.model.Enemy;
import cr.ac.una.fantasydefender.util.AppContext;
import java.util.List;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 *
 * @author Takkasama
 */
public class GameGraphic {
        
    private static final double SCALE_WIDTH_ENEMY = 0.05;
    private static final double SCALE_HEIGHT_ENEMY = 0.09;
    private static final double SCALE_WIDTH_CASTLE = 0.18;
    
    private EnemyAnimationManager enemyAnimation;
    private CastleAnimationManager castleAnimation;
    private Canvas gameCanvas;
    private GraphicsContext gc;

    public GameGraphic(Canvas gameCanvas, GraphicsContext graphicContext){
        this.gameCanvas = gameCanvas;
        this.gc = graphicContext;
        
        this.enemyAnimation = EnemyAnimationManager.getInstance();
        this.castleAnimation = CastleAnimationManager.getInstance();

    }
    
    
    
    public void render(double time, List<Enemy> enemies,
            double angleCrossbow, boolean isCrossbowShoting,
            double clickX, double clickY, 
            int currentHealthCastle, int currentElixerCastle, CastleDTO.CastleState castlestate) {
        
        double width = gameCanvas.getWidth();
        double height = gameCanvas.getHeight();
       
        gc.clearRect(0, 0, width, height);        
        
        drawCastle(time, castlestate);
        
        for (Enemy en : enemies)
            animateEnemies(time, en);
                
        drawCrossbow(time, angleCrossbow, isCrossbowShoting);
        
        drawShoot(time, clickX, clickY,isCrossbowShoting);

        drawHealthBar(time, currentHealthCastle);
        
        drawElixerBar(time, currentElixerCastle);    
    
        
    }
    
    private void drawCastle(double time, CastleDTO.CastleState state ){
        String keyAnimation = null;
                
        switch (state) {
            case HEALTY -> keyAnimation = "castle1";
            case SEMIDAMAGED -> keyAnimation = "castle2";
            case DAMAGED -> keyAnimation = "castle3";
            case CRITICAL -> keyAnimation = "castle4";
            case DESTROYED -> keyAnimation = "castle5";
        }
        
        Rectangle2D frame = castleAnimation.getAnimationManager().getFrame(time, keyAnimation);
        
        gc.drawImage(castleAnimation.getimageOfSpriteSheet("ctlCastle"), 
                frame.getMinX(), frame.getMinY(),
                frame.getWidth(), frame.getHeight(),
                0, 0, 
                gameCanvas.getWidth() * SCALE_WIDTH_CASTLE , 
                gameCanvas.getHeight());        
        
    }
    
     /**
     *  
     * @param time  - Tiempo de la vista en segundos para el calculo del frame actual de la barra de vida.
     */   
    private void drawHealthBar(double time , int currentHealthCastle) {
                
         int  healthCastle= currentHealthCastle >=0 ? currentHealthCastle : 0;
        
        String animationKey = "lifeBar" + healthCastle;

        Rectangle2D frame = castleAnimation.getAnimationManager().getFrame(time, animationKey);

        if(frame == null) return;
        
        double width = gameCanvas.getWidth()  * 0.18;
        double height = width * (frame.getHeight() / frame.getWidth());

        double x = gameCanvas.getWidth()  * 0.01;
        double y = gameCanvas.getHeight() * 0.02;

        gc.drawImage(
            castleAnimation.getimageOfSpriteSheet("ctlLifeBar"),
            frame.getMinX(), frame.getMinY(),
            frame.getWidth(), frame.getHeight(),
            x , y , width , height
        );
    }

      /**
     *  
     * @param time  - Tiempo de la vista en segundos para el calculo del frame actual de la barra de mana.
     */
    private void drawElixerBar(double time, int currentElixerCastle){
        
        int elixerCastle = currentElixerCastle >= 0 ? currentElixerCastle : 0;
        String animationKey = "ElixerBar" + elixerCastle; 

        Rectangle2D frame = castleAnimation.getAnimationManager().getFrame(time, animationKey);

        if(frame == null) return;
        
        double width = gameCanvas.getWidth()  * 0.18;
        double height = width * (frame.getHeight() / frame.getWidth());

        double x = gameCanvas.getWidth()  * 0.20;
        double y = gameCanvas.getHeight() * 0.02;

        gc.drawImage(
            castleAnimation.getimageOfSpriteSheet("ctlManaBar"),
            frame.getMinX(), frame.getMinY(),
            frame.getWidth(), frame.getHeight(),
            x , y , width , height
        );
    }
    
    private void drawCrossbow(double time, double angleCrossbow, boolean isCrossbowShoting){
        
        String animationKey = isCrossbowShoting ? "ctlCrossbow1Shoot" : "ctlCrossbow1Still";
        
        Rectangle2D frame = castleAnimation.getAnimationManager().getFrame(time ,animationKey);
        
        if(frame == null) return;
        
        double x = gameCanvas.getWidth() * 0.03;
        double y = (gameCanvas.getHeight() / 2) - frame.getHeight();
        
        double width  = gameCanvas.getWidth() * 0.10;
        double height = gameCanvas.getHeight() * 0.15;

        double resX = x + width * 0.25;
        double resY = y + height * 0.50;        
        
        Image spriteSheet = castleAnimation.getimageOfSpriteSheet("ctlCrossbow1");
        
        if(spriteSheet == null) return;

        gc.save();

        gc.translate(resX, resY);
        gc.rotate(angleCrossbow);

        gc.drawImage(
                spriteSheet,
                frame.getMinX(), frame.getMinY(),
                frame.getWidth(), frame.getHeight(),
                -width * 0.25, -height * 0.50,
                width, height
        );

        gc.restore();
    }

    private void drawShoot(double time, double clickX, double clickY, boolean isCrossbowShoting) {
        if (!isCrossbowShoting) return;

        Rectangle2D frame = castleAnimation.getAnimationManager().getFrame(time, "ctlImpact1");
        if (frame == null) return;

        Image spritesheet = castleAnimation.getimageOfSpriteSheet("ctlCrossbow1"); 

        double arrowWidth  = gameCanvas.getWidth()  * 0.04;
        double arrowHeight = gameCanvas.getHeight() * 0.02;

        gc.drawImage(spritesheet,
                frame.getMinX(), frame.getMinY(),
                frame.getWidth(), frame.getHeight(),
                clickX, clickY, 
                arrowWidth, arrowHeight              
        );
    }
    
    /**
     *  
     * @param time  - Tiempo de la vista en segundos para el calculo del frame actual del enemigo.
     * @param enemyDTO  - Enemgio del cual se obtendra su posicion para ubicarlo
     */
    private void animateEnemies(double time, Enemy enemyDTO){
        
        String enemyAction = enemyDTO.getEnemyPrefix() + getEnemyAction(enemyDTO);
        
        Rectangle2D frame = enemyAnimation.getAnimationManager().getFrame(time, enemyAction);
        Rectangle2D frameDefined = defineFrame(enemyDTO);
       
        if (frame == null || frameDefined == null) {
            System.out.println("Frame null: " + enemyAction);
            return;
        }

        Image spriteSheet = enemyAnimation.getimageOfSpriteSheet(enemyAction);
        
        if (spriteSheet == null) return;        

        gc.drawImage(
            spriteSheet,
            frame.getMinX(), frame.getMinY(),
            frame.getWidth(), frame.getHeight(),
            frameDefined.getMinX(), frameDefined.getMinY(),
            frameDefined.getWidth(),frameDefined.getHeight()
        );
    }
    
     /**
     *  
    * @param enemyDTO enemigo del cual se obtendra las posciones de x y para calcular su posicion relativa
    * @return las dimensiones del frame del enemigo redefinas y su ubicacion relativa en base a las proporciones de la vista
     */
    private Rectangle2D defineFrame(Enemy enemyDTO) {

        double baseWidth  = gameCanvas.getWidth()  * SCALE_WIDTH_ENEMY;
        double baseHeight = gameCanvas.getHeight() * SCALE_HEIGHT_ENEMY;
        
        double x = gameCanvas.getWidth()  * enemyDTO.getRelX();
        double y = gameCanvas.getHeight() * enemyDTO.getRelY();
        
        enemyDTO.defineEnemyHitbox(baseWidth, baseHeight);

        return new Rectangle2D(x, y, baseWidth, baseHeight);
    }
    
    private String getEnemyAction(Enemy enemyDTO){
        
        if(enemyDTO.getEffectState().equals(Enemy.EffectState.ICED))
            return "Ice";
        if(enemyDTO.getLifeState().equals(Enemy.LifeState.DIED))
            return "Dead";
        if(enemyDTO.getCurrentAction().equals(Enemy.Action.ATTACK)) 
            return "Attack";
         return "Run";
    }    

    
    
}
