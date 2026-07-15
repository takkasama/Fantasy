package cr.ac.una.fantasydefender.controller;

import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXSlider;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

/**
 *
 * @author takkasama
 */
public class SettingsGeneralController  extends Controller implements Initializable {

    @FXML
    private MFXSlider slGlobalVolume;
    @FXML
    private MFXSlider slSoundsEffectVolume;
    @FXML
    private MFXSlider slMusicVolume;
    @FXML
    private MFXRadioButton rdFullscreen;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rdFullscreen.selectedProperty().addListener((observable, oldValue, newValue) -> {
           if(newValue){
               ((Stage)(getParent().getScene().getWindow())).setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
               ((Stage)(getParent().getScene().getWindow())).setFullScreen(true);
           }
           else{
               ((Stage)(getParent().getScene().getWindow())).setFullScreen(false);
        }
        });
    }    

    @Override
    public void initialize() {
        
    }
    
}
