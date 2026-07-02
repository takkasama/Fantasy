package cr.ac.una.fantasydefender.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author Takkasama
 */
public class OrcWolfEnemy extends Enemy{
    
    private DoubleProperty currentHealth;

    public OrcWolfEnemy(int gameLevel , double difficulty) { 
        super();
        this.enemyPrefix = "ow";
        this.level = (int)(gameLevel * difficulty);
        this.healthBase = 40.0;
        this.speedBase = 0.02;
        this.damageBase = 4;
        this.rangeAttackBase = 1.02;
        
        this.pointsValue = 4;
        buildEnemy();
        
        this.currentHealth = new SimpleDoubleProperty(health);
    }
    
    @Override
    public void kicked(double towerDamage){ 
        setCurrentHealth( currentHealth.get() - towerDamage);
       

        if(currentHealth.get() <= 0){
            setLifeState(Enemy.LifeState.DIED);
            setDeadTimer(0.0);
        }
    }   
    
    @Override
   public final void buildEnemy(){
       
       this.damage = damageBase + (1 + (level / 6));
       this.health = healthBase + (1 + (level / 4));
       this.speed = speedBase;
       this.rangeAttack = rangeAttackBase;
       
   }

    public double getCurrentHealth() { return currentHealth.get(); }
    public void setCurrentHealth(double currentHealth) { this.currentHealth.set(currentHealth); }
    
    public DoubleProperty getCurrentHealthProperty(){ return currentHealth; }
         
}
