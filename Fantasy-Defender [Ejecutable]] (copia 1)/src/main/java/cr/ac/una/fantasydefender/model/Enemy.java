package cr.ac.una.fantasydefender.model;
/**
 *
 * @author TakkaSama
 */
public abstract class Enemy {
   
    //ENUMS FOT TO IDENTIFY THE STATES
    public enum LifeState{ 
        ALIVE,
        DIED
    }
    public  enum EffectState{
        ICED,
        BURNED,
        NONE
    }
    public enum Action{
        RUN,
        ATTACK
    }
    
    protected String enemyPrefix;    
    protected int level;
    
    //POSITION ON BOARD
    private double relX;
    private double relY;
    
    //HITBOX
    private double width;
    private double height;
    
    //STATS BASE OF THE ENEMY
    
    protected double healthBase;
    protected double damageBase;
    protected double speedBase;
    protected double rangeAttackBase;
    
    // STATS THAT WILL MODIFIED 
    
    protected double health;
    protected double damage;
    protected double speed;
    protected double rangeAttack;
    
    private double frecuencyAttack;
    
    protected int pointsValue;
    
    //STATES OF THE ENEMY
    private LifeState lifeState;
    private EffectState effectState;
    private Action  currentAction;
    
    //TIMER OF ENEMY DEAD ANIMATION
    private double deadTimer;
    private boolean isDeadEnd;
    
    private double lastAttack;
    
    

    public Enemy(){
        this.level = 1;
        this.enemyPrefix = "";
        
        this.relX =0;
        this.relY =0;
        
        this.width =0;
        this.height =0;
        
        this.healthBase = 1;
        this.damageBase = 1;
        this.speedBase = 1;
        this.rangeAttackBase = 1;
        
        this.frecuencyAttack = 0.7;
        
        this.health =0;
        this.damage =0;
        this.speed =0;
        this.rangeAttack =0;
        
        this.deadTimer =0;
        this.isDeadEnd = false;
        
        this.lastAttack =0;
        
        this.pointsValue =0;
        
        this.lifeState = LifeState.ALIVE;
        this.effectState = EffectState.NONE;
        this.currentAction = Action.RUN;
    }
    
    public int getLevel(){ return this.level; }
    
    public double getRelX() { return relX; }
    public void setRelX(double relX) { this.relX = relX; }

    public double getRelY() { return relY; }
    public void setRelY(double relY) { this.relY = relY; }

    public double getWidth() { return width; }
    public void setWidth(double width) { this.width = width; }

    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }
    
    
    public double getHealth() { return health; }
    public void setHealth(double health) { this.health = health; }

    public double getDamage() { return damage; }
    public void setDamage(double damage) { this.damage = damage; }

    public double getSpeed() { return speed; }
    public void setSpeed(double speed) { this.speed = speed; }

    public double getRangeAttack() { return rangeAttack; }
    public void setRangeAttack(double rangeAttack) { this.rangeAttack = rangeAttack; }

    public double getFrecuencyAttack(){ return frecuencyAttack; }
    public void setFrecuencyAttack(double frecuencyAttack){ this.frecuencyAttack =frecuencyAttack; }
    
    public int getPointsValue(){ return pointsValue; }
    public void setPointsValue(int pointsValue){ this.pointsValue = pointsValue; }
    
    public LifeState getLifeState() { return lifeState; }
    public void setLifeState(LifeState lifeState) { this.lifeState = lifeState; }

    public EffectState getEffectState() { return effectState; }
    public void setEffectState(EffectState effectState) { this.effectState = effectState; }

    public Action getCurrentAction() { return currentAction; }
    public void setCurrentAction(Action currentAction) { this.currentAction = currentAction; }
          
    public String getEnemyPrefix(){ return enemyPrefix; }
    public void setEnemyPrefix(String enemyPrefix){ this.enemyPrefix = enemyPrefix; }

    public double getDeadTimer() { return deadTimer; }
    public void setDeadTimer(double deadTimer) { this.deadTimer = deadTimer; }

    public boolean getIsDeadEnd() { return isDeadEnd; }
    public void setIsDeadEnd(boolean isDeadEnd) { this.isDeadEnd = isDeadEnd; }
    
    public double getLastAttack(){ return lastAttack; }
    public void setLastAttack(double lastAttack){ this.lastAttack = lastAttack; }
    
    public abstract void buildEnemy();
    
    public  abstract void kicked(double towerDamage);
    

    /**
     *
     * @param  deltaTime - Tiempo Transcurrido entre frame actual y el anterior para determinar la distancia que movio el enemigo 
     */
    public void update(double deltaTime){
        if(lifeState == LifeState.DIED) {
            this.deadTimer += deltaTime;
            
            if(deadTimer >= 1.5)
                isDeadEnd = true;
            
            return;
        }
        
        if(effectState == EffectState.ICED) return;
        
        if(currentAction == Action.ATTACK) return;
        
        
        
       setRelX(relX - speed * deltaTime);        
    }    
    
    public void defineEnemyPosition(double x, double y){
        this.relX = x;
        this.relY = y;
    }
    
    public void defineEnemyHitbox(double width, double height){
        this.width = width;
        this.height = height;
    }
        
}
