module cr.ac.una.fantasydefender {
    
    requires java.base;
    //Javafx
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires javafx.media;

    requires MaterialFX;
    requires atlantafx.base;
    
    requires java.instrument;

    requires jakarta.mail;
    requires jakarta.activation;
    
    //Source Package
    opens cr.ac.una.fantasydefender to javafx.fxml;

    //Persistence
    requires jakarta.persistence;
    requires eclipselink;
    //Sub-packages 
    opens cr.ac.una.fantasydefender.controller to javafx.fxml;
    
    exports cr.ac.una.fantasydefender.model;
    opens cr.ac.una.fantasydefender.model;
    
    exports cr.ac.una.fantasydefender;
    

}
