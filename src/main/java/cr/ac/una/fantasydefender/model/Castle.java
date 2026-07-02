package cr.ac.una.fantasydefender.model;

import java.io.Serializable;
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

/**
 *
 * @author Takkasama
 */
@Entity
@Table(name = "FANDE_CASTLE", schema = "una")
@NamedQueries({
    @NamedQuery(name = "Castle.findAll", query = "SELECT c FROM Castle c"),
    @NamedQuery(name = "Castle.findById", query = "SELECT c FROM Castle c WHERE c.id = :id")})
public class Castle implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "FANDE_CASTLE_EMP_ID_GENERATOR", sequenceName = "una.FANDE_CASTLE_SQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FANDE_CASTLE_EMP_ID_GENERATOR")     
    @Basic(optional = false)
    @Column(name = "CTL_ID")
    private Long id;
    
    @Basic(optional = false)
    @Column(name = "CTL_ELIXER_LVL")
    private int elixerLvl;
    
    @Basic(optional = false)
    @Column(name = "CTL_HEALTH_LVL")
    private int healthLvl;
    
    @Version
    @Column(name = "CTL_VERSION")
    private Long version;
    

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "castle", fetch = FetchType.LAZY)
    private Game game;

    public Castle() {
    }

    public Castle(CastleDTO castleDTO){
        this.id = castleDTO.getId();
        update(castleDTO);
    }

    public void update(CastleDTO castleDTO){
        this.elixerLvl = castleDTO.getElixerLevel();
        this.healthLvl = castleDTO.getHealthLevel();
        this.version = castleDTO.getVersion();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getElixerLvl() { return elixerLvl; }
    public void setElixerLvl(int elixerLvl) { this.elixerLvl = elixerLvl; }

    public int getHealthLvl() { return healthLvl; }
    public void setHealthLvl(int healthLvl) { this.healthLvl = healthLvl; }

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }

    public Game getGame() { return game; }
    public void setGame(Game game) { this.game = game;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Castle)) {
            return false;
        }
        Castle other = (Castle) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.fantasydefender.model.Castle[ ctlId=" + id + " ]";
    }
    
}







