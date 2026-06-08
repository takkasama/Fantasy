package cr.ac.una.fantasydefender.model;

import javafx.beans.property.*;

/**
 *
 * @author TakkaSama
 */
public class CastleDTO {
    
    public static enum CastleState { 
        HEALTY, 
        DAMAGED, 
        SEMIDAMAGED,
        CRITICAL,
        DESTROYED 
        
    }

    private static final double BASE_HEALTH = 250.0;
    private static final double HEALTH_UPGRADE_PER_LEVEL = 10.0;
    
    private static final double BASE_ELIXER =30.0;
    private static final double ELIXER_UPGRADE_PER_LEVEL = 5.0;
    
    private StringProperty id;
    private IntegerProperty healthLevel;
    private IntegerProperty elixerLevel;
    
    private DoubleProperty elixer;
    private  DoubleProperty health; 
    private ObjectProperty<CastleState> state; 
    
    private Long version;
    
    public CastleDTO() {
        this.id = new SimpleStringProperty("");
        this.elixerLevel = new SimpleIntegerProperty(1);
        this.healthLevel = new SimpleIntegerProperty(1);
        this.elixer = new SimpleDoubleProperty(BASE_ELIXER);
        this.health = new SimpleDoubleProperty(BASE_HEALTH);
        this.state = new SimpleObjectProperty(CastleState.HEALTY);
    }
    
    public CastleDTO(Castle castle){
        this();
        this.id.set(castle.getId().toString());
        this.elixerLevel.set(castle.getElixerLvl());
        this.healthLevel.set(castle.getHealthLvl());
        this.version = castle.getVersion();
    
        updateStats();
    }
    
    //STANDAR VALUES 
    
    public Long getId(){
        if(id.get() == null || id.get().isBlank())
            return null;
        
        return Long.valueOf(id.get());
    }
    
    public int getHealthLevel() { return healthLevel.get(); }   
    public void setHealthLevel(int healthLevel) { this.healthLevel.set(healthLevel); }

    public int getElixerLevel() { return elixerLevel.get(); }
    public void setElixerLevel(int elixerLevel) { this.elixerLevel.set(elixerLevel); }

    public double getElixer() { return elixer.get(); }
    public void setElixer(double elixer) { this.elixer.set(elixer); }

    public double getHealth() { return health.get(); }
    public void setHealth(double health) { this.health.set(health); }

    public CastleState getState(){ return state.get(); }
    public void setState(CastleState state){ this.state.set(state); }
    
    public Long getVersion(){ return version; }
    public void setVersion(Long version){ this.version = version; }
    
    // PROPERTY VALUES
    public StringProperty getIdProperty () { return id; }

    public IntegerProperty getHealthLevelProperty() { return healthLevel; }

    public IntegerProperty getElixerLevelProperty() { return elixerLevel; }

    public DoubleProperty getElixerProperty() { return elixer; }

    public DoubleProperty getHealthProperty() { return health; }

    public ObjectProperty<CastleState> getStateProperty() { return state; }
    
    
    
    public double getMaxHealth(){ return  BASE_HEALTH + (HEALTH_UPGRADE_PER_LEVEL * getHealthLevel()); }
    public double getMaxElixer(){return BASE_ELIXER + (ELIXER_UPGRADE_PER_LEVEL * getElixerLevel()); }

    public void castleKicked(double enemyDamage){

        setHealth(Math.max(0, health.get() - enemyDamage));

        double healthPercent = health.get() / getMaxHealth();

        if(healthPercent <= 0){
            setState(CastleState.DESTROYED);
        }
        else if(healthPercent <= 0.25){
            setState(CastleState.CRITICAL);
        }
        else if(healthPercent <= 0.50){
            setState(CastleState.SEMIDAMAGED);
        }
        else if(healthPercent <= 0.75){
            setState(CastleState.DAMAGED);
        }
        else{
            setState(CastleState.HEALTY);
        }
    }    
    public void updateStats(){
        this.elixer.set(BASE_ELIXER + (ELIXER_UPGRADE_PER_LEVEL * getElixerLevel()));
        this.health.set(BASE_HEALTH + (HEALTH_UPGRADE_PER_LEVEL * getHealthLevel()));
        setState(CastleState.HEALTY);
    }
    
}

