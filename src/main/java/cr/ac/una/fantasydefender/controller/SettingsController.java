package cr.ac.una.fantasydefender.controller;

import cr.ac.una.fantasydefender.model.GameDTO;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author andys
 */
public class SettingsController extends Controller implements Initializable {

    @FXML
    private MFXButton easyButton;
    @FXML
    private MFXButton hardButton;
    @FXML
    private MFXButton normalButton;
    @FXML
    private MFXButton brutalButton;
    @FXML
    private Pane toggleOff;
    @FXML
    private Pane toggleOn;
    @FXML
    private MFXButton leaveButton;

    
    private boolean toggle;
    private GameDTO game;
    
    @Override    
    public void initialize(URL url, ResourceBundle rb) {
        easyButton.setText("");
        normalButton.setText("");
        hardButton.setText("");
        brutalButton.setText("");
        leaveButton.setText("");
        
        this.game  = (GameDTO) AppContext.getInstance().get("SelectedGame");
        
        if(game == null){
           easyButton.setDisable(true);
           normalButton.setDisable(true);
           hardButton.setDisable(true);
           brutalButton.setDisable(true);
        }else {
           easyButton.setDisable(false);
           normalButton.setDisable(false);
           hardButton.setDisable(false);
           brutalButton.setDisable(false);
        }
    
    }    

    @Override
    public void initialize() {
    }
    
    @FXML
    private void onMouseReleasedToggleOff(MouseEvent event) {
        if (toggleOff.isHover()) {//checks if the mouse is hovering, if it isn't the click release does nothing
            toggleAction(true);
        }
    }

    @FXML
    private void onMouseReleasedToggleOn(MouseEvent event) {
        if (toggleOn.isHover()) {//checks if the mouse is hovering, if it isn't the click release does nothing
            toggleAction(false);
        }
    }

    private void leaveButtonReleased(MouseEvent event) {
        if (leaveButton.isHover()) {//checks if the mouse is hovering, if it isn't the click release does nothing
            clean();
            getParent().hideChildView();
        }
    }
    private void toggleAction(boolean toggle){
        this.toggle = toggle;
        if(toggle){
            toggleOn.setVisible(true);
            toggleOn.setManaged(true);
            toggleOff.setVisible(false);
            toggleOff.setManaged(false);
        }else{
            toggleOn.setVisible(false);
            toggleOn.setManaged(false);
            toggleOff.setVisible(true);
            toggleOff.setManaged(true);
        }
    }

    @FXML
    private void onActionEasyButton(ActionEvent event) {
        this.game.setDifficulty(GameDTO.getEASY_DIFFICULTY());
    }

    @FXML
    private void onActionNormalButton(ActionEvent event) {
        this.game.setDifficulty(GameDTO.getNORMAL_DIFFICULTY());
    }

    @FXML
    private void onActionHardButton(ActionEvent event) {
        this.game.setDifficulty(GameDTO.getHARD_DIFFICULTY());
    }

    @FXML
    private void onActionBrutalButton(ActionEvent event) {
        this.game.setDifficulty(GameDTO.getBRUTALITY_DIFFICULTY());
    }

    @FXML
    private void onActionLeaveButton(ActionEvent event) {
        if(game != null) saveGame();
        getParent().hideChildView();
    }
    private void saveGame(){
        
        try{
            GameService gameService =  new GameService();            
            Respuesta res = gameService.saveGame(game);
            if(res.getEstado())
                AppContext.getInstance().set("SelectedGame", game);
            else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "SaveGame", getStage(), "Error to save the game");
            }
            
        }catch (Exception e) {
            Logger.getLogger(GameMenuController.class.getName()).log(Level.SEVERE, "Error to save the Game", e);
            new Mensaje().showModal(Alert.AlertType.ERROR, "SaveGame", getStage(), "Error to save the Game");
        }
    }
    
}
