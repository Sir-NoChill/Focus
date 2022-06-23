package com.application.focusfxml;

import com.application.focusfxml.uiControllers.MasterController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FocusApplication extends Application {
    private MasterController masterController;
    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param stage the primary stage for this application, onto which
     *                     the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages.
     * @throws Exception if something goes wrong
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("GUIv3.fxml")//,
                //ResourceBundle.getBundle("focusfxml"),
        );

        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        controllerInit();
//        Put a loading sequence here that loads the last state if it is available, if not, start a new state
//        Loading sequence should load a new MasterController which holds a field that is a State
//        State state;
//        if (lastStateAvailable) {
//            load(lastState);
//        } else {
//            loadDefaultState();
//        }

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    private void controllerInit() {
        this.masterController = new MasterController();
    }

    public static void main(String[] args) {
        launch();
    }
}
