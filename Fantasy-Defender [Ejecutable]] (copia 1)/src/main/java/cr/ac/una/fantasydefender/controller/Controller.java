/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.fantasydefender.controller;

import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import cr.ac.una.fantasydefender.util.childViewInterface;

public abstract class Controller {

    private Stage stage;
    private String accion;
    private String nombreVista;
    private Controller parent;
    private BorderPane bPane; // added this to communicate the pane used to project a child scene in
    private Controller child;
    boolean childState;

    public boolean isChildState() {
        return childState;
    }

    public void setChildState(boolean childState) {
        this.childState = childState;
    }

    public Controller getChild() {
        return child;
    }

    public void setChild(Controller child) {
        this.child = child;
    }
    
    //this fucntion is used in the flow controller to tell this controller what pane is used for a child scene to be projected
    public void setChildBorderPane(BorderPane bPane){
        this.bPane = bPane;
    }
    //this method is used by the child to comunicate with it´s parent
    public Controller getParent(){
        return parent;
    }
    //this is to hide the pane used to project the child
    public void hideChildView() {
        bPane.setVisible(false);
        bPane.setManaged(false);
        childState = false;
        child.clean();
    }
    public void clean(){};
    //this is used to bring back the child view
    public void showChildView(){
        bPane.setVisible(true);
        bPane.setManaged(true);
        childState=true;
        System.out.println("child shown");
    }
    //this method is used to tell the child controller who it's parent is.
    public void setParent(Controller parent){
        this.parent = parent;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public String getNombreVista() {
        return nombreVista;
    }

    public void setNombreVista(String nombreVista) {
        this.nombreVista = nombreVista;
    }
    
    public void sendTabEvent(KeyEvent event) {
        event.consume();
        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, null, null, KeyCode.TAB, false, false, false, false);
        ((Control) event.getSource()).fireEvent(keyEvent);
    }

    public abstract void initialize();
}