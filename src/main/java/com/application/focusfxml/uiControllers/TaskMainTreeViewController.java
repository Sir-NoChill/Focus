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
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Stack;

public class TaskMainTreeViewController extends AbstractController implements Initializable {

    @FXML private TreeView<Element> taskTreeView;
    private Element currentSelection;

    @FXML private ProgressBar selectedTaskProgressBar;
    @FXML private Label selectedTaskProgress;
    @FXML private Label selectedTaskChildrenCount;
    @FXML private Label selectedTaskEstimatedTimeRemaining; //TODO Denotes the type of time, or hours/minutes maybe
    @FXML private Slider selectedTaskProgressSlider;
    @FXML private TreeView subTaskTreeView;
    @FXML private CheckBox selectedTaskComplete;


    public TaskMainTreeViewController() {
        this.currentSelection = null;
    }

    ///////////////////////////////////////////////////////
    //                                                   //
    //        STATIC METHODS                             //
    //                                                   //
    ///////////////////////////////////////////////////////


    /**
     *
     * @param state
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
    public TreeItem<Element> setTaskTreeView(State state) {
        TreeItem<Element> root = new TreeItem<>(new SuperList());

        root.setExpanded(true);

        while (!state.getElements().isEmpty()) {
            addTreeItems(root, state.getElements().pop());
        }

        return root;
    }

    public TreeItem<Element> buildSubTaskTreeView(Element element) {
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
    private void setCellFactory(TreeView taskTreeView) {
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
        taskTreeView.setRoot(setTaskTreeView(state));
        taskTreeView.setEditable(true);
        setCellFactory(taskTreeView);
        setFocusListener(taskTreeView);
        setCellFactory(subTaskTreeView);
    }

    private void update() {
        Element e = currentSelection;

        setSelectedTaskProgressBar(e);
        setSelectedTaskProgress(e);
        setSelectedTaskChildrenCount(e);
        setSelectedTaskEstimatedTimerRemaining(e);
        setSelectedTaskProgressSlider(e);
        setSelectedTaskSubTaskTreeView(e);
        setSelectedTaskComplete(e);
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
    }

    private void setSelectedTaskComplete(Element element) {
        boolean complete = element.isComplete();
        this.selectedTaskComplete.setSelected(complete);
    }

}
