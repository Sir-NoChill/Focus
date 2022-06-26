package com.application.focusfxml.uiControllers;

import com.Profile;
import elementStructure.Element;
import elementStructure.TaskSuperclass;
import elementStructure.tasks.SuperList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import uiClassExtensions.ElementTreeCellFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Stack;

public class TaskMainTreeViewController extends AbstractController implements Initializable {

    @FXML private TreeView<Element> taskTreeView;
    private Element currentSelection;

    @FXML private TitledPane selectedTaskAccordionViewOverview;
    @FXML private Label selectedTaskExpRemaining;
    @FXML private ProgressBar selectedTaskProgressBar;
    @FXML private Label selectedTaskProgress;
    @FXML private Label selectedTaskChildrenCount;
    @FXML private Label selectedTaskEstimatedTimeRemaining; //TODO Denotes the type of time, or hours/minutes maybe
    @FXML private Slider selectedTaskProgressSlider;
    @FXML private TreeView subTaskTreeView;
    @FXML private CheckBox selectedTaskComplete;
    @FXML private Button selectedTaskEditButton;

    private MasterController masterController;


    protected boolean creation;

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
        taskTreeView.setRoot(setTaskTreeView(profile));
        taskTreeView.setEditable(true);
        taskTreeView.setShowRoot(false);
        setCellFactory(taskTreeView);
        setFocusListener(taskTreeView);
        setCellFactory(subTaskTreeView);

        progressSliderValueListener();
        completedCheckboxListener();
    }
    /**
     *
     * @param profile
     * @return a tree item with chidren corresponding to each element contained in a state
     */
    //Needs to be indirectly recursive
    //FCN call{
    //  if true
    //      element.add
    //      element.addChildren
    //  else
    //      element.add
    //EFFECTS: adds all elements in the state to the treeView
    public TreeItem<Element> setTaskTreeView(Profile profile) {
        TreeItem<Element> root = new TreeItem<>(new SuperList());

        root.setExpanded(true);

        while (!profile.getElements().isEmpty()) {
            addTreeItems(root, profile.getElements().pop());
        }

        return root;
    }

    private TreeItem<Element> buildSubTaskTreeView(Element element) {
        TreeItem<Element> root = new TreeItem<>(element);
        root.setExpanded(true);

        Stack<Element> elementStack = new Stack<>();
        if (TaskSuperclass.class.isAssignableFrom(element.getClass())) {
            for (Element child : ((TaskSuperclass) element).getChildren()) {
                elementStack.push(child);
            }
        }

        while (!elementStack.isEmpty()) {
            addTreeItems(root,elementStack.pop());
        }

        return root;
    }

    /**
     * The recursive method to add elements to parent elements and tree elements
     * @param parent
     * @param child
     */
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
     * sets the cell factory of taskTreeView to ElementTreeCellFactory
     * @param taskTreeView
     */
    void setCellFactory(TreeView taskTreeView) {
        taskTreeView.setCellFactory(new Callback<TreeView, TreeCell>() {
            @Override
            public TreeCell<Element> call(TreeView param) {
                return new ElementTreeCellFactory();
            }
        });
    }

    /**
     * adds a change listener to the taskTreeView for listening purposes across the program
     *
     * Note that this may not be strictly necessary as the getSelectedItem might be able to replace this,
     * however the listener may be necessary for the call to be made
     * @param taskTreeView
     */
    private void setFocusListener(TreeView taskTreeView) {
        taskTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<Element>>() {
            /**
             * Called when the value of an {@link ObservableValue} changes.
             * <p>
             * In general, it is considered bad practice to modify the observed value in
             * this method.
             *
             * @param observable The {@code ObservableValue} which value changed
             * @param oldValue   The old value
             * @param newValue   The new value
             */
            @Override
            public void changed(ObservableValue<? extends TreeItem<Element>> observable, TreeItem<Element> oldValue, TreeItem<Element> newValue) {
                currentSelection = newValue.getValue();
                update();
            }
        });
    }



    private void completedCheckboxListener() {
        selectedTaskComplete.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    profile.complete(currentSelection);
                    update();
                } else {
                    profile.unComplete(currentSelection);
                    update();
                }
            }
        });
    }

    private void progressSliderValueListener() {
        selectedTaskProgressSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                currentSelection.setProgress(Double.parseDouble(String.valueOf(newValue)));
                update();
            }
        });
    }

    void update() {
        Element e = currentSelection;

        setSelectedTaskChildrenCount(e);
        setSelectedTaskEstimatedTimerRemaining(e);
        setSelectedTaskProgressBar(e);
        setSelectedTaskProgress(e);
        setSelectedTaskProgressSlider(e);
        setSelectedTaskSubTaskTreeView(e);
        setSelectedTaskComplete(e);
        setSelectedTaskExpValue(e);
        setSplitViewOverviewTitle(e);

        taskTreeView.refresh();

        masterController.update();
    }

    private void setSplitViewOverviewTitle(Element element) {
        selectedTaskAccordionViewOverview.setText("Overview: " + element.getTitle());
    }

    private void setSelectedTaskExpValue(Element element) {
        int exp = element.getRemainingExp();
        selectedTaskExpRemaining.setText(String.valueOf(exp));
    }

    private void setSelectedTaskProgressBar(Element element) {
        double progress = element.getProgress();
        selectedTaskProgressBar.setProgress(progress/100);
    }

    private void setSelectedTaskProgress(Element element) {
        double progress = element.getProgress();
        selectedTaskProgress.setText(String.valueOf(progress) + "%");
    }

    private void setSelectedTaskChildrenCount(Element element) {
        Iterator<Element> elementIterator = element.createIterator();

        int i = 0;
        while (elementIterator.hasNext()) {
            i++;
            elementIterator.next();
        }

        selectedTaskChildrenCount.setText(String.valueOf(i));
    }

    private void setSelectedTaskEstimatedTimerRemaining(Element element) {
        double time = element.getTime();
        selectedTaskEstimatedTimeRemaining.setText(String.valueOf(time));
    }

    private void setSelectedTaskProgressSlider(Element element) {
        if (TaskSuperclass.class.isAssignableFrom(element.getClass())) {
            selectedTaskProgressSlider.setDisable(true);
        } else {
            selectedTaskProgressSlider.setDisable(false);
        }
        double progress = element.getProgress();
        selectedTaskProgressSlider.setMin(0);
        selectedTaskProgressSlider.setMax(100);
        selectedTaskProgressSlider.setValue(progress);
    }

    private void setSelectedTaskSubTaskTreeView(Element element) {
        TreeItem<Element> root = buildSubTaskTreeView(element);
        subTaskTreeView.setRoot(root);
        //TODO this needs to notify the entire tree if the user adds something
        // so that the nodes can dynamically update

        //IDEAS note that this might work better if it were a tree grid view or whatever
        // That  would allow me to place progress etc next to each tree node
    }

    private void setSelectedTaskComplete(Element element) {
        boolean complete = element.isComplete();
        this.selectedTaskComplete.setSelected(complete);
    }

    @FXML
    void setSelectedTaskProgress(DragEvent event) {
        if (event.isDropCompleted()) {
            currentSelection.setProgress(selectedTaskProgressSlider.getValue());
            update();
            System.out.println("Dragged");
        }
    }

    @FXML
    void openEditDialogue(ActionEvent event) throws IOException {
        creation = false;
//        FXMLLoader fxmlLoader = new FXMLLoader(FocusApplication.class.getResource("focus_EditItemDialogue.fxml"));
//
//        EditDialogueController controller = new EditDialogueController();
//        controller.setCurrentParentSelection(this.currentSelection);
//        fxmlLoader.setController(controller);
        setupInjector();
        Parent root = Injector.load("focus_EditItemDialogue.fxml");

        Stage stage = new Stage();

        stage.setScene(new Scene(root,600,500));
        stage.setTitle("Edit Task");
        stage.show();
    }

    private void setupInjector() {
        Injector.setBundle(ResourceBundle.getBundle("focusfxml"));
        Callback<Class<?>,Object> controllerFactory = param -> {
            //TODO Get Data (this will be from the saved file eventually
            // Right now the state is from a static method in the abstract controller
            EditDialogueController controller = new EditDialogueController();
            controller.setCurrentParentSelection(this.currentSelection);
            controller.setParent(this);
            return controller;
        };

        Injector.addInjectionMethod(
                EditDialogueController.class,controllerFactory
        );
    }

    @FXML
    void completeTask(ActionEvent event) {
    }

    /////GETTERS AND SETTERS AS NECESSARY


    public void setMasterController(MasterController masterController) {
        this.masterController = masterController;
    }
}
