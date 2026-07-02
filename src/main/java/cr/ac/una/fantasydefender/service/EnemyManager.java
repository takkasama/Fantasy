package cr.ac.una.fantasydefender.service;

import cr.ac.una.fantasydefender.model.Enemy;
import cr.ac.una.fantasydefender.model.LevelGame;
import cr.ac.una.fantasydefender.model.MageEnemy;
import cr.ac.una.fantasydefender.model.SkeletonShieldEnemy;
import cr.ac.una.fantasydefender.model.SkeletonEnemy;
import cr.ac.una.fantasydefender.model.SlimeEnemy;
import cr.ac.una.fantasydefender.model.OrcWolfEnemy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Takkasama
 */
public class EnemyManager {
    
    public enum EnemyType {
        SLIME, 
        SKELETON, 
        SKELETON_SHIELD,
        ORC_WOLF,
        MAGE        
    }
    
    private LevelGame levelGame;
    private Map<EnemyType, Integer> enemiesProbabilities;
    private final Random randProbabilites;
    
    public EnemyManager(LevelGame levelGame){ 
        this.randProbabilites = new Random();
        this.levelGame = levelGame;
        this.enemiesProbabilities = new HashMap<>();
        defineEnemiesProbabilties();
      }
    
    private void defineEnemiesProbabilties() {
        int currentLevel = levelGame.getCurrentLevel();

        int slime = Math.max(20, 100 - currentLevel);
        int skeleton = currentLevel >= 10 ? Math.min(currentLevel, 100) : 0;
        int skelShield = currentLevel >= 20 ? Math.min(currentLevel - 20, 60) : 0;
        int mage = currentLevel >= 30 ? Math.min(currentLevel - 30, 40) : 0;
        int orcWolf = currentLevel >= 40 ? Math.min(currentLevel - 40, 20) : 0;

        double total = slime + skeleton + skelShield + mage + orcWolf;

        enemiesProbabilities.put(EnemyType.SLIME, (int) Math.round(slime / total * 100));

        enemiesProbabilities.put(EnemyType.SKELETON,(int) Math.round(skeleton / total * 100));

        enemiesProbabilities.put(EnemyType.SKELETON_SHIELD, (int) Math.round(skelShield / total * 100));

        enemiesProbabilities.put(EnemyType.MAGE, (int) Math.round(mage / total * 100));

        enemiesProbabilities.put(EnemyType.ORC_WOLF, (int) Math.round(orcWolf / total * 100));
    }
    
    public void updateEnemyManager(int currentLevel, double difficulty){
        this.levelGame = new LevelGame(currentLevel, difficulty);
        defineEnemiesProbabilties();
    } 
    
    public HashMap<Integer, List<Enemy>> getListEnemyToSpawn(){
        
        int numWaves = levelGame.getWaves();
        HashMap<Integer, List<Enemy>> waves = new HashMap<>();
                
        for (int i = 0; i < numWaves; i++ )
            waves.put((i) , buildWave(i));
        
        return waves;
    }

    private List<Enemy> buildWave(int waveNumber){
        List<Enemy> enemies = new ArrayList<>();
        for(int i = 0; i < levelGame.calculateEnemiesPerWave(); i++)
            enemies.add(createEnemy());
        
        return enemies;
    }
        
    private Enemy createEnemy(){
        
        int probability = randProbabilites.nextInt(100);
        int acumulativeProbability = 0;
        
        acumulativeProbability += enemiesProbabilities.get( EnemyType.SLIME);
        if(probability < acumulativeProbability)
            return new SlimeEnemy(levelGame.getCurrentLevel(), levelGame.getDifficulty());

        acumulativeProbability += enemiesProbabilities.get( EnemyType.SKELETON);
        if(probability <acumulativeProbability)
           return new SkeletonEnemy(levelGame.getCurrentLevel(), levelGame.getDifficulty());
        
        acumulativeProbability += enemiesProbabilities.get( EnemyType.SKELETON_SHIELD);
        if(probability < acumulativeProbability)
            return new SkeletonShieldEnemy(levelGame.getCurrentLevel(), levelGame.getDifficulty());
        
        acumulativeProbability += enemiesProbabilities.get( EnemyType.MAGE);
        if(probability < acumulativeProbability)
            return new MageEnemy(levelGame.getCurrentLevel(), levelGame.getDifficulty());
            
        acumulativeProbability += enemiesProbabilities.get(EnemyType.ORC_WOLF);
        if(probability > acumulativeProbability)
           return new OrcWolfEnemy(levelGame.getCurrentLevel(), levelGame.getDifficulty());
        
        return new SlimeEnemy(levelGame.getCurrentLevel(), levelGame.getDifficulty()); 

    }
            
}


