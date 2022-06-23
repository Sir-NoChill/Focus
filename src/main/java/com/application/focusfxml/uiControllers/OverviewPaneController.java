package com.application.focusfxml.uiControllers;

import elementStructure.Element;
import javafx.scene.control.ProgressBar;

public class OverviewPaneController {

    public static void overviewPaneInit() {
        //TreeView treeView = MasterController.getTaskTreeView();
        //setProgressBar(MasterController.getSelectedTaskProgressBar(),TaskMainTreeViewController.getSelectedElement(treeView));
    }

    public static void setProgressBar(ProgressBar progressBar, Element element) {
        progressBar.setProgress(element.getProgress());
    }
}
