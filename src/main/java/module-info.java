module com.example.focus {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires se.michaelthelin.spotify;

    opens com.application.focus to javafx.fxml;
    exports com.application.focus;
}