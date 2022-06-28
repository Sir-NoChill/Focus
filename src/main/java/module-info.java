module com.example.focusfxml {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;

    exports com.application.focusfxml;
    exports com.application.focusfxml.uiControllers;
    opens com.application.focusfxml.uiControllers to javafx.fxml;
}