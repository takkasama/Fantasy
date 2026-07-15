package cr.ac.una.fantasydefender.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author takkasama
 */
public class InfoGameController extends Controller implements Initializable {

    @FXML
    private Label lblLevel;
    @FXML
    private Label lblPoints;
    @FXML
    private Label lblCastle;
    @FXML
    private Label lblCrossbow;
    @FXML
    private Label lblArrow;
    @FXML
    private Label lblPower1;
    @FXML
    private Label lblPower2;
    @FXML
    private ImageView ttack;
    @FXML
    private Label lblInfo1;
    @FXML
    private Label lblInfo2;
    @FXML
    private Label lblInfo3;
    @FXML
    private Label lblInfo4;
    @FXML
    private Label lblInfo5;
    @FXML
    private VBox vBoxInfo;
    
    private boolean isShowed;
    private String currentKey;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
   @Override
    public void initialize() {
        load();
    }

    @FXML
    private void onMouseClickedLblCaastle(MouseEvent event) {
        showInfo("Castle");
    }

    @FXML
    private void onMouseClickedLblCrossbow(MouseEvent event) {
        showInfo("Crossbow");
    }

    @FXML
    private void onMouseClickedLblArrow(MouseEvent event) {
        showInfo("Arrow");
    }

    @FXML
    private void onMouseClickedLblPower1(MouseEvent event) {
        showInfo("Power1");
    }

    @FXML
    private void onMouseClickedLblPower2(MouseEvent event) {
        showInfo("Power2");
    }

    private void load(){
        vBoxInfo.setManaged(false);
        vBoxInfo.setVisible(false);
        isShowed = false;
        currentKey = "";
    }
    private void showInfo(String key){
        if(isShowed && currentKey.equals(key))
            isShowed = false;
        else {
            isShowed = true;
            currentKey = key;
        }
        vBoxInfo.setManaged(isShowed);
        vBoxInfo.setVisible(isShowed);
        
        
        // cargado de informacion de la informacion
    }
}
