package cr.ac.una.fantasydefender.controller;

import cr.ac.una.fantasydefender.model.PlayerDTO;
import cr.ac.una.fantasydefender.util.AppContext;
import cr.ac.una.fantasydefender.util.DataNotifier;
import cr.ac.una.fantasydefender.util.FlowController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Takkasama
 */
public class MainController extends Controller implements Initializable {
    @FXML
    private BorderPane root;
    @FXML
    private MFXButton btnPlay;
    @FXML
    private MFXButton btnLogOut;
    @FXML
    private MFXButton btnLogin;
    @FXML
    private Label lblRegister;    
    @FXML
    private VBox vBoxToVizualizer;
    
    private PlayerDTO playerDTO;
    private ObjectProperty<PlayerDTO> playerProperty  = new SimpleObjectProperty<>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        load();   
    }    
    @Override
    public void initialize() {
        getStage().setResizable(false);
    }
   
    
    /**
     *  ACTIONS 
     */
    @FXML
    private void onActionBtnPlay(ActionEvent event) {
        FlowController.getInstance().goViewInStage("GameMenuView", getStage());
    }

    @FXML
    private void onActionBtnLogOut(ActionEvent event) {
        AppContext.getInstance().set("Player", null);
        load();
    }

    @FXML
    private void onActionBtnLogin(ActionEvent event) {
        FlowController.getInstance().goViewInPane("LogInView", vBoxToVizualizer, false);
    }

    @FXML
    private void onMouseClickedLblRegister(MouseEvent event) {
        FlowController.getInstance().goViewInPane("RegisterView", vBoxToVizualizer, false);
    }   
    
    private void load(){
        playerDTO = (PlayerDTO) AppContext.getInstance().get("Player");
        playerProperty.set(playerDTO);
        
        if(playerDTO == null){
            btnLogOut.setManaged(false);
            btnLogOut.setVisible(false);
            
            btnPlay.setManaged(false);
            btnPlay.setVisible(false);
            
            btnLogin.setManaged(true);
            btnLogin.setVisible(true);
            
            lblRegister.setManaged(true);
            lblRegister.setVisible(true);
        }
        else{
            btnLogin.setManaged(false);
            btnLogin.setVisible(false);

            lblRegister.setManaged(false);
            lblRegister.setVisible(false);
            
            btnLogOut.setManaged(true);
            btnLogOut.setVisible(true);
            
            btnPlay.setManaged(true);
            btnPlay.setVisible(true);
        }
    }    
    
    private void listen(){
         ObjectProperty<PlayerDTO> playerContext =
                        (ObjectProperty<PlayerDTO>) AppContext.getInstance().get("Player");

                if (playerContext == null) {
                    playerContext = new SimpleObjectProperty<>(null);
                    AppContext.getInstance().set("Player", playerContext);
                }

                playerContext.addListener((ob, ov, nv) -> load());
            
    }
}