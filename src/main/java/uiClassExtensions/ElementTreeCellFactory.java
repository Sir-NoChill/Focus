package uiClassExtensions;

import com.exceptions.LeafAddChildException;
import elementStructure.Element;
import elementStructure.material.Reading;
import elementStructure.tasks.Project;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ElementTreeCellFactory extends TreeCell<Element> {
    private TextField textField;
    private final ContextMenu fullMenu = new ContextMenu();
    private final ContextMenu reducedMenu = new ContextMenu();

    public ElementTreeCellFactory() {
        Menu addItem = new Menu("Add New Task");
        Menu addMaterial = new Menu("Add New Material");

        MenuItem addProject = new MenuItem("Add Project");

        MenuItem addReading = new MenuItem("Add Reading");

        fullMenu.getItems().addAll(addItem, addMaterial);

        addMaterial.getItems().add(addReading);

        addItem.getItems().add(addProject);

        addProject.setOnAction((ActionEvent t) -> {
            itemBuilder("Project");
        });

        addReading.setOnAction((ActionEvent) -> {
            itemBuilder("Reading");
        });
    }

    private void itemBuilder(String string) {
        TreeItem<Element> elementTreeItem;
        switch (string) {
            case "Project":
                //Note that we need to override the isLeaf method so that the project can add more sub-projects
                //arbitrarily
                elementTreeItem = new TreeItem<>(new Project()) {
                    @Override
                    public boolean isLeaf() {
                        return false;
                    }
                };
                //add the element to the element parent
                getTreeItem().getChildren().add(elementTreeItem);
                try {
                    getTreeItem().getValue().addChildren(elementTreeItem.getValue());
                } catch (LeafAddChildException e) {
                    //pass
                }
                break;
            case "Reading":
                //No need to override the isLeaf method
                elementTreeItem = new TreeItem<>(new Reading());

                getTreeItem().getChildren().add(elementTreeItem);
                break;
        }
    }

    @Override
    public void startEdit() {
        super.startEdit();

        if (textField == null) {
            createTitleTextField();
        }
        setText(null);
        setGraphic(textField);
        textField.selectAll();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getTreeItem().getValue().getTitle());
        setGraphic(getTreeItem().getGraphic());
    }

    @Override
    public void updateItem(Element item, boolean empty) {
        super.updateItem(item,empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getTitle());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getTitle());
                setGraphic(getTreeItem().getGraphic());
                if (!getTreeItem().isLeaf() && getTreeItem().getParent() != null) {
                    setContextMenu(fullMenu);
                }
            }
        }
    }

    //Something is goin gwron
    private void createTitleTextField() {
        textField = new TextField(getTitle());
        Element e = this.getTreeItem().getValue();
        textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    e.setTitle(textField.getText());
                    commitEdit(e);//Get value
                } else if (event.getCode().equals(KeyCode.ESCAPE)) {
                    cancelEdit();
                }
            }
        });
    }

    private String getTitle() {
        return this.getTreeItem().getValue().getTitle();
    }

    private void setTitle(String text) {
        this.getTreeItem().getValue().setTitle(text);
    }

    private String getString() {
        return getItem() == null ? "" : getItem().getTitle();
    }

}
