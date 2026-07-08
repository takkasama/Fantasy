package cr.ac.una.fantasydefender.model;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.io.Serializable;


/**
 *
 * @author chela
 */
@Entity
@Table(name = "FANDE_GAME" , schema = "una")
@NamedQueries({
    @NamedQuery(name = "Game.findAll", query = "SELECT g FROM Game g"),
    @NamedQuery(name = "Game.findById", query = "SELECT g FROM Game g WHERE g.id = :id"),
})
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "FANDE_GAME_EMP_ID_GENERATOR", sequenceName = "una.FANDE_GAME_SQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FANDE_GAME_EMP_ID_GENERATOR")       
    @Basic(optional = false)
    @Column(name = "GM_ID")
    private Long id;
    
    @Basic(optional = false)
    @Column(name = "GM_LEVEL")
    private int level;
    
    @Basic(optional = false)
    @Column(name = "GM_DIFFICULTY")
    private char difficulty;
    
    @Basic(optional = false)
    @Column(name = "GM_POINTS")
    private int points;
    
    @Basic(optional = false)
    @Column(name = "GM_NAME")
    private String name;
    
    @Basic(optional = false)
    @Column(name = "GM_ICE_LEVEL")
    private int iceLevel;
    
    @Basic(optional = false)
    @Column(name = "GM_METEOR_LEVEL")
    private int meteorLevel;
    
    @Version
    @Column(name = "GM_VERSION")
    private Long version;
    
    @JoinColumn(name = "CTL_ID", referencedColumnName = "CTL_ID")
    @OneToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Castle castle;
    
    @JoinColumn(name = "CRB_ID", referencedColumnName = "CRB_ID")
    @OneToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Crossbow crossbow;
    
    @JoinColumn(name = "PLY_ID", referencedColumnName = "PLY_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Player player;

    public Game() {
    }
    
    public Game(GameDTO gameDTO){
        this.id = gameDTO.getId();
        update(gameDTO);
    }
    
    public void update(GameDTO gameDTO){
        this.level = gameDTO.getLevel();
        this.points = gameDTO.getPoints();
        this.name = gameDTO.getName();
        this.iceLevel = gameDTO.getIceLevel();
        this.meteorLevel = gameDTO.getMeteorLevel();
        setDifficulty(gameDTO.getDifficulty());
        
        this.castle = new Castle(gameDTO.getCastle());
        this.crossbow = new Crossbow(gameDTO.getCrossbow());
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public double getDifficulty() {
        switch (difficulty) {
            case 'E': return 0.7;
            case 'N': return 1.2;
            case 'H': return 1.6;
            case 'B': return 2.3;
            default: return 1.2;
        }
    }
    public void setDifficulty(double difficulty) { 
       
        if (difficulty == 0.7) 
            this.difficulty = 'E';
        
        else if (difficulty == 1.6) 
            this.difficulty = 'H';
        
         else if (difficulty == 2.3) 
            this.difficulty = 'B';
         
         else 
            this.difficulty = 'N'; 
        
    }
    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getIceLevel() { return iceLevel; }
    public void setIceLevel(int iceLevel) { this.iceLevel = iceLevel; }

    public int getMeteorLevel() { return meteorLevel; }
    public void setMeteorLevel(int meteorLevel) { this.meteorLevel = meteorLevel; }

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }

    public Castle getCastle() { return castle; }
    public void setCastle(Castle castle) { this.castle = castle; }

    public Crossbow getCrossbow() { return crossbow; }
    public void setCrossbow(Crossbow crossbow) { this.crossbow = crossbow; }

    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Game)) {
            return false;
        }
        Game other = (Game) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.fantasydefender.model.Game[ gpId=" + id + " ]";
    }
    
}
