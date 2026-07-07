package cr.ac.una.fantasydefender.controller;

import cr.ac.una.fantasydefender.model.PlayerDTO;
import cr.ac.una.fantasydefender.util.AppContext;
import cr.ac.una.fantasydefender.util.FlowController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Takkasama
 */
public class MainController extends Controller implements Initializable {
    @FXML
    private BorderPane root;
    @FXML
    private MFXButton btnPlay;
    @FXML
    private MFXButton btnSettins;
    @FXML
    private MFXButton btnLogOut;
    @FXML
    private MFXButton btnMoreAbout;
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
        ObjectProperty<PlayerDTO> playerContext =
                (ObjectProperty<PlayerDTO>) AppContext.getInstance().get("Player");

        if (playerContext == null) {
            playerContext = new SimpleObjectProperty<>(null);
            AppContext.getInstance().set("Player", playerContext);
        }

        playerContext.addListener((ob, ov, nv) -> load());

        load();

        
    }    
    @Override
    public void initialize() {
    }
   
    @FXML
    private void onActionBtnPlay(ActionEvent event) {
        FlowController.getInstance().goViewInStage("GameMenuView", getStage());
    }

    @FXML
    private void onActionBtnSettings(ActionEvent event) {
        
    }

    @FXML
    private void onActionBtnLogOut(ActionEvent event) {
        AppContext.getInstance().set("Player", new SimpleObjectProperty<PlayerDTO>());
        load();
    }

    @FXML
    private void onActionBtnMoreAbout(ActionEvent event) {
        
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
        
        playerDTO = ((ObjectProperty<PlayerDTO>) AppContext.getInstance().get("Player")).get();
        playerProperty.set(playerDTO);
        
        if(playerDTO == null){
            btnLogOut.setManaged(false);
            btnLogOut.setVisible(false);
            
            btnPlay.setManaged(false);
            btnPlay.setVisible(false);

            btnSettins.setManaged(false);
            btnSettins.setVisible(false);
            
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

            btnSettins.setManaged(true);
            btnSettins.setVisible(true);
        }
    }
    
  private class ButtonCell extends ListCell<PlayerDTO> {

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
               //  ACTION BUTTOM    
            });
            
        }
        

            @Override
             protected void updateItem(PlayerDTO player, boolean empty) {      
                 super.updateItem(player, empty);
                 
                 if(empty || player == null){
                     setText(null);
                     setGraphic(null);
                 }
                 
                 else{
                     String name = player.getName() != null ? player.getName() : "NO NAME";
                     
                     String text = name.toUpperCase() + "\t Player Name : " + player.getName() ;

                     label.setText(text);
                     setGraphic(hbox);
                 }
             }
    
    }
}