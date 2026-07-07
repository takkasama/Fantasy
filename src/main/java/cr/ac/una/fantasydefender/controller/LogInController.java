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
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author andys
 */
public class LogInController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private MFXTextField txtUserName;
    @FXML
    private MFXPasswordField txtPassword;
    @FXML
    private MFXButton btnEnter;
    @FXML
    private MFXButton btnCancel;
    
    private List<Node> required = new ArrayList<>();
    private boolean isSessionValid = false;  
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        indicateRequired();
        
        load();
    }    
    @Override
    public void initialize() {
        setNombreVista("Login User");
    }
    private void load(){
        txtUserName.delegateSetTextFormatter(Formato.getInstance().letrasFormat(30));
        txtPassword.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(255));

        cleanFields();
    }

    @FXML
    private void onkeyPressedTxtUserName(KeyEvent event) {
        onActionBtnEnter(null);
    }

    @FXML
    private void onKeyPressedTxtPassword(KeyEvent event) {
        onActionBtnEnter(null);
    }

    @FXML
    private void onActionBtnEnter(ActionEvent event) {
        login();
        
        if(!isSessionValid) return;
       cleanFields();
        FlowController.getInstance().goMain();
       
    }
    @FXML
    private void onActionBtnCancel(ActionEvent event) {
        cleanFields();
        FlowController.getInstance().goViewInPane("LogInView", null, true);
    }
    
    private void cleanFields() {
        txtUserName.clear();
        txtPassword.clear();
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
    
    public void indicateRequired(){
        required.clear();
        required.addAll(Arrays.asList(txtUserName, txtPassword));
    }

    private void login(){
        String invalids = validateRequired();
        try{
            if(invalids != null && !invalids.isBlank())
                new Mensaje().showModal(Alert.AlertType.ERROR, "Login user", getStage(), invalids);
            else{
                PlayerService userService = new PlayerService();
                Respuesta res = userService.getPlayer(txtUserName.getText());
                
                if(res.getEstado()){
                    validateRequired();
                    PlayerDTO player = (PlayerDTO) res.getResultado("Player");
                    if(!player.getPassword().equals(txtPassword.getText())){
                         new Mensaje().showModal(Alert.AlertType.ERROR, "Login Player", getStage(), "Incorrect Password");
                         isSessionValid = false;
                    } else{
                        isSessionValid = true;
                        ObjectProperty<PlayerDTO> playerProperty = new SimpleObjectProperty<>(player);
                        AppContext.getInstance().set("Player", playerProperty);
                    }
                }
            }
          }catch(Exception ex){
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, "Invalid Player To Login", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Login Player", getStage(), "An Error Occurred During Open Session");
        }

    }

    
}