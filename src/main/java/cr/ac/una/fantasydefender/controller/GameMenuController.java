package cr.ac.una.fantasydefender.controller;

import cr.ac.una.fantasydefender.model.GameDTO;
import cr.ac.una.fantasydefender.model.PlayerDTO;
import cr.ac.una.fantasydefender.service.GameService;
import cr.ac.una.fantasydefender.util.AppContext;
import cr.ac.una.fantasydefender.util.DataNotifier;
import cr.ac.una.fantasydefender.util.FlowController;
import cr.ac.una.fantasydefender.util.Mensaje;
import cr.ac.una.fantasydefender.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 *
 * @author Takkasama
 */
public class GameMenuController extends Controller implements Initializable, DataNotifier.Listener {

   @FXML
    private VBox root;
    @FXML
    private Label lblPlayer;
    @FXML
    private ListView<GameDTO> ltParties;
    @FXML
    private VBox vBoxVizualizer;
    @FXML
    private MFXButton btnPlay;
    @FXML
    private MFXButton btnUpgrade;
    @FXML
    private MFXButton btnSettings;
    @FXML
    private MFXButton btnMenu;
    @FXML
    private MFXButton btnInfo;
    @FXML
    private MFXButton btnNewGame;
    
    private PlayerDTO player;
    @FXML
    private MFXButton btnHide;
    private Label lblParties;
    private boolean isHide;
    @FXML
    private VBox vBoxParites;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DataNotifier.subscribe(this);
        loadView();
    }    
    @Override
    public void initialize() {
    }
    
    @Override
    public void onDataChanged(String event) {
        if(event.equals("newGame"))    
            loadView();
        if(event.equals("updateGame")){
            GameDTO updateGame = (GameDTO) AppContext.getInstance().get("SelectedGame");
             saveGame(updateGame);
             loadView();
        }
        
    }
    
    @FXML
    private void onActionBtnNewGame(ActionEvent event) {
        FlowController.getInstance().goViewInPane("NewGameView", vBoxVizualizer, false);
    }

    @FXML
    private void onActionBtnPlay(ActionEvent event) {
        GameDTO game = ltParties.getSelectionModel().getSelectedItem();
        if(game == null)
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "onActionBtnPlay", getStage(), "Please Select some game");
        else{
            AppContext.getInstance().set("SelectedGame", game);
            FlowController.getInstance().goViewInStage("GameView", getStage());
        }
    }

    @FXML
    private void onActionBtnUpgrade(ActionEvent event) {
        FlowController.getInstance().goViewInPane("UpgradeView", vBoxVizualizer, false);
    }

    @FXML
    private void onActionBtnInfo(ActionEvent event) {
        FlowController.getInstance().goViewInPane("InfoGameView", vBoxVizualizer, false);
    }

    @FXML
    private void onActionBtnSettings(ActionEvent event) {
        FlowController.getInstance().goViewInPane("SettingsView", vBoxVizualizer, false);
    }

    @FXML
    private void onActionBtnMenu(ActionEvent event) {
        FlowController.getInstance().goViewInStage("MainView", getStage());
    }
    
    @FXML
    private void onMouseClickedLtParties(MouseEvent event) {
        if(event.getClickCount() == 2){
            GameDTO selectedGame = ltParties.getSelectionModel().getSelectedItem();
            if(selectedGame == null) return;
            
            AppContext.getInstance().set("SelectedGame", selectedGame);
            DataNotifier.notifyChange("newGameSelected");
        }
    }    
    
    @FXML
    private void onActionBtnHide(ActionEvent event) {
        if(!isHide){
            vBoxParites.setManaged(isHide);
            vBoxParites.setVisible(isHide);
            
            isHide = !isHide;
        }else{
            vBoxParites.setManaged(isHide);
            vBoxParites.setVisible(isHide);
            
            isHide = !isHide;
        }
    }


    
    private void loadView(){
        this.player = (PlayerDTO)AppContext.getInstance().get("Player");
        this.isHide = false; 
        
         lblPlayer.setText("Welcome " + player.getName());
         ltParties.setItems(player.getGamesListObservable());       
         ltParties.setCellFactory((p) -> new ButtonCell());
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
    
    private void  cleanView(){
        AppContext.getInstance().set("Player", null);
        this.ltParties.getItems().clear();
    }


    
    
  private class ButtonCell extends ListCell<GameDTO> {

        final Button cellButton = new Button();
        final Label label = new Label();               
        final VBox vBox = new VBox(10);               

        public ButtonCell() {
           
            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().addAll(label, cellButton);
            vBox.getStyleClass().add("jfx-title-label-4");
            
            vBox.setPadding(new Insets(30));
            
            cellButton.setPrefWidth(72);
            cellButton.getStyleClass().add("jfx-cBTrash");
            cellButton.setOnAction((t) -> {
                if(!new Mensaje().showConfirmation("RemoveGame", getStage(), "Are you Shure?")) return;
                GameDTO game = ButtonCell.this.getListView().getItems().get(ButtonCell.this.getIndex());

                if(removeGame(game)){
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
     
                     String text = name.toUpperCase().toUpperCase() + "\n Level : " + game.getLevel() + "" ;

                     label.setText(text);
                     setGraphic(vBox);
                 }
             }
    
    }

}
