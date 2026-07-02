package cr.ac.una.fantasydefender.controller;

import cr.ac.una.fantasydefender.model.CastleDTO;
import cr.ac.una.fantasydefender.model.CrossbowDTO;
import cr.ac.una.fantasydefender.model.GameDTO;
import cr.ac.una.fantasydefender.model.PlayerDTO;
import cr.ac.una.fantasydefender.service.CastleService;
import cr.ac.una.fantasydefender.service.CrossbowService;
import cr.ac.una.fantasydefender.util.AppContext;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
public class MakeCampaignController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private MFXTextField txfCampaignName;    
    @FXML
    private MFXButton normalButton;
    @FXML
    private MFXButton hardButton;
    @FXML
    private MFXButton brutalButton;
    @FXML
    private MFXButton easyButton;
    

    private GameDTO game;
    @FXML
    private MFXButton cancelButton;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.game = new GameDTO();
        this.brutalButton.setUserData(GameDTO.getBRUTALITY_DIFFICULTY());
        this.hardButton.setUserData(GameDTO.getHARD_DIFFICULTY());
        this.normalButton.setUserData(GameDTO.getNORMAL_DIFFICULTY());
        this.easyButton.setUserData(GameDTO.getEASY_DIFFICULTY());  
    
        this.easyButton.setText("");
        this.normalButton.setText("");
        this.hardButton.setText("");
        this.brutalButton.setText("");
        this.cancelButton.setText("");

    }    
    
    @Override
    public void initialize() {
        // TODO
    }        
    @FXML
    private void onActinoEasyButton(ActionEvent event) {
        this.game.setDifficulty((double) easyButton.getUserData());
        defineGame();
         getParent().hideChildView();
    }

    @FXML
    private void onActionNormalButton(ActionEvent event) {
        this.game.setDifficulty((double) normalButton.getUserData());
        defineGame();
         getParent().hideChildView();    
                 
    }

    @FXML
    private void onActionHardButton(ActionEvent event) {
        this.game.setDifficulty((double) hardButton.getUserData());
        defineGame();
         getParent().hideChildView();        
        
    }

    @FXML
    private void onActionBrutalButton(ActionEvent event) {
        this.game.setDifficulty((double) brutalButton.getUserData());
        defineGame();
         getParent().hideChildView();
    }

    @FXML
    private void onActionCancelButton(ActionEvent event) {
        getParent().hideChildView();
    }
    
    @FXML
    private void onKeyPressedTxfCampaignName(KeyEvent event) {
        
        if(event.getCode() == KeyCode.ENTER){
            this.game.setDifficulty(GameDTO.getNORMAL_DIFFICULTY());
            defineGame();
            getParent().hideChildView();
        }    
    
    }
    
    

    private void defineGame() {
        if(txfCampaignName.getText().isBlank())
            this.game.setName("New Game");
        else 
            this.game.setName(txfCampaignName.getText());

        this.game.setPlayer((PlayerDTO) AppContext.getInstance().get("Player"));
        this.game.setCrossbow(saveCrossbow());
        this.game.setCastle(saveCastle());

        AppContext.getInstance().set("NewGame", game);
    }

    private CrossbowDTO saveCrossbow() {
        try {
            CrossbowService crossbowService = new CrossbowService();
            Respuesta res = crossbowService.saveCrossbow(new CrossbowDTO());

            if(res.getEstado())
                return (CrossbowDTO) res.getResultado("Crossbow"); 
            else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Save Crossbow",getStage(), res.getMensaje());
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(MakeCampaignController.class.getName()).log(Level.SEVERE, "Error Saving Crossbow", e);
            return null;
        }
    }

    private CastleDTO saveCastle() {
        try {
            CastleService castleService = new CastleService();
            Respuesta res = castleService.saveCastle(new CastleDTO());

            if(res.getEstado())
                return (CastleDTO) res.getResultado("Castle"); 
            else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Save Castle", getStage(), res.getMensaje());
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(MakeCampaignController.class.getName()).log(Level.SEVERE, "Error Saving Castle", e);
            return null;
        }
    }    


}
