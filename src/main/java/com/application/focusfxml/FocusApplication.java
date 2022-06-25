package com.application.focusfxml;

import com.application.focusfxml.uiControllers.Injector;
import com.application.focusfxml.uiControllers.MasterController;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ResourceBundle;

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
        setupInjector();

        Parent root = Injector.load("GUIv4.fxml");
//        FXMLLoader fxmlLoader = new FXMLLoader(
//                HelloApplication.class.getResource("GUIv3.fxml"),
//                ResourceBundle.getBundle("focusfxml"),
//                new JavaFXBuilderFactory(),
//                param -> {
//                    //This needs to return the custon Controller class
//                    //With data?
//                    //State state = State.getTestState();
//                    //return new Controller(state); //Returns a new controller with the state data
//                    return null;
//                }
//        );

//        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
//        controllerInit();
//        Put a loading sequence here that loads the last state if it is available, if not, start a new state
//        Loading sequence should load a new MasterController which holds a field that is a State
//        State state;
//        if (lastStateAvailable) {
//            load(lastState);
//        } else {
//            loadDefaultState();
//        }
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
            return new MasterController();
        };

        Injector.addInjectionMethod(
                MasterController.class,controllerFactory
        );
    }

    public static void main(String[] args) {
        launch();
    }
}
