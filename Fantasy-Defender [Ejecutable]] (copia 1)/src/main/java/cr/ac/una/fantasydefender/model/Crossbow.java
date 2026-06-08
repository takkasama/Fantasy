package cr.ac.una.fantasydefender.model;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.io.Serializable;

/**
 *
 * @author Takkasama
 */
@Entity
@Table(name = "FANDE_CROSSBOW", schema = "una")
@NamedQueries({
    @NamedQuery(name = "Crossbow.findAll", query = "SELECT c FROM Crossbow c"),
    @NamedQuery(name = "Crossbow.findById", query = "SELECT c FROM Crossbow c WHERE c.id = :id")})

public class Crossbow implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "FANDE_CROSSBOW_EMP_ID_GENERATOR", sequenceName = "una.FANDE_CROSSBOW_SQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FANDE_GAME_EMP_ID_GENERATOR")          
    @Basic(optional = false)
    @Column(name = "CRB_ID")
    private Long id;
    
    @Basic(optional = false)
    @Column(name = "CRB_DAMAGE_LVL")
    private int damageLvl;
    
    @Basic(optional = false)
    @Column(name = "CRB_FRECUENCY_LVL")
    private int frecuencyLvl;
    
    @Version
    @Column(name = "CRB_VERSION")
    private Long version;
    
    @Basic(optional = false)
    @Column(name = "CBR_CROSSBOW_SELECT")
    private String crossbowSelect;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "crossbow", fetch = FetchType.LAZY)
    private Game game;

     public Crossbow() {
    }
    
    public Crossbow(CrossbowDTO crossbowDTO){
        this.id = crossbowDTO.getId();
        update(crossbowDTO);
    }
    
    public void update(CrossbowDTO crossbowDTO){
        this.damageLvl = crossbowDTO.getDamageLvl();
        this.frecuencyLvl = crossbowDTO.getFrecuencyLvl();
        this.crossbowSelect = crossbowDTO.getCrossbowSelected();
        this.version = crossbowDTO.getVersion();
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getDamageLvl() { return damageLvl; }
    public void setDamageLvl(int damageLvl) { this.damageLvl = damageLvl; }

    public int getFrecuencyLvl() { return frecuencyLvl; }
    public void setFrecuencyLvl(int frecuencyLvl) { this.frecuencyLvl = frecuencyLvl; }

    public String getCrossbowSelected() { return crossbowSelect; }
    public void setCrossbowSelected(String crossbowSelected) { this.crossbowSelect = crossbowSelected; }

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }

    public Game getGame() { return game; }
    public void setGame(Game game) { this.game = game; }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Crossbow)) {
            return false;
        }
        Crossbow other = (Crossbow) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.fantasydefender.model.Crossbow[ crbId=" + id + " ]";
    }
    
}
