package cr.ac.una.fantasydefender.controller;

import cr.ac.una.fantasydefender.model.CastleDTO;
import cr.ac.una.fantasydefender.model.CrossbowDTO;
import cr.ac.una.fantasydefender.model.GameDTO;
import cr.ac.una.fantasydefender.model.PlayerDTO;
import cr.ac.una.fantasydefender.service.CastleService;
import cr.ac.una.fantasydefender.service.CrossbowService;
import cr.ac.una.fantasydefender.service.GameService;
import cr.ac.una.fantasydefender.util.AppContext;
import cr.ac.una.fantasydefender.util.FlowController;
import cr.ac.una.fantasydefender.util.Mensaje;
import cr.ac.una.fantasydefender.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author andys
 */
public class GameMenuController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private BorderPane childProjector;
    @FXML
    private VBox vBox;
    @FXML
    private MFXButton settingsButton;
    @FXML
    private MFXButton campaignButton;
    @FXML
    private MFXButton leaveButton;
    @FXML
    private MFXButton newCampaignButton;
    @FXML
    private ListView<GameDTO> campaignListView;
    @FXML
    private Label playerNameLabel;
    @FXML
    private Label selectedGameLabel;

    private PlayerDTO player;
    
    private GameDTO newGame;
    private ObjectProperty<GameDTO> newGameProperty = new SimpleObjectProperty<>();
    @FXML
    private MFXButton upgradeButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.newGame = new GameDTO();
        this.newGameProperty.set(newGame);
        
        loadView();
        loadNewGame();
    }    
    @Override
    public void initialize() {
        setNombreVista("Game Menu");
    }

    @FXML
    private void onActionCampaignButton(ActionEvent event) {
        if(!isChildState()){
            GameDTO game = campaignListView.getSelectionModel().getSelectedItem();
            
            if(game != null){
                AppContext.getInstance().set("SelectedGame", game);
                FlowController.getInstance().goMain("Game");
            }
            else
                new Mensaje().showModal(Alert.AlertType.WARNING, "onActionCampaignButton", getStage(), "Please Selectec game or create a new Game");
            
        } 
    }

    @FXML
    private void onActionSettingsButton(ActionEvent event) {
        if(!isChildState()){
                GameDTO game = campaignListView.getSelectionModel().getSelectedItem();

                if(game != null)
                    AppContext.getInstance().set("SelectedGame", game);     
                
            FlowController.getInstance().goViewPane("SettingsView",childProjector); 
            showChildView();
        } 
    }

    @FXML
    private void onActionLeaveButton(ActionEvent event) {
        cleanView();
        FlowController.getInstance().goMain("Main");
    }

    @FXML
    private void onActionNewCampaignButton(ActionEvent event) {
        FlowController.getInstance().goViewPane("MakeCampaignView", childProjector);
        showChildView();

        newGame = (GameDTO) AppContext.getInstance().get("NewGame");
        if(newGame != null){
            newGameProperty.set(null);
            newGameProperty.set(newGame);
        }
    }
    

    @FXML
    private void onActionUpgradeButton(ActionEvent event) {
        if(!isChildState()){
                GameDTO game = campaignListView.getSelectionModel().getSelectedItem();
                if(game == null){
                    new Mensaje().showModal(Alert.AlertType.WARNING, "upgradeButton", getStage(), "Please Select a game");
                    return;
                }
               AppContext.getInstance().set("SelectedGame", game);
               FlowController.getInstance().goViewPane("UpgradeView", childProjector);
        }        
    }
    
    
        
    private void loadNewGame(){
        
        newGameProperty.addListener((obs, oldValue, newValue) -> {
            if(newValue != null){
                  GameDTO saved = saveGame(newGame);
                if(saved != null){
                    player.getGamesListObservable().add(saved);
                    campaignListView.getItems().add(saved);
                }      
            }
        });
    }
    
    

    private void loadView(){
        newCampaignButton.setText("");
        campaignButton.setText("");
        settingsButton.setText("");
        leaveButton.setText("");
        upgradeButton.setText("");
        
        childState = false;
        setChildBorderPane(childProjector);
        
        this.player = (PlayerDTO)AppContext.getInstance().get("Player");
        
        playerNameLabel.setText("WELCOME : " + player.getName());
          
         campaignListView.setItems(player.getGamesListObservable());       
         campaignListView.setCellFactory((p) -> new ButtonCell());

          campaignListView.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue)->{
              if(newValue != null){
                selectedGameLabel.setText(newValue.getName());
              }
          });

    }

    private GameDTO saveGame(GameDTO game){
        
        try{
            GameService gameService =  new GameService();            
            Respuesta res = gameService.saveGame(game);
            if(res.getEstado())
              return (GameDTO) res.getResultado("Game");            
            else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "SaveGame", getStage(), "Error to save the game");
                return null;
            }
            
        }catch (Exception e) {
            Logger.getLogger(GameMenuController.class.getName()).log(Level.SEVERE, "Error to save the Game", e);
            new Mensaje().showModal(Alert.AlertType.ERROR, "SaveGame", getStage(), "Error to save the Game");
        }
        return null;
    }
    

    private boolean removeGame(GameDTO game){
        try{
            if(game.getId() != null){
                GameService gameService = new GameService();

                Respuesta res = gameService.removeGame(game);
                if(res.getEstado()){
                    return true;
                }
                else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "removeGame", getStage(), res.getMensaje());           
                    return false;
                  }
            }
        }catch(Exception e){
            Logger.getLogger(GameMenuController.class.getName()).log(Level.SEVERE, "Error to remove the Game", e);
            new Mensaje().showModal(Alert.AlertType.ERROR, "RemoveGame", getStage(), "Error to remove the Game");
        }
        return false;
        
    }
    
    private boolean removeCrossbow(CrossbowDTO crossbowDTO){
      try{
                if(crossbowDTO.getId() != null){
                    CrossbowService crossbowService = new CrossbowService();

                    Respuesta res = crossbowService.removeCrossbow(crossbowDTO);
                    if(res.getEstado()){
                        return true;
                    }
                    else {
                        new Mensaje().showModal(Alert.AlertType.ERROR, "removeCrossbow", getStage(), res.getMensaje());           
                      }
                }
            }catch(Exception e){
                Logger.getLogger(GameMenuController.class.getName()).log(Level.SEVERE, "Error to remove the Crossbow", e);
                new Mensaje().showModal(Alert.AlertType.ERROR, "RemoveCrossbow", getStage(), "Error to remove the Crossbow");
            }    
      return false;
    }
    
    
    private boolean removeCastle(CastleDTO castleDTO){
      try{
                if(castleDTO.getId() != null){
                    CastleService castleService = new CastleService();

                    Respuesta res = castleService.removeCastle(castleDTO);
                    if(res.getEstado()){
                        return true;
                    }
                    else {
                        new Mensaje().showModal(Alert.AlertType.ERROR, "removeCastle", getStage(), res.getMensaje());           
                      }
                }
            }catch(Exception e){
                Logger.getLogger(GameMenuController.class.getName()).log(Level.SEVERE, "Error to remove the Castle", e);
                new Mensaje().showModal(Alert.AlertType.ERROR, "RemoveCastle", getStage(), "Error to remove the Castle");
            }    
      return false;
    }
     
    
    
    private void  cleanView(){
        AppContext.getInstance().set("Player", null);
        this.campaignListView.getItems().clear();
        this.selectedGameLabel.setText("");
        this.playerNameLabel.setText("");
    }

  private class ButtonCell extends ListCell<GameDTO> {

        final Button cellButton = new Button();
        final Label label = new Label();               
        final HBox hbox = new HBox(10);               
        final Region region = new Region();

        public ButtonCell() {
            HBox.setHgrow(region, Priority.ALWAYS);
            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.getChildren().addAll(label,region, cellButton);
            hbox.getStyleClass().add("jfx-title-label-4");
            
            hbox.setPadding(new Insets(30));
            
            cellButton.setPrefWidth(72);
            cellButton.getStyleClass().add("jfx-cBTrash");
            cellButton.setOnAction((t) -> {
                if(!new Mensaje().showConfirmation("RemoveGame", getStage(), "Are you Shure?")) return;
                GameDTO game = ButtonCell.this.getListView().getItems().get(ButtonCell.this.getIndex());

                if(removeGame(game) && removeCastle(game.getCastle()) && removeCrossbow(game.getCrossbow())){
                    player.getGameList().remove(game);
                    player.getGamesListObservable().remove(game);

                    ButtonCell.this.getListView().getItems().remove(game);                      
                }
                

            });
            
        }

            @Override
             protected void updateItem(GameDTO game, boolean empty) {      
                 super.updateItem(game, empty);
                 
                 if(empty || game == null){
                     setText(null);
                     setGraphic(null);
                 }
                 
                 else{
                     String name = game.getName() != null ? game.getName() : "NO NAME";
                     String difficultyName;
                     if(GameDTO.getEASY_DIFFICULTY() == game.getDifficulty()) 
                         difficultyName = "EASY";
                     else if(GameDTO.getHARD_DIFFICULTY() == game.getDifficulty()) 
                         difficultyName = "HARD";
                     else if(GameDTO.getBRUTALITY_DIFFICULTY() == game.getDifficulty()) 
                         difficultyName = "BRUTALITY";
                     else
                          difficultyName = "NORMAL";
                     String text = name.toUpperCase().toUpperCase() + " \n Difficulty : "  + difficultyName + "\t Points : " +game.getPoints() + "\t Level : " + game.getLevel() ;

                     label.setText(text);
                     setGraphic(hbox);
                 }
             }
    
    }

}
