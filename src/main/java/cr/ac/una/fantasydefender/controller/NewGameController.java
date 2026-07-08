package cr.ac.una.fantasydefender.controller;

import cr.ac.una.fantasydefender.model.CastleDTO;
import cr.ac.una.fantasydefender.model.CrossbowDTO;
import cr.ac.una.fantasydefender.model.GameDTO;
import cr.ac.una.fantasydefender.model.PlayerDTO;
import cr.ac.una.fantasydefender.service.GameService;
import cr.ac.una.fantasydefender.util.AppContext;
import cr.ac.una.fantasydefender.util.DataNotifier;
import cr.ac.una.fantasydefender.util.FlowController;
import cr.ac.una.fantasydefender.util.Mensaje;
import cr.ac.una.fantasydefender.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author takkasama
 */
public class NewGameController extends Controller implements Initializable {

    @FXML
    private MFXTextField txtGameName;
    @FXML
    private MFXButton btnEasy;
    @FXML
    private MFXButton btnNormal;
    @FXML
    private MFXButton btnHard;
    @FXML
    private MFXButton btnBrutality;
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @Override
    public void initialize() {
    }
    

    @FXML
    private void onKeyPressedTxtGameName(KeyEvent event) {
        buildAndSaveGame(GameDTO.getNORMAL_DIFFICULTY());
    }

    @FXML
    private void onActionBtnEasy(ActionEvent event) {
        buildAndSaveGame(GameDTO.getEASY_DIFFICULTY());
    }

    @FXML
    private void onActionBtnNormal(ActionEvent event) {
        buildAndSaveGame(GameDTO.getNORMAL_DIFFICULTY());
    }

    @FXML
    private void onActionBtnHard(ActionEvent event) {
        buildAndSaveGame(GameDTO.getHARD_DIFFICULTY());
    }

    @FXML
    private void onActionBtnBrutality(ActionEvent event) {
        buildAndSaveGame(GameDTO.getBRUTALITY_DIFFICULTY());
    }
    
    private void buildAndSaveGame(double difficulty){
        GameDTO gameDTO = new GameDTO();
        PlayerDTO playerDTO = (PlayerDTO) AppContext.getInstance().get("Player");
        gameDTO.setName(txtGameName.getText().isBlank() ? "Empty" : txtGameName.getText());
        gameDTO.setDifficulty(difficulty);
        gameDTO.setCastle(new CastleDTO());
        gameDTO.setCrossbow(new CrossbowDTO());
        gameDTO.setPlayer(playerDTO);
        
        GameDTO saved = saveGame(gameDTO);
        if(saved != null){
            playerDTO.getGameList().add(saved);
            playerDTO.getGamesListObservable().add(saved);
            
            DataNotifier.notifyChange("newGame");
            FlowController.getInstance().goViewInPane("NewGameView", null, true);
        }
    
    }
    private GameDTO saveGame (GameDTO gameDTO){
        try {
            GameService gameService =  new GameService();
            Respuesta res = gameService.saveGame(gameDTO);
            
            if(res.getEstado()){
                new Mensaje().showModal( Alert.AlertType.CONFIRMATION,"saveGame", getStage(), "The game was save succeffully.");
                return (GameDTO) res.getResultado("Game");
            }
            else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "saveGame", getStage(), "Error to save the game, please try again.");
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(NewGameController.class.getName()).log(Level.SEVERE, "Error to save the game", e);
            new Mensaje().showModal(Alert.AlertType.ERROR, "saveGame", getStage(), "Error to save the game, please try again.");
            return null;
        }
    }
    
}

