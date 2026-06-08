package cr.ac.una.fantasydefender.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.util.ArrayList;

/**
 *
 * @author TakkaSama
 */
@Entity
@Table(name = "FANDE_PLAYER", schema = "una")
@NamedQueries({
    @NamedQuery(name = "Player.findAll", query = "SELECT p FROM Player p"),
    @NamedQuery(name = "Player.findById", query = "SELECT p FROM Player p WHERE p.id = :id"),
    @NamedQuery(name = "Player.findByNameOrEmail", query = "SELECT p FROM Player p WHERE p.email = :email OR p.name = :name")
})
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "FANDE_PLAYER_EMP_ID_GENERATOR", sequenceName = "una.FANDE_PLAYERS_SQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FANDE_PLAYER_EMP_ID_GENERATOR")    
    @Column(name = "PLY_ID")
    private Long id;
    
    @Basic(optional = false)
    @Column(name = "PLY_NAME")
    private String name;
    
    @Basic(optional = false)
    @Column(name = "PLY_EMAIL")
    private String email;
    
    @Basic(optional = false)
    @Column(name = "PLY_PASSWORD")
    private String password;
    
    @Basic(optional = false)
    @Column(name = "PLY_REGISTER_DATE")
    private LocalDate registerDate;
    
    @Lob
    @Column(name = "PLY_AVATAR")
    private byte[] avatar;
    
    @Column(name = "PLY_AVATAR_TYPE")
    private String avatarType;
    
    @Version
    @Column(name = "PLY_VERSION")
    private Long version;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "player", fetch = FetchType.LAZY)
    private List<Game> gameList = new ArrayList<>();

    public Player() {}
    public Player(PlayerDTO playerDTO) {
        this.id = playerDTO.getId();
        update(playerDTO);
    }
    
    public void update(PlayerDTO playerDTO){
       this.name = playerDTO.getName();
       this.email = playerDTO.getEmail();
       this.password =playerDTO.getPassword();
       this.registerDate = playerDTO.getRegisterDate();
       this.avatar = playerDTO.getAvatar();
       this.avatarType = playerDTO.getAvatarType();
       this.version = playerDTO.getVersion();
       
       if(playerDTO.getGameList() != null)
          for(GameDTO gm : playerDTO.getGameList())
               gameList.add(new Game(gm));
    }

    public Long getId() { return id; } 
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; } 
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; } 
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public LocalDate getRegisterDate() { return registerDate; } 
    public void setRegisterDate(LocalDate registerDate) { this.registerDate = registerDate; }

    public byte[] getAvatar() { return avatar; } 
    public void setAvatar(byte[] avatar) { this.avatar = avatar; }

    public String getAvatarType() { return avatarType; } 
    public void setAvatarType(String avatarType) { this.avatarType = avatarType; }

    public Long getVersion() { return version; } 
    public void setVersion(Long version) { this.version = version; }

    public List<Game> getGameList() { return gameList; } 
    public void setGameList(List<Game> gameList) { this.gameList = gameList; }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Player)) {
            return false;
        }
        Player other = (Player) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.fantasydefender.model.Player[ plyId=" + id + " ]";
    }
    
}