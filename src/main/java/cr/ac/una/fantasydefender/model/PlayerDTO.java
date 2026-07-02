package cr.ac.una.fantasydefender.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Takkasama
 */
public class PlayerDTO {
    private StringProperty id;
    private StringProperty name;
    private StringProperty email;
    private StringProperty password;
    private ObjectProperty<LocalDate> registerDate;
    private List<GameDTO> gamesList;
    
    private String avatarType;
    private byte[] avatar;
    private Long version;
    
    public PlayerDTO(){
        this.id = new SimpleStringProperty("");
        this.name = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");
        this.password = new SimpleStringProperty("");
        this.registerDate = new SimpleObjectProperty<>(LocalDate.now());
        this.gamesList = new ArrayList<>();
    }

    public PlayerDTO(Player player){
        this();
        this.id.set(player.getId().toString());
        this.name.set(player.getName());
        this.email.set(player.getEmail());
        this.password.set(player.getPassword());
        this.registerDate.set(player.getRegisterDate());
        this.avatar = player.getAvatar();
        this.avatarType = player.getAvatarType();
        this.version = player.getVersion();
        
        if(player.getGameList() != null)
            for(Game gm : player.getGameList())
                gamesList.add(new GameDTO(gm));
            
        
    }
    public Long getId() { 
        if(id == null || id.get().isBlank()) return null;
        return Long.valueOf(id.get());
    }

    public String getName() { return name.get(); }
    public void setName(String name) { this.name.set(name); }

    public String getPassword() { return password.get(); }
    public void setPassword(String password) { this.password.set(password); }

    public String getEmail() { return email.get(); }
    public void setEmail(String email) { this.email.set(email); }

    public LocalDate getRegisterDate() { return registerDate.get(); }
    public void setRegisterDate(LocalDate registerDate) { this.registerDate.set(registerDate); }
    
    public List<GameDTO> getGameList(){ return gamesList; }
    public void setGameList(List<GameDTO> gameList) { this.gamesList = gameList; }

    public String getAvatarType() { return avatarType; }
    public void setAvatarType(String avatarType) { this.avatarType = avatarType; }

    public byte[] getAvatar() { return avatar; }
    public void setAvatar(byte[] avatar) { this.avatar = avatar; }

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }
    
    
    
    public StringProperty getIdProperty() { return id; }

    public StringProperty getNameProperty() { return name; }

    public StringProperty getEmailProperty() { return email; }

    public StringProperty getPasswordProperty() { return password; }

    public ObjectProperty<LocalDate> getRegisterDateProperty() { return registerDate; }

    public ObservableList<GameDTO> getGamesListObservable() { 
        ObservableList<GameDTO> gameListObservable = FXCollections.observableArrayList();
        gameListObservable.addAll(gamesList);
        return gameListObservable;
    }
    
        
}
