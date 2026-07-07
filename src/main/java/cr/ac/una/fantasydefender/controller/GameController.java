package cr.ac.una.fantasydefender.controller;

import cr.ac.una.fantasydefender.model.GameDTO;
import cr.ac.una.fantasydefender.service.GameManager;
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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Takkasama
 */
public class GameController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Canvas gameCanvas;
    @FXML
    private Label lblWaveNumber;
    @FXML
    private VBox vbPause;
    @FXML
    private MFXButton btnContinue;
    @FXML
    private MFXButton btnMenu;
    @FXML
    private MFXButton btnNextLevel;
    @FXML
    private MFXButton btnRetry;
    @FXML
    private MFXButton btnPause;

    private GameManager gameManager;
    private GameDTO game = (GameDTO)AppContext.getInstance().get("SelectedGame");
    private GraphicsContext gc;
    private boolean isGameStarted;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        load();
       
        bindGameCanvas();

        
        gameCanvas.widthProperty().addListener((obs , oldValue , newValue) -> { 
            if (gameCanvas.getWidth() > 0 && gameCanvas.getHeight() > 0) 
                    start();
        });
        gameCanvas.heightProperty().addListener((obs, oldValue, newValue) -> {  
            if (gameCanvas.getWidth() > 0 && gameCanvas.getHeight() > 0) 
                    start();
                });
        
        Platform.runLater(() -> {start();});

        verifyGameStatus();
    }

    @Override
    public void initialize() {
        setNombreVista("Fantasy Defender");
    }

    public void clean() {}    

    private void onActionPauseButton(ActionEvent event) {
        game.setGameState(GameDTO.GameState.PAUSED);
    }
    
    private void load(){
        
        this.isGameStarted = false;
                
        this.gc  = gameCanvas.getGraphicsContext2D();
        this.gameManager = new GameManager(gameCanvas, gc, game);   
    }
    
    private void start(){
        
        
       if(!isGameStarted){
           gameManager.buildGame();
           updateLabelWaves();
           isGameStarted = true;
       }else        
            gameManager.startGame(); 
                
    }
    
    private void bindGameCanvas(){
        this.gameCanvas.widthProperty().bind(root.widthProperty());
        this.gameCanvas.heightProperty().bind(root.heightProperty());     
    }
    
    private void verifyGameStatus() {
        
        game.getGameStateProperty().addListener((obs, oldValue, newValue) -> {

            if(newValue == GameDTO.GameState.NEXT_LEVEL){
                if(game.getGameResult() != GameDTO.GameResult.VICTORY) return;

                saveGame();
                
                game.setGameResult(GameDTO.GameResult.NONE);

                gameManager.resetGame();

                game.setGameState(GameDTO.GameState.ACTIVE);
            }       
            
            else  if(newValue == GameDTO.GameState.RESTARTED){
                gameManager.resetGame();
                game.setGameState(GameDTO.GameState.ACTIVE);
   
            }
            else if(newValue == GameDTO.GameState.ACTIVE && oldValue == GameDTO.GameState.PAUSED){
                gameManager.playGame();
            }
   
            else if(newValue ==GameDTO.GameState.EXITING ){
                if(game.getGameResult() == GameDTO.GameResult.VICTORY)
                    saveGame();
                game.setGameResult(GameDTO.GameResult.NONE);
                
                gameManager.stopGame();
                
                FlowController.getInstance().goMain("GameMenu");
            }
            else  if(newValue == GameDTO.GameState.PAUSED){
      
            }

        });
        
    }
    
    private void updateLabelWaves(){
        if(gameManager.getLevelGame() ==null) return;
        this.lblWaveNumber.setText("Total Waves : " + gameManager.getLevelGame().getWaves());
        
        this.gameManager.getLevelGame().getCurrentWaveProperty().addListener((obs, oldValue, newValue) -> {
            lblWaveNumber.setText("Wave : " + newValue + "\t - /  - \t" + oldValue);
        });
    }

   private void saveGame(){
        try{
            GameService gameService = new GameService();
            Respuesta res = gameService.saveGame(game);
            if(!res.getEstado())
                new Mensaje().showModal(Alert.AlertType.ERROR, "saveGame", getStage(), res.getMensaje());
            else 
                new Mensaje().showModal(Alert.AlertType.WARNING, "saveGame", getStage(), "The was saved successufully");
        }catch(Exception ex){
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, "Error to save the save", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "saveGame", getStage(), "An Error Occurred During Save the Game");
        }
    }
   
   private void showPauseBox(){
       vbPause.setManaged(true);
       vbPause.setVisible(true);
   }
   
   private void hidePauseBox(){
       vbPause.setManaged(false);
       vbPause.setVisible(false);
   }
   
   private void loadPauseBox(){
       
       
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

    @FXML
    private void onKeyPressedBtnContinue(KeyEvent event) {
        if(event.getCode() == KeyCode.ESCAPE)
            onActionBtnContinue(null);
    }

    @FXML
    private void onActionBtnContinue(ActionEvent event) {
        game.setGameState(GameDTO.GameState.ACTIVE);
        hidePauseBox();
    }

    @FXML
    private void onActionBtnMenu(ActionEvent event) {
         game.setGameState(GameDTO.GameState.EXITING);
         hidePauseBox();
    }

    @FXML
    private void onActionBtnNextLevel(ActionEvent event) {
        game.setGameState(GameDTO.GameState.NEXT_LEVEL);
        hidePauseBox();
    }

    @FXML
    private void onActionBtnRetry(ActionEvent event) {
        game.setGameState(GameDTO.GameState.RESTARTED);
        hidePauseBox();
    }

    @FXML
    private void onKeyPressedBtnPause(KeyEvent event) {
        if(event.getCode() == KeyCode.ESCAPE)
            showPauseBox();
    }

    @FXML
    private void onActionBtnPause(ActionEvent event) {
        showPauseBox();
    }
    
}
