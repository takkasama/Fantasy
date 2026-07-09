package cr.ac.una.fantasydefender.controller;

import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXSlider;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

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
        // TODO
    }    

    @Override
    public void initialize() {
    }
    
}
