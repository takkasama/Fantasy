package cr.ac.una.fantasydefender.controller;

import cr.ac.una.fantasydefender.model.GameDTO;
import cr.ac.una.fantasydefender.util.AppContext;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author chela
 */
public class PauseController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private MFXButton continueButton;
    @FXML
    private MFXButton retryButton;
    @FXML
    private MFXButton nextLevelButton;
    
    private GameDTO game;
    @FXML
    private MFXButton exitGameButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadView();
        
    }    

    @Override
    public void initialize() {
        setNombreVista("Pause");
        
    }

    @FXML
    private void onActionContinueButton(ActionEvent event) {
        game.setGameState(GameDTO.GameState.ACTIVE);
        getParent().hideChildView();
    }

    @FXML
    private void onActinoRetryButton(ActionEvent event) {
        game.setGameState(GameDTO.GameState.RESTARTED);
        getParent().hideChildView();
    }

    @FXML
    private void onActionNextLevel(ActionEvent event) {
        game.setGameState(GameDTO.GameState.NEXT_LEVEL);
        getParent().hideChildView();
    }

    @FXML
    private void onActionExitGameButton(ActionEvent event) {
        game.setGameState(GameDTO.GameState.EXITING);
        getParent().hideChildView();
        
    }
    
    private void loadView(){
        continueButton.setText("");
        retryButton.setText("");
        nextLevelButton.setText("");
        exitGameButton.setText("");
        
        game = (GameDTO) AppContext.getInstance().get("SelectedGame");
        
       if(game.getGameState() == GameDTO.GameState.PAUSED &&  game.getGameResult() == GameDTO.GameResult.NONE){
           nextLevelButton.setManaged(false);
       }
        if(game.getGameState() == GameDTO.GameState.PAUSED && game.getGameResult() == GameDTO.GameResult.DEFEAT){
            continueButton.setManaged(false);
            nextLevelButton.setManaged(false);
        }
        if(game.getGameState() == GameDTO.GameState.PAUSED && game.getGameResult() == GameDTO.GameResult.VICTORY){
            continueButton.setManaged(false);
        }
    }

}
