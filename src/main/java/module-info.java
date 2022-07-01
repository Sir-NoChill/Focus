module com.application.focusfxml {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;

    exports com.application.focusfxml;
    exports com.application.focusfxml.uiControllers;
    exports com to com.fasterxml.jackson.databind;
    exports elementStructure to com.fasterxml.jackson.databind;
    exports elementStructure.tasks to com.fasterxml.jackson.databind;
    exports elementStructure.material to com.fasterxml.jackson.databind;

    opens com.application.focusfxml.uiControllers to javafx.fxml;
    opens com to com.fasterxml.jackson.databind;
    opens elementStructure to com.fasterxml.jackson.databind;
}