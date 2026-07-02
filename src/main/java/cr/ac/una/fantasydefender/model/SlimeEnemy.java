package cr.ac.una.fantasydefender.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author Takkasama
 */
public class SlimeEnemy extends Enemy{
    
 private DoubleProperty currentHealth;

    public SlimeEnemy(int gameLevel , double difficulty) { 
        super();
        this.enemyPrefix = "sl";
        this.level = (int)(gameLevel * difficulty);
        this.healthBase = 11;
        this.speedBase = 0.03;
        this.damageBase = 2;
        this.rangeAttackBase = 1.0;
        
        this.pointsValue = 2;
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
       
       this.damage = damageBase + (1.0 + (level / 8.0));
       this.health = healthBase + (level / 8.0) * 5;
       this.speed = speedBase ;
       this.rangeAttack = rangeAttackBase;
       
   }

    public double getCurrentHealth() { return currentHealth.get(); }
    public void setCurrentHealth(double currentHealth) { this.currentHealth.set(currentHealth); }
    
    public DoubleProperty getCurrentHealthProperty(){ return currentHealth; }
         
}
