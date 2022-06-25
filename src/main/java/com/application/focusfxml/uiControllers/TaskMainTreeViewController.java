package com.application.focusfxml.uiControllers;

import com.State;
import elementStructure.Element;
import elementStructure.TaskSuperclass;
import elementStructure.tasks.SuperList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.Callback;
import uiClassExtensions.ElementTreeCellFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TaskMainTreeViewController extends AbstractController implements Initializable {

    @FXML private TreeView taskTreeView;


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
        taskTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                Element e = (Element) ((TreeItem) newValue).getValue();
            }
        });
    }

    public Element getSelectedElement(TreeView treeView) {
        TreeItem elementTreeItem = (TreeItem) treeView.getSelectionModel().getSelectedItem();
        Element element = (Element) elementTreeItem.getValue();

        return element;
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
    }
}
