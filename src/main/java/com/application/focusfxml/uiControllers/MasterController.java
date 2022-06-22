package com.application.focusfxml.uiControllers;

import com.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import static com.State.getTestState;

public class MasterController implements Initializable {
    /* GUI Elements
    * This is a comprehensive list of all elements with a function in the program, in theory all methods not
    * directly corresponding to the initialisation of the GUI should be moved into their respective sub Controller
    * denoted by itemNameController.
    *
    *
    * Strike all of that up there, this should almost be the final inheriting class that inherits every other
    * controller so that the fields are distributed in the correct places
     */
    @FXML private TreeView taskTreeView;
    @FXML private TabPane masterTabView;
    @FXML private TextField selectedTaskTitle;
    @FXML private TextField selectedTaskExpAmount;
    @FXML private ProgressBar selectedTaskProgressBar;
    @FXML private ToggleButton selectedTaskComplete;
    @FXML private TextArea selectedTaskDescription;
    @FXML private Label selectedTaskProgress;
    @FXML private DatePicker selectedTaskDueDate;
    @FXML private Label selectedTaskChildrenCount;
    @FXML private Label selectedTaskEstimatedTimeRemaining; //TODO Denotes the type of time, or hours/minutes maybe
    @FXML private ToggleButton selectedTaskManualProgress;
    @FXML private Slider selectedTaskProgressSlider;
    @FXML private TextField selectedTaskTimeDiv1;
    @FXML private TextField selectedTaskTimeDiv2;
    @FXML private TreeView subTaskTreeView;

    protected State state;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public MasterController() {
        this.state = null;
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
        //TODO Commnent this out when the time comes!!!!
        this.state = getTestState();
        //---------------------------
        TaskMainTreeViewController.treeStateInit(taskTreeView,this.state);
        this.selectedTaskTitle = new TextField();

    }

    //GETTERS AND SETTERS FOR ALL FIELDS BELOW THIS POINT

    public TreeView getTaskTreeView() {
        return taskTreeView;
    }

    public void setTaskTreeView(TreeView taskTreeView) {
        this.taskTreeView = taskTreeView;
    }

    public TabPane getMasterTabView() {
        return masterTabView;
    }

    public void setMasterTabView(TabPane masterTabView) {
        this.masterTabView = masterTabView;
    }

    public TextField getSelectedTaskTitle() {
        return selectedTaskTitle;
    }

    public void setSelectedTaskTitle(TextField selectedTaskTitle) {
        this.selectedTaskTitle = selectedTaskTitle;
    }

    public TextField getSelectedTaskExpAmount() {
        return selectedTaskExpAmount;
    }

    public void setSelectedTaskExpAmount(TextField selectedTaskExpAmount) {
        this.selectedTaskExpAmount = selectedTaskExpAmount;
    }

    public ProgressBar getSelectedTaskProgressBar() {
        return selectedTaskProgressBar;
    }

    public void setSelectedTaskProgressBar(ProgressBar selectedTaskProgressBar) {
        this.selectedTaskProgressBar = selectedTaskProgressBar;
    }

    public ToggleButton getSelectedTaskComplete() {
        return selectedTaskComplete;
    }

    public void setSelectedTaskComplete(ToggleButton selectedTaskComplete) {
        this.selectedTaskComplete = selectedTaskComplete;
    }

    public TextArea getSelectedTaskDescription() {
        return selectedTaskDescription;
    }

    public void setSelectedTaskDescription(TextArea selectedTaskDescription) {
        this.selectedTaskDescription = selectedTaskDescription;
    }

    public Label getSelectedTaskProgress() {
        return selectedTaskProgress;
    }

    public void setSelectedTaskProgress(Label selectedTaskProgress) {
        this.selectedTaskProgress = selectedTaskProgress;
    }

    public DatePicker getSelectedTaskDueDate() {
        return selectedTaskDueDate;
    }

    public void setSelectedTaskDueDate(DatePicker selectedTaskDueDate) {
        this.selectedTaskDueDate = selectedTaskDueDate;
    }

    public Label getSelectedTaskChildrenCount() {
        return selectedTaskChildrenCount;
    }

    public void setSelectedTaskChildrenCount(Label selectedTaskChildrenCount) {
        this.selectedTaskChildrenCount = selectedTaskChildrenCount;
    }

    public Label getSelectedTaskEstimatedTimeRemaining() {
        return selectedTaskEstimatedTimeRemaining;
    }

    public void setSelectedTaskEstimatedTimeRemaining(Label selectedTaskEstimatedTimeRemaining) {
        this.selectedTaskEstimatedTimeRemaining = selectedTaskEstimatedTimeRemaining;
    }

    public ToggleButton getSelectedTaskManualProgress() {
        return selectedTaskManualProgress;
    }

    public void setSelectedTaskManualProgress(ToggleButton selectedTaskManualProgress) {
        this.selectedTaskManualProgress = selectedTaskManualProgress;
    }

    public Slider getSelectedTaskProgressSlider() {
        return selectedTaskProgressSlider;
    }

    public void setSelectedTaskProgressSlider(Slider selectedTaskProgressSlider) {
        this.selectedTaskProgressSlider = selectedTaskProgressSlider;
    }

    public TextField getSelectedTaskTimeDiv1() {
        return selectedTaskTimeDiv1;
    }

    public void setSelectedTaskTimeDiv1(TextField selectedTaskTimeDiv1) {
        this.selectedTaskTimeDiv1 = selectedTaskTimeDiv1;
    }

    public TextField getSelectedTaskTimeDiv2() {
        return selectedTaskTimeDiv2;
    }

    public void setSelectedTaskTimeDiv2(TextField selectedTaskTimeDiv2) {
        this.selectedTaskTimeDiv2 = selectedTaskTimeDiv2;
    }

    public TreeView getSubTaskTreeView() {
        return subTaskTreeView;
    }

    public void setSubTaskTreeView(TreeView subTaskTreeView) {
        this.subTaskTreeView = subTaskTreeView;
    }
}
