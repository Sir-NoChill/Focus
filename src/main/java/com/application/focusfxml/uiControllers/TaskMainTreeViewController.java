package com.application.focusfxml.uiControllers;

import elementStructure.Element;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;

public class TaskMainTreeViewController implements Initializable {
    @FXML private TreeView<Element> taskTreeView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        //FOR TESTING PURPOSES ONLY!!!!
//        //TODO Commnent this out when the time comes!!!!
//        this.state = getTestState();
//        //---------------------------
//        treeStateInit();

    }
}
