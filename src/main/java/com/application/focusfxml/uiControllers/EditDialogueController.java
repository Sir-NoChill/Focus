package com.application.focusfxml.uiControllers;

import elementStructure.Element;
import elementStructure.MaterialSuperclass;
import elementStructure.TaskSuperclass;
import elementStructure.material.Reading;
import elementStructure.tasks.Project;
import elementStructure.tasks.SuperList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.paint.Material;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditDialogueController extends TaskMainTreeViewController implements Initializable {

    @FXML
    private Button cancelEditButton;

    @FXML
    private Button confirmEditButton;

    @FXML
    private TextField taskExpBox;

    @FXML
    private TextField taskInitialProgressBox;

    @FXML
    private TextArea taskDescription;

    @FXML
    private TextField taskTimeUnitField;

    @FXML
    private ComboBox<String> taskTypeComboBox;

    @FXML
    private TreeView<Element> taskParentSelection;
    private Element currentParentSelection;

    @FXML
    private Label timeUnitLabel;

    @FXML
    private TextField taskTitleTextField;

    @FXML Label taskProgressSetField;

    private boolean creation;

    private TaskMainTreeViewController parent;

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

        taskParentSelection.setRoot(setTaskTreeView(profile));
        taskParentSelection.setEditable(true);
        taskParentSelection.setShowRoot(false);
        setCellFactory(taskParentSelection);
        setFocusListener(taskParentSelection);

        addItemsTaskTypeCombo();

        setFieldValues(currentParentSelection);
    }

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
                currentParentSelection = newValue.getValue();
            }
        });
    }

    //SETTING ALL OF THE LABELS AND TEXT FIELDS

    //Done Add the items dependant on:
    // ----------------------------------
    //  \ creation   |     true   | false
    //   ____________|            |
    //    assignable |            |
    //     from      |            |
    //   ------------|------------|------
    //     TaskSprcls| ALL        | TASKS
    //   ------------|------------|------
    //     MatSprCls | IMPOSSIBLE | MATERIALS
    //
    private void addItemsTaskTypeCombo() {
        // Materials: Reading
        // Tasks:     Project
        if (TaskSuperclass.class.isAssignableFrom(currentParentSelection.getClass()) && !creation) {
            //Tasks
            taskTypeComboBox.getItems().addAll(
                    "Project"
            );
        } else if (MaterialSuperclass.class.isAssignableFrom(currentParentSelection.getClass()) && !creation){
            //Materials
            taskTypeComboBox.getItems().addAll(
                    "Reading"
            );
        } else if (TaskSuperclass.class.isAssignableFrom(currentParentSelection.getClass()) && creation) {
            //All
            taskTypeComboBox.getItems().addAll(
                    "Project",
                    "Reading"
            );
        } else {
            taskTypeComboBox.setDisable(true);
        }
    }

    private void setFieldValues(Element e) {
        setTitleTextField(e);
        setExpField(e);
        setTimeField(e);
        setCurrentTaskType(e);
        setDescription(e);
        setCurrentParentSelectionTreeView(e);
        setCurrentComboBoxSelection(e);
        setCurrentProgressViewLabel(e);
    }

    private void setCurrentProgressViewLabel(Element element) {
        if (creation) {
            taskProgressSetField.setText("Initial Progress:");
            taskInitialProgressBox.setText("0");
        } else {
            taskProgressSetField.setText("Progress:");
            taskInitialProgressBox.setText(String.valueOf(element.getProgress()));
        }
    }

    private void setCurrentComboBoxSelection(Element element) {
        if (element.getClass().equals(SuperList.class)) {
            taskTypeComboBox.getItems().add("Super List");
            taskTypeComboBox.getSelectionModel().select("Super List");
            taskTypeComboBox.setDisable(true);
        } else if (element.getClass().equals(Project.class)) {
            taskTypeComboBox.getSelectionModel().select("Project");
        } else if (element.getClass().equals(Reading.class)) {
            taskTypeComboBox.getSelectionModel().select("Reading");
        }
    }

    private void setCurrentParentSelectionTreeView(Element element) {
        if (!creation) {
            taskParentSelection.setDisable(true);
        } else {

        }
    }

    private void setDescription(Element element) {
        taskDescription.setText(element.getDescription());
    }

    private void setCurrentTaskType(Element e) {
//        String selection = taskTypeComboBox.getSelectionModel().getSelectedItem();
//
//        switch (selection) {
//            case "Reading":
//            case "Project":
//        }
    }

    private void setTimeField(Element element) {
        taskTimeUnitField.setText(String.valueOf(element.getTime()));
        timeUnitLabel.setText(timeUnitChooser(element));
    }

    private String timeUnitChooser(Element element) {
        if (Material.class.isAssignableFrom(element.getClass())) {
            Class<? extends Element> elementClass = element.getClass();
            if (Reading.class.equals(elementClass)) {
                return "Pages";
            } else {
                return "Unknown";
            }
        } else {
            return "Hours";
        }
    }

    private void setExpField(Element element) {
        taskExpBox.setText(String.valueOf(element.getThisTaskExp()));
    }

    private void setTitleTextField(Element element) {
        taskTitleTextField.setText(element.getTitle());
    }


    //ACTION COMMANDS BELOW
    @FXML
    void confirmEdit(ActionEvent event) {
        EventHandler<ActionEvent> handler = event1 -> {
            if (creation) {
                //TODO implement source
            } else {
                currentParentSelection.setTitle(taskTitleTextField.getText());
                currentParentSelection.setProgress(Double.parseDouble(taskInitialProgressBox.getText()));
                currentParentSelection.setExp(Integer.parseInt(taskExpBox.getText()));
                currentParentSelection.setTime(Double.parseDouble(taskTimeUnitField.getText()));
                currentParentSelection.setDescription(taskDescription.getText());
                //TODO implement the change of parent
            }
            Node source = (Node) event1.getSource();
            Stage stage = (Stage) source.getScene().getWindow();

//            stage.getOnCloseRequest().handle(null);

            parent.update();
            stage.close();
        };

        //Need to update the taskMainTreeView before the close is initiated
        handler.handle(event);

        //reusability of the method, need a creation adn an edit method, not sure if I can make that happen

    }

    @FXML
    void cancelEdit(ActionEvent event) {
        EventHandler<ActionEvent> handler = event1 -> {
            Node source = (Node) event1.getSource();
            Stage stage = (Stage) source.getScene().getWindow();

//            stage.getOnCloseRequest().handle(null);

            parent.update();
            stage.close();
        };

        handler.handle(event);

    }

    //// SETTERS AT NEED

    public void setCurrentParentSelection(Element element) {
        this.currentParentSelection = element;
    }

    public void setParent(TaskMainTreeViewController controller) {
        this.parent = controller;
    }
}
