package cr.ac.una.fantasydefender.controller;

import cr.ac.una.fantasydefender.model.GameDTO;
import cr.ac.una.fantasydefender.util.AppContext;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 *
 * @author takkasama
 */
public class PauseController extends Controller implements Initializable {

    @FXML
    private MFXButton btnContinue;
    @FXML
    private MFXButton bntRetry;
    @FXML
    private MFXButton btnNextLevel;
    @FXML
    private MFXButton btnMenu;
    
    private GameDTO game;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadView();
        
    }    

    @Override
    public void initialize() {
        setNombreVista("Pause");
        
    }

    private void onActionBtnContinue(ActionEvent event) {
        game.setGameState(GameDTO.GameState.ACTIVE);
    }

    @FXML
    private void onActionBtnRetry(ActionEvent event) {
        game.setGameState(GameDTO.GameState.RESTARTED);
    }

    @FXML
    private void onActionBtnNextLevel(ActionEvent event) {
        game.setGameState(GameDTO.GameState.NEXT_LEVEL);
    }

    @FXML
    private void onActionBtnMenu(ActionEvent event) {
        game.setGameState(GameDTO.GameState.EXITING);
        
    }
    
    private void loadView(){
        
        game = (GameDTO)AppContext.getInstance().get("SelectedGame");
        
       if(game.getGameState() == GameDTO.GameState.PAUSED &&  game.getGameResult() == GameDTO.GameResult.NONE){
           btnNextLevel.setManaged(false);
       }
        if(game.getGameState() == GameDTO.GameState.PAUSED && game.getGameResult() == GameDTO.GameResult.DEFEAT){
            btnContinue.setManaged(false);
            btnNextLevel.setManaged(false);
        }
        if(game.getGameState() == GameDTO.GameState.PAUSED && game.getGameResult() == GameDTO.GameResult.VICTORY){
            btnContinue.setManaged(false);
        }
    
    
    }



}
