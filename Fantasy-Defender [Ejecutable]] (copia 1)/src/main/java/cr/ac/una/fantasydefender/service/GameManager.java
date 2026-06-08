package cr.ac.una.fantasydefender.service;

import cr.ac.una.fantasydefender.model.CastleDTO;
import cr.ac.una.fantasydefender.model.CrossbowDTO;
import cr.ac.una.fantasydefender.model.Enemy;
import cr.ac.una.fantasydefender.model.GameDTO;
import cr.ac.una.fantasydefender.model.LevelGame;
import cr.ac.una.fantasydefender.model.SpecialAttack;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 *      LA PRESENTE CLASE SINCRONIZARA ANIMACION , DIBUJADO Y DANO DEL JUEGO 
 *      ESTA CLASE ES DE UNA UNINCA INSTANCIA QUE SERA LLAMADA UNICAMENTE EN RUNTIME DE JUEGO ES DECIR EN LA VISTA DE JUEGO
 * 
 *      LA UNICA EXEPCION ES LA LLAMADA DE ESTA CLASE EN MULTIPLES DE VISTA EN EL PROYECTO PERSONAL DE PRUEBAS GRAFICAS.
 * 
 * @author Takkasama
 */
public class GameManager {
    
    private static final double SCALE_WIDTH_ENEMY = 0.05;
    private static final double SCALE_HEIGHT_ENEMY = 0.09;
    private static final double SCALE_WIDTH_CASTLE = 0.18;
    private static final double SHOOT_ANIM_DURATION = 0.15;

    //CONSTRUCCION DE JEUGO
    private  SpecialAttack specialAttackDTO;
    private GameDTO game;
    private CrossbowDTO crossbow;
    private CastleDTO castle;
    private LevelGame levelGame;    
    private  EnemyManager enemyManager;
        
    //ATRIBUBTO DE CONTROL DE ANIMACION 

    private GameGraphic graphics;
    private Canvas gameCanvas;
    private AnimationTimer timer;
       
    //ATRIBUTOS DE CONTROL DE JUEGO

    private Map<Integer, List<Enemy>> waves;
    private List<Enemy> enemies;
    private List<Enemy> currentWave;
    
    private int waveNumber;    
    private int waveSpawnIndex;
    private double enemyFrecuency;
    
    private long lastTime;
    
    private double angleCrossbow;
    private double shootCooldown;
    private boolean isShooting;
    private double shootAnimTime;
    
    private double clickX;
    private double clickY;
    
    private int currentHealthCastle;
    private int currentElixerCastle;

    private int totalPoints;
    
    public  GameManager(){ }
    
    public GameManager(Canvas canva, GraphicsContext graphicsContext, GameDTO game){

        this.game = game;

        this.gameCanvas = canva;
        this.graphics = new GameGraphic(gameCanvas, graphicsContext);
        
        this.currentWave = new ArrayList<>();
        this.enemies = new ArrayList<>();
        
        this.waves = new HashMap<>();
    }

    public LevelGame getLevelGame(){ return levelGame; }
    
    public void buildGame(){   
        
        this.crossbow = game.getCrossbow();
        this.castle = game.getCastle();
        
        this.castle.updateStats();
        this.crossbow.updateStats();
        
        this.levelGame = new LevelGame(game.getLevel(), game.getDifficulty());
        this.enemyManager = new EnemyManager(levelGame);
    
        
        this.waveNumber = 0;
        
        this.waves = enemyManager.getListEnemyToSpawn();
        this.levelGame.setCurrentWave(levelGame.getWaves());
        
        this.currentWave = waves.get(waveNumber);
        
        this.waveSpawnIndex = 0;
        
        this.enemyFrecuency = 0;
        
        this.lastTime = -1;
        
        this.angleCrossbow = 0;
              
        this.clickX =0;
        
        this.clickY =0;
        
        this.totalPoints = 0;
        
        defineEnemyPositions();
        
        verifyClicked();
        
        verifyCastleStats();
        
    }
    
    private void defineEnemyPositions(){
        Random rand = new Random();

        double relX = 1.0 - SCALE_WIDTH_ENEMY;        
        for(int i =0 ; i < levelGame.getWaves(); i++)
            for(Enemy en : waves.get(i))
            en.defineEnemyPosition(relX, rand.nextDouble(1.0 - SCALE_HEIGHT_ENEMY));

    }
      
    private void spawnEnemies(double delltaTime){
                
        //Control de frecuencia de spawn de enemigos en base a a la frecuencia obtenida de LevelGame
        if(waveSpawnIndex < currentWave.size()){
            
            enemyFrecuency += delltaTime;
            
            if(enemyFrecuency >= levelGame.getFrecuencyEnemy()){
                enemyFrecuency = 0;
                enemies.add(currentWave.get(waveSpawnIndex));                
                Comparator<Enemy> compare = (en1, en2) -> {
                    if(en1.getRelY() > en2.getRelY()) return 1;
                    if(en1.getRelY() < en2.getRelY()) return  -1;
                    return 0;
                };
                
                enemies.sort(compare);
                waveSpawnIndex++;
            }
                        
        }
        
        
        //Control de pasado de oleada
        boolean isEnemyWaveSpawned = waveSpawnIndex >= currentWave.size();
        boolean isValidNextWave = enemies.isEmpty();
        
        if(isEnemyWaveSpawned && isValidNextWave){
            waveNumber++;
            levelGame.setCurrentWave(levelGame.getWaves() - waveNumber);
            
            if(levelGame.getWaves() > waveNumber){
                
                currentWave = waves.get(waveNumber);
                waveSpawnIndex =0;
                enemyFrecuency =0;
            }
            
        }
    }
    
    
    private void updateEnemies(double deltaTime) {

        for (Enemy en : enemies) {     
            
            en.update(deltaTime);
            double limitX = (SCALE_WIDTH_CASTLE * en.getRangeAttack());
            if (en.getRelX() <=limitX ) {
                en.setCurrentAction(Enemy.Action.ATTACK);
                
                if(en.getLastAttack() <= en.getFrecuencyAttack())
                        en.setLastAttack(en.getLastAttack() + deltaTime);
                else if(en.getLastAttack() >= en.getFrecuencyAttack()){
                    en.setLastAttack(0);
                    castle.castleKicked(en.getDamage());
                }

            }
            
        }
        
        List<Enemy> toRemove = new ArrayList<>();
        for(Enemy en : enemies){
            if(en.getIsDeadEnd()){
                totalPoints += en.getPointsValue();
                toRemove.add(en);
            }
        }
        enemies.removeAll(toRemove);
        
    }
    
    public void updateCrossbow(double deltaTime){
        
        if(shootCooldown > 0){
            shootCooldown -= deltaTime;
        }

        if(isShooting){
            shootAnimTime += deltaTime;

            if(shootAnimTime >= SHOOT_ANIM_DURATION){
                isShooting = false;
                shootAnimTime = 0;
            }
        }
    }
    
    
    public void startGame(){
        
        if(timer != null){
            timer.start();
            return;
        }
        
        buildGame();
        
        timer = new AnimationTimer() {
            @Override
            public void handle(long now){
                if(lastTime < 0){
                    lastTime = now;
                    return;
                }
                double deltaTime = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;
                
                
                spawnEnemies(deltaTime);
                updateEnemies(deltaTime);    
                updateCrossbow(deltaTime);
                verifyCastleStats();
                verifyVictory();
                
                graphics.render(now / 1_000_000_000.0, enemies, angleCrossbow, isShooting,
                        clickX, clickY, currentHealthCastle, currentElixerCastle, castle.getState());

            }
        };       
       playGame();
    }
    
    private void calculateAngleRotation(){
            double width = gameCanvas.getWidth() * 0.12;
            double height = gameCanvas.getHeight() * 0.15;

            double x = gameCanvas.getWidth() * 0.03;
            double y = (gameCanvas.getHeight() / 2) - height;

            double baseX = x + width / 2;
            double baseY = y + height / 2;

            double vectorX = clickX- baseX;
            double vectorY = clickY - baseY;

            double radials  = Math.atan2(vectorY, vectorX);

            angleCrossbow = Math.toDegrees(radials);
    }

    
    private void verifyShootCrossbow(){
        if(shootCooldown > 0)
            return;

        shootCooldown = crossbow.getFrecuency();

        isShooting = true;
        shootAnimTime = 0;    
        
    }
    
    private void verifyClicked(){       

        gameCanvas.setOnMouseClicked(event -> {
            
            this.clickX = event.getX();
            this.clickY = event.getY();
            calculateAngleRotation();
            verifyShootCrossbow();
            verifyEnemyKicked();
            //verifySpecialAtackArea();
            
        });
    }
        
    private void verifyEnemyKicked(){
                
        for(Enemy en : enemies){

            double enemyX = gameCanvas.getWidth() * en.getRelX();
            double enemyY = gameCanvas.getHeight() * en.getRelY();
            
            boolean isContainX = clickX >= enemyX && clickX <= enemyX + en.getWidth();

            boolean isContainY = clickY >= enemyY && clickY <= enemyY + en.getHeight();
            
            if(isContainX && isContainY && isShooting){
                en.kicked(crossbow.getDamage());
                break;
            }
 
        }        
    }
     
    private void verifySpecialAtackArea(){
        double containamentArea = Math.pow(specialAttackDTO.getX() - clickX, 2) + Math.pow(specialAttackDTO.getY() - clickY, 2);
        if(containamentArea <= specialAttackDTO.getRadio()){
            
            //LOGICA DE ATAQUE ESPECIAL 
            
        }
    
    }
    
    private void verifyVictory(){
        
        if(waveNumber > levelGame.getWaves() && enemies.isEmpty() && castle.getState() != CastleDTO.CastleState.DESTROYED){
            game.setLevel(game.getLevel() + 1);
            game.setPoints(game.getPoints() + totalPoints);
            System.out.println(totalPoints);
            pauseGame();
            game.setGameResult(GameDTO.GameResult.VICTORY);
            game.setGameState(GameDTO.GameState.PAUSED);
        }
        
        if(castle.getState() == CastleDTO.CastleState.DESTROYED){
            this.totalPoints = 0;
            pauseGame();
            game.setGameResult(GameDTO.GameResult.DEFEAT);
            game.setGameState(GameDTO.GameState.PAUSED);
        }
        
    }
    
    private void verifyCastleStats(){
        this.currentHealthCastle =(int)(Math.ceil(castle.getHealth() * 10.0 / castle.getMaxHealth()));
        this.currentElixerCastle = (int)(Math.ceil(castle.getElixer() * 10.0 / castle.getMaxElixer()));          
    }
    
    public void pauseGame() { 
       if(timer != null) timer.stop(); 
    }
     
    public void playGame() {
        if(timer != null )timer.start();
    }
    
    public void resetGame() { 
        
        pauseGame();
        lastTime = -1;
        enemies.clear();
        buildGame();
        playGame();
        
    }
    
    public void stopGame(){
        pauseGame();
        enemies.clear();
    }
    
}
