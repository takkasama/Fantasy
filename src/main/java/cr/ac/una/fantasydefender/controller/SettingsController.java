package cr.ac.una.fantasydefender.controller;

import cr.ac.una.fantasydefender.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

/**
 *
 * @author takkasama
 */
public class SettingsController  extends Controller implements Initializable {

    @FXML
    private MFXButton btnPrev;
    @FXML
    private VBox PaneVizualizer;
    @FXML
    private MFXButton btnNext;
    private List<String> nodes;
    private int indexCurrentSetting;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @Override
    public void initialize() {
        load();
    }

    @FXML
    private void onActionBtnPrev(ActionEvent event) {
        indexCurrentSetting--;
        if(indexCurrentSetting < 0)
            indexCurrentSetting = 2;
    
        FlowController.getInstance().goViewInPane(nodes.get(indexCurrentSetting), PaneVizualizer, false);
    }

    @FXML
    private void onActionBtnNext(ActionEvent event) {
        indexCurrentSetting++;
        if(indexCurrentSetting > 2)
            indexCurrentSetting = 0;
        
        FlowController.getInstance().goViewInPane(nodes.get(indexCurrentSetting), PaneVizualizer, false);
    }
    
    
    
    private void load(){
        nodes = List.of("SettingsControlView", "SettingsGeneralView", "SettingsGameView");
        indexCurrentSetting = 1;   
        
        FlowController.getInstance().goViewInPane(nodes.get(indexCurrentSetting), PaneVizualizer, false);
    }
}
