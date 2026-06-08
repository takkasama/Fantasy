package cr.ac.una.fantasydefender.controller;

import cr.ac.una.fantasydefender.util.FlowController;
import cr.ac.una.fantasydefender.util.Mensaje;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import cr.ac.una.fantasydefender.util.childViewInterface;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;

/**
 * FXML Controller class
 *
 * @author andys
 */
public class MainController extends Controller implements Initializable, childViewInterface {
    private ImageView mainBackground;
    @FXML
    private VBox vBox;
    @FXML
    private AnchorPane root;
    @FXML
    private BorderPane childProjector;
    @FXML
    private MFXButton informationButton;
    @FXML
    private MFXButton loginButton;
    @FXML
    private MFXButton registerButton;
    @FXML
    private MFXButton exitButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        load();
    }    
    @Override
    public void initialize() {
    }
    @Override
    public void clean(){}
    

    @FXML
    private void onActionLoginButton(ActionEvent event) {
            if(!isChildState()){
                FlowController.getInstance().goViewPane("LogInView",childProjector); 
                showChildView();
            } 
    }

    @FXML
    private void onActionRegisterButton(ActionEvent event) {
        if(!isChildState()){
            FlowController.getInstance().goViewPane("RegisterView", childProjector);
            showChildView();
        }
    }

    @FXML
    private void onActionExitButton(ActionEvent event) {
            if (new Mensaje().showConfirmation("Exit Game", getStage(), "Are you sure you want to leave? (╥‸╥)") )
                this.getStage().close();
    }

    @FXML
    private void onActionInformationButton(ActionEvent event) {
            new Mensaje().showModal(Alert.AlertType.INFORMATION,"About the game!", getStage(), "this is a project for the class programing 2 "
                    + "in the National University of Costa Rica by the students: Isaac and Andy");
    }

    private void load(){
        childState = false;
        setChildBorderPane(childProjector);
        childProjector.setVisible(false);
        childProjector.setManaged(false);
        
        registerButton.setText("");
        loginButton.setText("");
        informationButton.setText("");
        exitButton.setText("");

    }
}