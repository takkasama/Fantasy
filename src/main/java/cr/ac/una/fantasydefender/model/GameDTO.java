package cr.ac.una.fantasydefender.model;

import javafx.beans.property.*;

/**
 *
 * @author TakkaSama
 */
public class GameDTO {
    
    public enum GameResult{
        VICTORY,
        DEFEAT,
        NONE
    }
    public enum GameState{
        PAUSED, 
        ACTIVE,
        RESTARTED,
        NEXT_LEVEL,
        EXITING
    }
    
    private final static double EASY_DIFFICULTY = 0.7;
    private final static double NORMAL_DIFFICULTY = 1.2;
    private final static double HARD_DIFFICULTY = 1.6;
    private final static double BRUTALITY_DIFFICULTY = 2.3;
    
    
    private ObjectProperty<GameResult> gameResult;
    private ObjectProperty<GameState> gameState;
    
    private StringProperty id;
    private StringProperty name;
    private IntegerProperty points;
    private IntegerProperty level;
    private IntegerProperty meteorLevel;
    private IntegerProperty iceLevel;
    private DoubleProperty difficulty;   
    
    private ObjectProperty<CastleDTO> castle;
    private ObjectProperty<CrossbowDTO> crossbow;
    private ObjectProperty<PlayerDTO> player;
    
    private Long version;

    public GameDTO() {
        this.gameResult = new SimpleObjectProperty(GameResult.NONE);
        this.gameState = new SimpleObjectProperty<>(GameState.ACTIVE);
        this.id = new SimpleStringProperty();
        this.level = new SimpleIntegerProperty(1);
        this.difficulty = new SimpleDoubleProperty(1.2);
        this.points = new SimpleIntegerProperty(0);
        this.name = new SimpleStringProperty("");
        this.meteorLevel = new SimpleIntegerProperty(1);
        this.iceLevel = new SimpleIntegerProperty(1);
        this.castle = new SimpleObjectProperty<>();
        this.crossbow = new SimpleObjectProperty<>();
        this.player = new SimpleObjectProperty<>();
    }
    
    public GameDTO(Game game){
        this();
        this.id.set(game.getId().toString());
        this.points.set(game.getPoints());
        this.name.set(game.getName());
        this.level.set(game.getLevel());
        this.difficulty.set(game.getDifficulty());
        this.meteorLevel.set(game.getMeteorLevel());
        this.iceLevel.set(game.getIceLevel());
        
        this.castle.set(new CastleDTO(game.getCastle()));
        this.crossbow.set( new CrossbowDTO(game.getCrossbow()));
        
        this.version = game.getVersion();
    }

    public Long getId() { 
        if(id.get() == null || id.get().isBlank()) return null; 
        return Long.valueOf(id.get()); 
    }
    
    public GameResult getGameResult(){ return gameResult.get(); }
    public void setGameResult(GameResult gameState){ this.gameResult.set(gameState); }
    
    public GameState getGameState(){ return gameState.get(); }
    public void setGameState(GameState gameState) { this.gameState.set(gameState); }

    public int getPoints() { return points.get(); }
    public void setPoints(int points) { this.points.set(points); }
    
    public String getName() { return name.get(); }
    public void setName(String name) { this.name.set(name); }

    public int getLevel() { return level.get(); }
    public void setLevel(int level) { this.level.set(level); }

    public double getDifficulty(){ return difficulty.get(); }
    public void setDifficulty(double difficulty) { this.difficulty.set(difficulty); }
    
    public int getMeteorLevel() { return meteorLevel.get(); }
    public void setMeteorLevel(int meteorLevel) { this.meteorLevel.set(meteorLevel); }

    public int getIceLevel() { return iceLevel.get(); }
    public void setIceLevel(int iceLevel) { this.iceLevel.set(iceLevel); }

    public CastleDTO getCastle() { return castle.get(); }
    public void setCastle(CastleDTO castle) { this.castle.set(castle); }

    public CrossbowDTO getCrossbow() { return crossbow.get(); }
    public void setCrossbow(CrossbowDTO crossbow) { this.crossbow.set(crossbow); }

    public PlayerDTO getPlayer() { return player.get(); }
    public void setPlayer(PlayerDTO player) { this.player.set(player); }

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }

    public static double getEASY_DIFFICULTY() { return EASY_DIFFICULTY; }

    public static double getNORMAL_DIFFICULTY() { return NORMAL_DIFFICULTY; }

    public static double getHARD_DIFFICULTY() { return HARD_DIFFICULTY; }

    public static double getBRUTALITY_DIFFICULTY() { return BRUTALITY_DIFFICULTY; }




    public StringProperty getIdProperty() { return id; }
    
    public ObjectProperty<GameResult> getGameResultProperty(){ return gameResult; }
    
    public ObjectProperty<GameState> getGameStateProperty() { return gameState; }

    public IntegerProperty getPointsProperty() { return points; }

    public StringProperty getNameProperty() { return name; }
    
    public IntegerProperty getLevelProperty() { return level; }
    
    public DoubleProperty getDifficultyProperty() { return difficulty; }

    public ObjectProperty<CastleDTO> getCastleProperty() { return castle; }

    public ObjectProperty<CrossbowDTO> getCrossbowProperty() { return crossbow; }

    public ObjectProperty<PlayerDTO> getPlayerProperty() { return player; }

    public IntegerProperty getMeteorLevelProperty() { return meteorLevel; }

    public IntegerProperty getIceLevelProperty() { return iceLevel; }
    
    

}
