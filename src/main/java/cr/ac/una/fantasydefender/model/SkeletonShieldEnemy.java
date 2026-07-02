package cr.ac.una.fantasydefender.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author Takkasama
 */
public class SkeletonShieldEnemy extends Enemy{
    
    private DoubleProperty currentHealth;
    
    public SkeletonShieldEnemy(int gameLevel, double difficulty) {
        
        super();
        this.enemyPrefix = "skSh";
        this.level = (int)(gameLevel * difficulty);
        this.healthBase = 21.0;
        this.speedBase = 0.03;
        this.damageBase = 3.0;
        this.rangeAttackBase = 1.0;
        
        this.pointsValue =3;
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
       
       this.damage = damageBase + (1 + (level / 8.0));
       this.health = healthBase + (1 + (level / 4.0));
       this.speed = speedBase;
       this.rangeAttack = rangeAttackBase;
       
   }

    public double getCurrentHealth() { return currentHealth.get(); }
    public void setCurrentHealth(double currentHealth) { this.currentHealth.set(currentHealth); }

    
    public DoubleProperty getCurrentHealthProperty(){ return currentHealth; }

}
