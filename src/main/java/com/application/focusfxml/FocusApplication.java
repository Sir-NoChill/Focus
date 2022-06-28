package com.application.focusfxml;

import com.Profile;
import com.application.focusfxml.uiControllers.AbstractController;
import com.application.focusfxml.uiControllers.Injector;
import com.application.focusfxml.uiControllers.MasterController;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ResourceBundle;

public class FocusApplication extends Application {
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
        setupInjector();

        Parent root = Injector.load("focus_MainView(GUIv6).fxml");
        stage.setTitle("Focus");
        stage.setScene(new Scene(root,1000,800));
        stage.show();
    }

    //IDEAS I am not 100% sure that the interface injection idea I have going on here is the right one
    private void setupInjector() {
        Injector.setBundle(ResourceBundle.getBundle("focusfxml"));
        Callback<Class<?>,Object> controllerFactory = param -> {
            //TODO Get Data (this will be from the saved file eventually
            // Right now the state is from a static method in the abstract controller
            AbstractController.setProfile(Profile.getTestState());
            MasterController controller = new MasterController();
            return controller;
        };

        Injector.addInjectionMethod(
                MasterController.class,controllerFactory
        );
    }

    public static void main(String[] args) {
        launch();
    }
}
