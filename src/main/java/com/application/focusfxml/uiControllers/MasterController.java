package com.application.focusfxml.uiControllers;

import com.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import static com.State.getTestState;

public class MasterController extends AbstractController implements Initializable {
    /* GUI Elements
    * This is a comprehensive list of all elements with a function in the program, in theory all methods not
    * directly corresponding to the initialisation of the GUI should be moved into their respective sub Controller
    * denoted by itemNameController.
    *
    *
    * Strike all of that up there, this should almost be the final inheriting class that inherits every other
    * controller so that the fields are distributed in the correct places
    *
    * Strike that again, what we are going to do is make a whole shwack of classes that define methods for
    * each of the elements contained within the scene
     */
    @FXML private TabPane masterTabView;
    @FXML private TaskMainTreeViewController treeViewController;

    public MasterController() {
        super();
    }


    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //FOR TESTING PURPOSES ONLY!!!!
        //TODO Comment this out when the time comes!!!!
        this.state = getTestState();
        treeViewController = new TaskMainTreeViewController();
    }

    //---------------------------------------------------//
    //GETTERS AND SETTERS FOR ALL FIELDS BELOW THIS POINT//
    //---------------------------------------------------//


    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public TabPane getMasterTabView() {
        return masterTabView;
    }

    public void setMasterTabView(TabPane masterTabView) {
        this.masterTabView = masterTabView;
    }
}
