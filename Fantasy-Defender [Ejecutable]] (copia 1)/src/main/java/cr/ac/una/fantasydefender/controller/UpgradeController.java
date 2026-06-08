package cr.ac.una.fantasydefender.controller;

import cr.ac.una.fantasydefender.model.GameDTO;
import cr.ac.una.fantasydefender.service.CastleService;
import cr.ac.una.fantasydefender.service.CrossbowService;
import cr.ac.una.fantasydefender.service.GameService;
import cr.ac.una.fantasydefender.util.AppContext;
import cr.ac.una.fantasydefender.util.Mensaje;
import cr.ac.una.fantasydefender.util.Respuesta;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author andys
 */
public class UpgradeController extends Controller implements Initializable {

    @FXML
    private Text txtHealth;
    @FXML
    private Text txtElixir;
    @FXML
    private Text txtSpeed;
    @FXML
    private Text txtDamage;
    @FXML
    private Text txtMeteor;
    @FXML
    private Text txtFrost;
    @FXML
    private Text txtPoints;
    @FXML
    private Pane healthHover;
    @FXML
    private Pane healthSprite;
    @FXML
    private Pane elixirHover;
    @FXML
    private Pane elixirSprite;
    @FXML
    private Pane speedHover;
    @FXML
    private Pane damageHover;
    @FXML
    private Pane meteorHover;
    @FXML
    private Pane frostHover;
    @FXML
    private Pane cancelButton;
    @FXML
    private Pane acceptButton;
    @FXML
    private VBox speedSprite;
    @FXML
    private VBox damageSprite;
    @FXML
    private VBox meteorSprite;
    @FXML
    private VBox frostSprite;
    
    
    private GameDTO gameDTO = (GameDTO)AppContext.getInstance().get("SelectedGame");
        
    private int realPoints;
    private int realPriceMulti;
    private int realHealthLvl;
    private int realElixirLvl;
    private int realFrecuencyLvl;
    private int realDamageLvl;
    private int realMeteorLvl;
    private int realFrostLvl;
    
    private int auxPoints;
    private int auxHealth;
    private int auxElixir;
    private int auxSpeed;
    private int auxDamage;
    private int auxMeteor;
    private int auxFrost;
    @FXML
    private Pane leaveButton;
    
    
    //----------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        resetAuxiliaries();
        resetLevels();
        setHover();
        updateFeedText();
    }    
    //----------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void initialize() {
        setNombreVista("Upgrade View");
    }
    //----------------------------------------------------------------------------------------------------------------------------------------
    private void resetAuxiliaries(){//  this method resets all auxiliary variables that are used to follow the changes on the scene
        auxPoints = 0;
        auxHealth = 0;
        auxElixir = 0;
        auxSpeed = 0;
        auxDamage = 0;
        auxMeteor = 0;
        auxFrost = 0;
    }
    //----------------------------------------------------------------------------------------------------------------------------------------
    private void resetLevels(){
        realPriceMulti=10;
        realPoints = gameDTO.getPoints();
        realHealthLvl=gameDTO.getCastle().getHealthLevel();
        realElixirLvl=gameDTO.getCastle().getElixerLevel();
        realFrecuencyLvl=gameDTO.getCrossbow().getFrecuencyLvl();
        realDamageLvl=gameDTO.getCrossbow().getDamageLvl();
        realMeteorLvl=gameDTO.getMeteorLevel();
        realFrostLvl=gameDTO.getIceLevel();
    } 
    //----------------------------------------------------------------------------------------------------------------------------------------
    private void updateFeedText(){//this method updates the changes into the Text components of the view
        txtPoints.setText(realPoints + "Pts. --> " + (realPoints - auxPoints) + "Pts.");
        txtHealth.setText("Health Lvl." + (realHealthLvl + auxHealth)   + " (↑" +(realHealthLvl + auxHealth)*realPriceMulti +   "pts.)");
        txtElixir.setText("Elixir Lvl." + (realElixirLvl + auxElixir)   + " (↑" +(realElixirLvl + auxElixir)*realPriceMulti +   "pts.)");
        txtSpeed.setText ("Speed Lvl."  + (realFrecuencyLvl  + auxSpeed)    + " (↑" +(realFrecuencyLvl  + auxSpeed) *realPriceMulti +   "pts.)");
        txtDamage.setText("Damage Lvl." + (realDamageLvl + auxDamage)   + " (↑" +(realDamageLvl + auxDamage)*realPriceMulti +   "pts.)");
        txtMeteor.setText("Meteor Lvl." + (realMeteorLvl + auxMeteor)   + " (↑" +(realMeteorLvl + auxMeteor)*realPriceMulti +   "pts.)");
        txtFrost.setText ("Frost Lvl."  + (realFrostLvl  + auxFrost)    + " (↑" +(realFrostLvl  + auxFrost) *realPriceMulti +   "pts.)");
    }
    //----------------------------------------------------------------------------------------------------------------------------------------
    private void setHover(){//this part activates or deactivates the hover effect on each button based on if there are enough points for it
        if (realPoints - auxPoints >= 0){
            if((realPoints - auxPoints) < ((realHealthLvl + auxHealth)*realPriceMulti)){healthHover.setVisible(false);}
            else{healthHover.setVisible(true);}
            if((realPoints - auxPoints) < ((realElixirLvl + auxElixir)*realPriceMulti)){elixirHover.setVisible(false);}
            else{elixirHover.setVisible(true);}
            if((realPoints - auxPoints) < ((realFrecuencyLvl  + auxSpeed) *realPriceMulti)){speedHover.setVisible (false);}
            else{speedHover.setVisible (true);}
            if((realPoints - auxPoints) < ((realDamageLvl + auxDamage)*realPriceMulti)){damageHover.setVisible(false);}
            else{damageHover.setVisible(true);}
            if((realPoints - auxPoints) < ((realMeteorLvl + auxMeteor)*realPriceMulti)){meteorHover.setVisible(false);}
            else{meteorHover.setVisible(true);}
            if((realPoints - auxPoints) < ((realFrostLvl  + auxFrost) *realPriceMulti)){frostHover.setVisible (false);}
            else{frostHover.setVisible (true);}
        }else{
            System.out.println("more points selected than there are points available");
        }
    }
    //----------------------------------------------------------------------------------------------------------------------------------------
    @FXML
    private void onMouseReleasedHealth(MouseEvent event) {
        if (healthSprite.isHover()) {//checks if the mouse is hovering, if it isn't the click release does nothing
            if((realPoints - auxPoints) >= ((realHealthLvl + auxHealth)*realPriceMulti)){
                auxPoints += ((realHealthLvl + auxHealth)*realPriceMulti);
                auxHealth++;
                setHover();
                updateFeedText();
            }
        }
    }
    //-------------------------------------------------------------------------------------------------------------------------------------------
    @FXML
    private void onMouseReleasedElixir(MouseEvent event) {
        if (elixirSprite.isHover()) {//checks if the mouse is hovering, if it isn't the click release does nothing
            if((realPoints - auxPoints) >= ((realElixirLvl + auxElixir)*realPriceMulti)){
                auxPoints += ((realElixirLvl + auxElixir)*realPriceMulti);
                auxElixir++;
                setHover();
                updateFeedText();
            }
        }
    }
    //-------------------------------------------------------------------------------------------------------------------------------------------
    @FXML
    private void onMouseReleasedSpeed(MouseEvent event) {
        if (speedSprite.isHover()) {//checks if the mouse is hovering, if it isn't the click release does nothing
            if((realPoints - auxPoints) >= ((realFrecuencyLvl + auxSpeed)*realPriceMulti)){
                auxPoints += ((realFrecuencyLvl + auxSpeed)*realPriceMulti);
                auxSpeed++;
                setHover();
                updateFeedText();
            }
        }
    }
    //-------------------------------------------------------------------------------------------------------------------------------------------
    @FXML
    private void onMouseReleasedDamage(MouseEvent event) {
        if (damageSprite.isHover()) {//checks if the mouse is hovering, if it isn't the click release does nothing
            if((realPoints - auxPoints) >= ((realDamageLvl + auxDamage)*realPriceMulti)){
                auxPoints += ((realDamageLvl + auxDamage)*realPriceMulti);
                auxDamage++;
                setHover();
                updateFeedText();
            }
        }
    }
    //-------------------------------------------------------------------------------------------------------------------------------------------
    @FXML
    private void onMouseReleasedMeteor(MouseEvent event) {
        if (meteorSprite.isHover()) {//checks if the mouse is hovering, if it isn't the click release does nothing
            if((realPoints - auxPoints) >= ((realMeteorLvl + auxMeteor)*realPriceMulti)){
                auxPoints += ((realMeteorLvl + auxMeteor)*realPriceMulti);
                auxMeteor++;
                setHover();
                updateFeedText();
            }
        }
    }
    //-------------------------------------------------------------------------------------------------------------------------------------------
    @FXML
    private void onMouseReleasedFrost(MouseEvent event) {
        if (frostSprite.isHover()) {//checks if the mouse is hovering, if it isn't the click release does nothing
            if((realPoints - auxPoints) >= ((realFrostLvl + auxFrost)*realPriceMulti)){
                auxPoints += ((realFrostLvl + auxFrost)*realPriceMulti);
                auxFrost++;
                setHover();
                updateFeedText();
            }
        }
    }
    //-------------------------------------------------------------------------------------------------------------------------------------------
    @FXML
    private void onMouseReleasedCancel(MouseEvent event) {
        if (cancelButton.isHover()) {
            getParent().hideChildView();
        }
    }
    //-------------------------------------------------------------------------------------------------------------------------------------------
    @FXML
    private void onMouseReleasedAccept(MouseEvent event) {
        if (acceptButton.isHover()) {//checks if the mouse is hovering, if it isn't the click release does nothing
            getParent().hideChildView();//this efectivly clears the login view
            saveUpgrades();
        }
    }
    
    
    private void saveUpgrades(){
        this. gameDTO.getCastle().setHealthLevel(realHealthLvl + auxHealth);
        this.gameDTO.getCastle().setElixerLevel(realElixirLvl + auxElixir);

        this.gameDTO.getCrossbow().setDamageLvl(realDamageLvl + auxDamage);
        this. gameDTO.getCrossbow().setFrecuencyLvl(realFrecuencyLvl + auxSpeed);

        this. gameDTO.setMeteorLevel(realMeteorLvl + auxMeteor);
        this.gameDTO.setIceLevel(realFrostLvl + auxFrost);

        this.gameDTO.setPoints(realPoints - auxPoints);
        
        saveCastle();
        saveCrossbow();
        saveGame();
    }
    
    private void saveGame(){
        try{
            GameService gameservice = new GameService();
            
            Respuesta res = gameservice.saveGame(gameDTO);
            
            if(res.getEstado())
                new Mensaje().showConfirmation("Successfully Upgrades", getStage(), "The Changes were Successfully implemented");
              
        }catch(Exception ex){
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, "Invalid Game To Apply Changes", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Save Castle", getStage(), "An Error Occurred During Save Upgrade Changes");
        }
    }
    

    private void saveCrossbow(){
        try{
            CrossbowService crossbowService = new CrossbowService();
            
            Respuesta res = crossbowService.saveCrossbow(gameDTO.getCrossbow());
            
            if(!res.getEstado())
                System.out.println(res.getMensajeInterno());
              
        }catch(Exception ex){
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, "Invalid Game To Apply Changes", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Save Castle", getStage(), "An Error Occurred During Save Upgrade Changes");
        }
    }    
    
        private void saveCastle(){
        try{
            CastleService castleService = new CastleService();
            
            Respuesta res = castleService.saveCastle(gameDTO.getCastle());
            
            if(!res.getEstado())
                System.out.println(res.getMensajeInterno());
              
        }catch(Exception ex){
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, "Invalid Game To Apply Changes", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Save Castle", getStage(), "An Error Occurred During Save Upgrade Changes");
        }
    }    

    @FXML
    private void onMouseReleasedLeave(MouseEvent event) {
    }
}
