package cr.ac.una.fantasydefender.controller;

import cr.ac.una.fantasydefender.model.PlayerDTO;
import cr.ac.una.fantasydefender.service.PlayerService;
import cr.ac.una.fantasydefender.util.EmailManager;
import cr.ac.una.fantasydefender.util.FlowController;
import cr.ac.una.fantasydefender.util.Formato;
import cr.ac.una.fantasydefender.util.Mensaje;
import cr.ac.una.fantasydefender.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Takkasama
 */
public class RegisterController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private MFXTextField txtEmail;
    @FXML
    private MFXTextField txtPlayerName;
    @FXML
    private MFXPasswordField txtPassword;
    @FXML
    private MFXPasswordField txtConfirmPassword;
    @FXML
    private MFXButton btnRegister;
    @FXML
    private Label lblRegisterState;
    @FXML
    private MFXButton btnCancel;
    @FXML
    private MFXTextField txtCode;
    @FXML
    private MFXButton btnGetCode;    
    
    private PlayerDTO player;
    
    private ObjectProperty<PlayerDTO> playerProperty = new SimpleObjectProperty<>();
    private List<Node> required = new ArrayList<>();
    
    private final static int COOLDOWN_SECONDS = 30;
    private String currentCodeRegister;


    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        load();

        bindUser();
        indicateRequired();
        loadValues();
    }  
    
    @Override
    public void initialize() {
        setNombreVista("Register Player");
        cleanFields();
    }
    
    @FXML
    private void onActionBtnRegister(ActionEvent event) {
        String invalids = validateRequired();        
        try{
            if(!invalids.isBlank())
                new Mensaje().showModal(Alert.AlertType.ERROR, "Save Player", getStage(), invalids);
            else if (!isValidPasswordConfirm())
                new Mensaje().showModal(Alert.AlertType.ERROR, "Save Player", getStage(), "The password does not match.");    
            else if(!currentCodeRegister.equals(txtCode.getText())){
                new Mensaje().showModal(Alert.AlertType.ERROR, "Save Player", getStage(), "The verification code does not match.");
                currentCodeRegister = "";
            }
            else{
                PlayerService playerService = new PlayerService();
                Respuesta res = playerService.savePlayer(player);
                if(res.getEstado()){
                    this.player = (PlayerDTO) res.getResultado("Player");
                    this.playerProperty.set(player);
                    cleanFields();
                    FlowController.getInstance().goViewInPane("LogInView", null, true);                    
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "SavePlayer", getStage(), " The Player was register Successefully");
                    
                }
                else new Mensaje().showModal(Alert.AlertType.NONE, "Player User", getStage(), res.getMensajeInterno());
            }
            
        } catch (Exception e) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, "Error Saving Player", e);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Saver Player", getStage(), "An error occurred while saving the Player.");
        }
        
    }

    @FXML
    private void onActionBtnCancel(ActionEvent event) {
        cleanFields();
        FlowController.getInstance().goViewInPane("RegisterView", null, true);
    }
    
    @FXML
    private void onKeyPressedTxtCode(KeyEvent event) {
        if(event.getCode().equals(event.getCode().ENTER))
        onActionGetCode(null);
    }

    @FXML
    private void onActionGetCode(ActionEvent event) {
        if(!txtEmail.getText().isBlank()){
            startCoolDown();
            sendEmailToCofirm();
        }else {
            new Mensaje().showModal(Alert.AlertType.WARNING, "onActionGetCode", getStage(), "The space of the email is required.");
        }
    }

    
    private void bindUser(){
        try{
            playerProperty.addListener((o, oldV, newV) -> {
                if(oldV  != null){
                    txtPlayerName.textProperty().unbindBidirectional(oldV.getNameProperty());
                    txtEmail.textProperty().unbindBidirectional(oldV.getEmailProperty());
                    txtPassword.textProperty().unbindBidirectional(oldV.getPasswordProperty());                
                }
                if(newV != null){
                    txtPlayerName.textProperty().bindBidirectional(newV.getNameProperty());
                    txtEmail.textProperty().bindBidirectional(newV.getEmailProperty());
                    txtPassword.textProperty().bindBidirectional(newV.getPasswordProperty());
                }

            });
        }
        catch(Exception e){
            new Mensaje().showModal(Alert.AlertType.ERROR, "ERROR Bindeo", getStage(), "An error occurred during binding.");
        }
    }
    
    private void loadValues(){
       this.player = new PlayerDTO();
       this.player.setRegisterDate(LocalDate.now());
       this.playerProperty.set(player);
       validateRequired();
    }
    
    private void indicateRequired(){
        required.clear();
        required.addAll(Arrays.asList(txtPlayerName, txtPassword, txtConfirmPassword, txtEmail));        
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

    private boolean isValidPasswordConfirm(){
        if(txtPassword.getText() == null || txtPassword.getText().isBlank())
            return false;
        
        if(txtConfirmPassword.getText() == null || txtConfirmPassword.getText().isBlank())
            return false;
        
        if(!txtConfirmPassword.getText().equals(txtPassword.getText()))
            return false;
        
        return true;
    }
    
    private void startCoolDown(){
        btnGetCode.setDisable(true);
        
        int coolDownTime[] = {COOLDOWN_SECONDS};
        Timeline timeline = new Timeline();
        
        timeline.setCycleCount(coolDownTime[0]);
        KeyFrame keyframe = new KeyFrame(Duration.seconds(1), e ->{
           coolDownTime[0]--;
        });
        
        timeline.getKeyFrames().add(keyframe);
        
        timeline.setOnFinished(e ->{
            btnGetCode.setDisable(false);
        });
        timeline.play();
        
    }
    
    private void sendEmailToCofirm(){
        Random random = new Random();
        String code = String.valueOf(random.nextInt(10000));
        this.currentCodeRegister = code;
        
        // Hacer tabla en base de datos para el admin y guaadar credenciales
        
        char pass[]  = "whkncwinhaeoazgj".toCharArray();
        EmailManager.getInstance().init("takka.games.personal.use@gmail.com", pass);
        EmailManager.getInstance().sendCodeToConfirmEmail(txtEmail.getText(), code);
        
    }
    
    private void cleanFields(){
        this.txtPlayerName.setText("");
        this.txtEmail.setText("");
        this.txtConfirmPassword.setText("");
        this.txtPassword.setText("");
        this.txtCode.setText("");
    }

    private void load(){

        txtPlayerName.delegateSetTextFormatter(Formato.getInstance().letrasFormat(30));
        txtEmail.delegateSetTextFormatter(Formato.getInstance().emailFormat());
        txtPassword.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(255));
        txtConfirmPassword.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(255));
        
        this.player = new PlayerDTO();


    }


}
