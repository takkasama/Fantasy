package cr.ac.una.fantasydefender.controller;

import cr.ac.una.fantasydefender.model.GameDTO;
import cr.ac.una.fantasydefender.util.AppContext;
import cr.ac.una.fantasydefender.util.DataNotifier;
import cr.ac.una.fantasydefender.util.Mensaje;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author takkasama
 */
public class SettingsGameController extends Controller implements Initializable , DataNotifier.Listener{

    @FXML
    private Label lblCurrentName;
    @FXML
    private MFXTextField txtNewName;
    @FXML
    private MFXButton btnSetName;
    @FXML
    private Label lblCurrentDifficultty;
    @FXML
    private MFXButton btnEasy;
    @FXML
    private MFXButton btnNormal;
    @FXML
    private MFXButton btnHard;
    @FXML
    private MFXButton btnBrutality;
    
    private GameDTO selectedGame;
    @FXML
    private AnchorPane root;
    @FXML
    private HBox hBoxDifficulties;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        load();
        DataNotifier.subscribe(this);
    }    

    @Override
    public void initialize() {
        cleanFields();
    }

    @Override
      public void onDataChanged(String event){
          if(event.equals("newGameSelected"))
            load();
      }
      
    @FXML
    private void onKeyPressedNewName(KeyEvent event) {
         onActionBtnSetName(null);
    }

    @FXML
    private void onActionBtnEasy(ActionEvent event) {
        changeDifficulty("EASY");
    }

    @FXML
    private void onActionBtnNormal(ActionEvent event) {
        changeDifficulty("NORMAL");
    }

    @FXML
    private void onActIonBtnHard(ActionEvent event) {
        changeDifficulty("HARD");
    }

    @FXML
    private void onActionBtnBrutality(ActionEvent event) {
        changeDifficulty("BRUTALITY");
    }
    
    @FXML
    private void onActionBtnSetName(ActionEvent event) {
        if(txtNewName.getText().isBlank()){
            new Mensaje().showModal(Alert.AlertType.WARNING, "onActionBtnSetName", getStage(), "The field could not be blanck");
            txtNewName.setManaged(false);
            txtNewName.setVisible(false);
            
            btnSetName.setManaged(false);
            btnSetName.setVisible(false);
            
            lblCurrentName.setManaged(true);
            lblCurrentName.setVisible(true);
            
            return;
        }
        txtNewName.setManaged(false);
        txtNewName.setVisible(false);

        btnSetName.setManaged(false);
        btnSetName.setVisible(false);

        lblCurrentName.setManaged(true);
        lblCurrentName.setVisible(true);
        
        selectedGame.setName(txtNewName.getText());
        lblCurrentName.setText(selectedGame.getName());
        
        
        AppContext.getInstance().set("SelectedGame", selectedGame);
        DataNotifier.notifyChange("updateGame");

    }

    @FXML
    private void onMouseClickedLblCurrentName(MouseEvent event) {
        if(selectedGame  == null){
            new Mensaje().showModal(Alert.AlertType.WARNING, "onMouseClickedLblCurrentName", getStage(), "Please Select the game to modify");
            return;
        }
        if(event.getClickCount() == 2){
            lblCurrentName.setManaged(false);
            lblCurrentName.setVisible(false);

            txtNewName.setManaged(true);
            txtNewName.setVisible(true);
            txtNewName.setText(selectedGame.getName());
            
            btnSetName.setManaged(true);
            btnSetName.setVisible(true);
        }
    }

    @FXML
    private void onMouseClickedLblCurrentDifficulty(MouseEvent event) {
        if(selectedGame  == null){
            new Mensaje().showModal(Alert.AlertType.WARNING, "onMouseClickedLblCurrentName", getStage(), "Please Select the game to modify");
            return;
        }
        if(event.getClickCount() == 2){
             hBoxDifficulties.setManaged(true);
             hBoxDifficulties.setVisible(true);
         }
    }    
    private void load(){
         selectedGame = (GameDTO) AppContext.getInstance().get("SelectedGame");
        
        cleanFields();
        
        hBoxDifficulties.setManaged(false);
        hBoxDifficulties.setVisible(false);
        
        txtNewName.setManaged(false);
        txtNewName.setVisible(false);
        
        btnSetName.setManaged(false);
        btnSetName.setVisible(false);
        
        lblCurrentName.setText(selectedGame == null ? "UNDEFINED" : selectedGame.getName());
       
        if(selectedGame != null){
            if(selectedGame.getDifficulty() == GameDTO.getEASY_DIFFICULTY())
                lblCurrentDifficultty.setText("EASY");
            else if( selectedGame.getDifficulty() == GameDTO.getNORMAL_DIFFICULTY())
                lblCurrentDifficultty.setText("NORMAL");
            else if(selectedGame.getDifficulty() == GameDTO.getHARD_DIFFICULTY())
                lblCurrentDifficultty.setText("HARD");
            else if(selectedGame.getDifficulty() == GameDTO.getBRUTALITY_DIFFICULTY())
                lblCurrentDifficultty.setText("BRUTALITY");
        }
        else lblCurrentDifficultty.setText("UNDEFINED");
        
    }
    
    private void cleanFields(){
        txtNewName.setText("");
    }


    private void changeDifficulty(String name){
        switch (name) {
            case "EASY":
                lblCurrentDifficultty.setText(name);
                selectedGame.setDifficulty(GameDTO.getEASY_DIFFICULTY());
                  break;
                 
            case "NORMAL":
                lblCurrentDifficultty.setText(name);
                selectedGame.setDifficulty(GameDTO.getNORMAL_DIFFICULTY());
                  break;
                 
            case "HARD":
                lblCurrentDifficultty.setText(name);
                selectedGame.setDifficulty(GameDTO.getHARD_DIFFICULTY());
                  break;
                  
            default:
                lblCurrentDifficultty.setText(name);
                selectedGame.setDifficulty(GameDTO.getBRUTALITY_DIFFICULTY());
        }
        
        hBoxDifficulties.setManaged(false);
        hBoxDifficulties.setVisible(false);
        
        AppContext.getInstance().set("SelectedGame", selectedGame);
        DataNotifier.notifyChange("updateGame");
    }



}
