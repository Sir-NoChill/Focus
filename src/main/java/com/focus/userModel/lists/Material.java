package com.focus.userModel.lists;

import java.io.File;

//Leaf
public abstract class Material extends WorkItem {
    private File attachedFile;

    //Note that the length will be implemented in the concrete leafs
    //because it is different for each type of item
    protected Material(String title, int expValue, String filePath, double progress) {
        super(title, expValue);
        if (filePath.equals("")) {
            this.attachedFile = null;
        } else {
            this.attachedFile = new File(filePath);
        }
        this.progress = progress;
    }


}
