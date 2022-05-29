package com.focus.userModel.lists;

import annotations.Visual;

import java.io.File;

import static com.focus.ExpCounter.getExpCounterInstance;
import static java.util.Objects.isNull;

//Leaf
public abstract class Material extends WorkItem {
    private File attachedFile;

    //Note that the length will be implemented in the concrete leafs
    //because it is different for each type of item
    protected Material(String title, int expValue, String filePath, double progress) {
        super(title, expValue);
        FileAddition(filePath);
        this.progress = progress;
    }

    //For no file
    protected Material(String title, int expValue, double progress) {
        super(title, expValue);
        FileAddition("");
        this.progress = progress;
    }

    protected Material(String title, int expValue, double progress,String description,WorkContainer parent) {
        super(title, expValue,description,parent);
        FileAddition("");
        this.progress = progress;
    }

    public Material(String title, int expValue, String filePath, double progress, String description, WorkContainer parent) {
        super(title, expValue, description, parent);
        FileAddition(filePath);
        this.progress = progress;
    }

    private void FileAddition(String filePath) {
        if (filePath.equals("")) {
            this.attachedFile = null;
        } else {
            this.attachedFile = new File(filePath);
        }
    }

    @Override
    public void complete() {
        getExpCounterInstance().add(this.expValue);
        this.complete = true;
        if (!isNull(parent)) {
            boolean areComplete = true;
            for (WorkItem child : parent.getChildren()) {
                if (child.isComplete() == false) {
                    areComplete = false;
                }
            }
            if (areComplete) {
                parent.complete();
            }
        }
    }

    @Override
    @Visual
    public void printWorkItem() {
        System.out.println("- (Leaf) -" + this.title + "\n");
    }
}
