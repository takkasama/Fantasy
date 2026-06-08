package cr.ac.una.fantasydefender.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Takkasama
 */
public class LevelGame {
    private static final int ENEMIES_BASE = 15;
    private double difficulty;
    
    private IntegerProperty currentWave;

    private double factorLevel;
    private int currentLevel;
    
    private int waves;
    private int totalEnemies;
    private double frecuencyEnemy;
    
    
    public LevelGame() {}
    public LevelGame(int level, double difficulty){
        this.currentWave = new SimpleIntegerProperty(0);
        this.currentLevel = level;
        this.difficulty = difficulty;
        buildLevel();
    }

    private void buildLevel(){
        this.factorLevel = (int)(1 + (this.currentLevel * 0.15));
        this.totalEnemies = calculateTotalEnemies();
        this.waves = calculateWaves();
        this.frecuencyEnemy = calculateFrecuencyEnemies();
        
        
    }
    
    
    private int calculateWaves() { return  Math.max(1, currentLevel / 2 + 1); }

    private double calculateFrecuencyEnemies() {return  Math.max(0.3, 2.0 - (factorLevel * 0.01)); }

    private int calculateTotalEnemies() { return ENEMIES_BASE + (currentLevel * 3); }

    public int calculateEnemiesPerWave() { return totalEnemies / waves; }    

    public double getDifficulty() { return difficulty; }

    public double getFactorLevel() { return factorLevel; }

    public int getWaves() { return waves; }

    public int getTotalEnemies() { return totalEnemies; }

    public double getFrecuencyEnemy() { return frecuencyEnemy; }    
    
    public int getCurrentLevel(){ return currentLevel; }
    
    public int getCurrentWave(){ return currentWave.get(); }
    public void setCurrentWave(int currentWave) { this.currentWave.set(currentWave); } 
    
    
    public IntegerProperty getCurrentWaveProperty(){ return currentWave; }
    
}
