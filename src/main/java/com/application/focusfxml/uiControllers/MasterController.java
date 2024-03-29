package com.application.focusfxml.uiControllers;

import com.Profile;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import elementStructure.Element;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//TODO eliminate the Initializable interface
public class MasterController extends AbstractController implements Initializable {
    @FXML private GridPane timerViewTab;
    @FXML private TimerViewController timerViewController;
    @FXML private SplitPane treeViewTab;
    @FXML private TaskMainTreeViewController treeViewTabController;
    @FXML private Label profileTasks;
    @FXML private Label profileExp;
    @FXML private Label profileRewards;
    @FXML private TabPane masterTabView;
    @FXML private MenuItem saveCurrentProfile;
    @FXML private MenuItem editCurrentProfile;



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

        update();
        treeViewTabController.setMasterController(this);
    }

    public void update() {

        setProfileTasks();
        setProfileExp();
        setProfileRewards();
    }

    private void setProfileRewards() {
        profileRewards.setText("Coming Soon");
    }

    private void setProfileExp() {
        profileExp.setText(String.valueOf("Exp: " + profile.getProfileExp()));
    }

    private void setProfileTasks() {

        int i = 0;
        for (Element element : profile.getElements()) {
            i += element.countChildren();
        }

        profileTasks.setText("Total incomplete tasks: " + i);
    }


    public void saveProfile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");

        File file = fileChooser.showSaveDialog(masterTabView.getScene().getWindow());

        if (file != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS);

            Profile saveProfile = AbstractController.profile;
            try {
                //TODO, make the file read only and on deserialisation we can duplicate the string and read from there
                objectMapper.writeValue(file, AbstractController.profile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void editProfile(ActionEvent actionEvent) {
    }

    public void displayHelp(ActionEvent actionEvent) {
    }

    //---------------------------------------------------//
    //GETTERS AND SETTERS FOR ALL FIELDS BELOW THIS POINT//
    //---------------------------------------------------//


    public Profile getState() {
        return profile;
    }

//    public void setState(Profile profile) {
//        this.profile = profile;
//    }

    public TabPane getMasterTabView() {
        return masterTabView;
    }

    public void setMasterTabView(TabPane masterTabView) {
        this.masterTabView = masterTabView;
    }

    public void loadProfile(ActionEvent actionEvent) {
        ObjectMapper mapper = new ObjectMapper();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load");


        File file = fileChooser.showOpenDialog(masterTabView.getScene().getWindow());
        Profile loadProfile;

        try {
            loadProfile = mapper.readValue(file,Profile.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        AbstractController.setProfile(loadProfile);
        treeViewTabController.repopulateTreeView();
        update();

    }
}
