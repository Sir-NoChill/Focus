package com.focus.userModel.lists;

import java.io.File;

import static com.focus.ExpCounter.getExpCounterInstance;

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
    }


}
