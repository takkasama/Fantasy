package cr.ac.una.fantasydefender.model;

/**
 *
 * @author Takkasama
 */
public abstract class SpecialAttack {
    
    private double x;
    private double y;
    
    private double radio;    
    private double coolDownTime;
    
    private int level;
    
    public SpecialAttack() {
        this.x =0;
        this.y =0;
        this.level =0;
        this.coolDownTime =0;
        this.radio =0;
    }
    
    protected abstract void defineSpecialAttack();

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public double getRadio() { return radio; }
    public void setRadio(double radio) { this.radio = radio; }

    public double getCoolDownTime() { return coolDownTime; }
    public void setCoolDownTime(double coolDownTime) { this.coolDownTime = coolDownTime; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
    
}
