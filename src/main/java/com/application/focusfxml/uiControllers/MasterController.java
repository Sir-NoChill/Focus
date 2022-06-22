package com.application.focusfxml.uiControllers;

import com.State;
import elementStructure.Element;
import elementStructure.TaskSuperclass;
import elementStructure.tasks.SuperList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import uiClassExtensions.ElementTreeCellFactory;

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
    @FXML public TabPane masterTabView;
    @FXML public TextField selectedTaskTitle;
    @FXML public TextField selectedTaskExpAmount;
    @FXML public ProgressBar selectedTaskProgressBar;
    @FXML public ToggleButton selectedTaskComplete;
    @FXML public TextArea selectedTaskDescription;
    @FXML public Label selectedTaskProgress;
    @FXML public DatePicker selectedTaskDueDate;
    @FXML public Label selectedTaskChildrenCount;
    @FXML public Label selectedTaskEstimatedTimeRemaining; //TODO Denotes the type of time, or hours/minutes maybe
    @FXML public ToggleButton selectedTaskManualProgress;
    @FXML public Slider selectedTaskProgressSlider;
    @FXML public TextField selectedTaskTimeDiv1;
    @FXML public TextField selectedTaskTimeDiv2;
    @FXML public TreeView subTaskTreeView;

    protected State state;
    public TabPaneController tabPaneController;
    public TreeViewController treeViewController;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public MasterController() {
        this.state = null;
    }

    //Needs to be indirectly recursive
    //FCN call{
    //  if true
    //      element.add
    //      element.addChildren
    //  else
    //      element.add
    //EFFECTS: adds all elements in the state to the treeView
    public TreeItem<Element> setTaskTreeView() {
        TreeItem<Element> root = new TreeItem<>(new SuperList());

        root.setExpanded(true);

        while (!this.state.getElements().isEmpty()) {
            addTreeItems(root, state.getElements().pop());
        }

        return root;
    }

    //Recursive call to be executed on the root node with all the elements from the State, which should be divided into
    //two different SuperLists, one for completed = true, and one for completed = false
    private void addTreeItems(TreeItem<Element> parent, Element child) {
        if (TaskSuperclass.class.isAssignableFrom(child.getClass())) {
            TreeItem<Element> addedItem = new TreeItem<>(child);
            parent.getChildren().add(addedItem);
            for (Element element : ((TaskSuperclass) child).getChildren()) {
                addTreeItems(addedItem, element);
            }
        } else {
            parent.getChildren().add(new TreeItem<>(child));
        }
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
        treeStateInit();
        this.selectedTaskTitle = new TextField();

    }

    private void treeStateInit() {
        taskTreeView.setRoot(setTaskTreeView());
        taskTreeView.setEditable(true);
        taskTreeView.setCellFactory(new Callback<TreeView, TreeCell>() {
            @Override
            public TreeCell<Element> call(TreeView param) {
                return new ElementTreeCellFactory();
            }
        });
        taskTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                Element e = (Element) ((TreeItem) newValue).getValue();
            }
        });
    }
}
