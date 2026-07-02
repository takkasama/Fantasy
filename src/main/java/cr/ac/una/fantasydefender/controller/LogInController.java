package cr.ac.una.fantasydefender.controller;

import cr.ac.una.fantasydefender.model.PlayerDTO;
import cr.ac.una.fantasydefender.service.PlayerService;
import cr.ac.una.fantasydefender.util.AppContext;
import cr.ac.una.fantasydefender.util.FlowController;
import cr.ac.una.fantasydefender.util.Formato;
import cr.ac.una.fantasydefender.util.Mensaje;
import cr.ac.una.fantasydefender.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import cr.ac.una.fantasydefender.util.childViewInterface;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;

/**
 * FXML Controller class
 *
 * @author andys
 */
public class LogInController extends Controller implements Initializable, childViewInterface {

    @FXML
    private MFXTextField txfUserName;
    @FXML
    private MFXPasswordField pwfPassword;
    @FXML
    private MFXButton cancelButton;
    @FXML
    private MFXButton logInButton;
    @FXML
    private BorderPane controlsBorderPane;
    @FXML
    private AnchorPane root;
    

    
    private List<Node> required = new ArrayList<>();
    private boolean isSessionValid = false;  
    
    //-------------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cancelButton.setText("");
        logInButton.setText("");
        txfUserName.delegateSetTextFormatter(Formato.getInstance().letrasFormat(30));
        pwfPassword.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(255));
        indicateRequired();
    }    
    //-------------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void initialize() {
    }
    //-------------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void clean() {
        txfUserName.clear();
        pwfPassword.clear();
    }
    
    @FXML
    private void onActionLogInButton(ActionEvent event) {
        login();
        if(isSessionValid) FlowController.getInstance().goMain("GameMenu");
    }

    @FXML
    private void onActionCancelButton(ActionEvent event) {
        clean();
        getParent().hideChildView();
    }
    
    
    public String validateRequired() {
        Boolean valid = true;
        String invalid = "";
        for (Node node : required) {
            if (node instanceof MFXTextField && (((MFXTextField) node).getText() == null || ((MFXTextField) node).getText().isBlank())) {
                if (valid) {
                    invalid += ((MFXTextField) node).getFloatingText();
                } else {
                    invalid += "," + ((MFXTextField) node).getFloatingText();
                }
                valid = false;
            } else if (node instanceof MFXPasswordField && (((MFXPasswordField) node).getText() == null || ((MFXPasswordField) node).getText().isBlank())) {
                if (valid) {
                    invalid += ((MFXPasswordField) node).getFloatingText();
                } else {
                    invalid += "," + ((MFXPasswordField) node).getFloatingText();
                }
                valid = false;
            } else if (node instanceof MFXDatePicker && ((MFXDatePicker) node).getValue() == null) {
                if (valid) {
                    invalid += ((MFXDatePicker) node).getFloatingText();
                } else {
                    invalid += "," + ((MFXDatePicker) node).getFloatingText();
                }
                valid = false;
            } else if (node instanceof MFXComboBox && ((MFXComboBox) node).getSelectionModel().getSelectedIndex() < 0) {
                if (valid) {
                    invalid += ((MFXComboBox) node).getFloatingText();
                } else {
                    invalid += "," + ((MFXComboBox) node).getFloatingText();
                }
                valid = false;
            }
        }
        if (valid) {
            return "";
        } else {
            return "Required fields or fields with formatting problems [" + invalid + "].";
        }
    }
    //-------------------------------------------------------------------------------------------------------------------------------------------
    public void indicateRequired(){
        required.clear();
        required.addAll(Arrays.asList(txfUserName, pwfPassword));
    }
    //------------------------------------------------------------------------------------------------------------------------------------------- 
    private void login(){
        String invalids = validateRequired();
        try{
            if(invalids != null && !invalids.isBlank())
                new Mensaje().showModal(Alert.AlertType.ERROR, "Login user", getStage(), invalids);
            else{
                PlayerService userService = new PlayerService();
                Respuesta res = userService.getPlayer(txfUserName.getText());
                
                if(res.getEstado()){
                    validateRequired();
                    PlayerDTO player = (PlayerDTO) res.getResultado("Player");
                    if(!player.getPassword().equals(pwfPassword.getText())){
                         new Mensaje().showModal(Alert.AlertType.ERROR, "Login Player", getStage(), "Incorrect Password");
                         isSessionValid = false;
                    } else{
                        isSessionValid = true;
                        AppContext.getInstance().set("Player", player);    
                    }
                }
            }
          }catch(Exception ex){
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, "Invalid Player To Login", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Login Player", getStage(), "An Error Occurred During Open Session");
        }

    }
    
}