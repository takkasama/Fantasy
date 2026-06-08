package cr.ac.una.fantasydefender.model;

import javafx.beans.property.*;

/**
 *
 * @author Takkasama
 */
public class CrossbowDTO {
    
    private final static double  BASE_DAMAGE = 5.0;
    private final static double DAMAGE_UPGRADE_PER_LEVEL = 2.0;
    
    private final static double BASE_FRECUENCY =1.0;
    private static final double FRECUENCY_REDUCTION_PER_LEVEL = 0.05;
    private static final double MIN_FRECUENCY = 0.1;    
 
    private StringProperty id;
    private IntegerProperty damageLvl;
    private IntegerProperty frecuencyLvl; 
    
    private DoubleProperty damage;
    private DoubleProperty frecuency;
    
    private StringProperty crossbowSelected;
        
    private Long version;
    
    public CrossbowDTO(){
        this.id = new SimpleStringProperty("");
        this.damageLvl = new SimpleIntegerProperty(1);
        this.frecuencyLvl = new SimpleIntegerProperty(1);
        
        this.damage = new SimpleDoubleProperty(BASE_DAMAGE);
        this.frecuency = new SimpleDoubleProperty(BASE_FRECUENCY);
        
        this.crossbowSelected = new SimpleStringProperty("D");
    }

    public CrossbowDTO(Crossbow crossbow){
        this();
        this.id.set(crossbow.getId().toString());
        this.damageLvl.set(crossbow.getDamageLvl());
        this.frecuencyLvl.set(crossbow.getFrecuencyLvl());
        this.crossbowSelected.set(crossbow.getCrossbowSelected());
        this.version = crossbow.getVersion();

        updateStats();
     }
    
    public Long getId() { 
        if(id.get() == null || id.get().isBlank())
            return null;
        return Long.valueOf(id.get());
    }

    public int getDamageLvl() { return damageLvl.get(); }
    public void setDamageLvl(int damageLvl) { this.damageLvl.set(damageLvl); }

    public int getFrecuencyLvl() { return frecuencyLvl.get(); }
    public void setFrecuencyLvl(int frecuencyLvl) { this.frecuencyLvl.set(frecuencyLvl); }

    public double getDamage() { return damage.get(); }
    public void setDamage(double damage) { this.damage.set(damage); }
    
    public String getCrossbowSelected() { return crossbowSelected.get(); }
    public void setCrossbowSelected(String crossbowSelected) { this.crossbowSelected.set(crossbowSelected); }

    public double getFrecuency() { return frecuency.get(); }
    public void setFrecuency(double frecuency) { this.frecuency.set(frecuency); }
    
    public Long getVersion(){ return version; }
    public void setVersion(Long version){ this.version = version; }

    public StringProperty getIdProperty() { return id; }

    public IntegerProperty getDamageLvlProperty() { return damageLvl; }

    public IntegerProperty getFrecuencyLvlProperty() { return frecuencyLvl; }
    
    public StringProperty getCrossbowSelectedProperty() { return crossbowSelected; }

    public DoubleProperty getDamageProperty() { return damage; }

    public DoubleProperty getFrecuencyProperty() { return frecuency; } 
    
    public void updateStats(){
        this.frecuency.set(Math.max(MIN_FRECUENCY, BASE_FRECUENCY - (getFrecuencyLvl() * FRECUENCY_REDUCTION_PER_LEVEL)));
        this.damage.set(BASE_DAMAGE + (DAMAGE_UPGRADE_PER_LEVEL * getDamageLvl()));
    }  
    
}
