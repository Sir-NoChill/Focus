module com.example.focusfxml {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.application.focusfxml to javafx.fxml;
    exports com.application.focusfxml;
    exports com.application.focusfxml.uiControllers;
    opens com.application.focusfxml.uiControllers to javafx.fxml;
}