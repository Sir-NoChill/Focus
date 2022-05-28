package com.focus.userModel.lists.materials;

import com.focus.userModel.lists.Material;
import com.focus.userModel.lists.WorkContainer;

public class Reading extends Material {
    int pages; //Potentially overload this with the possibility of being a 2d int
    protected Reading(String title, int expValue, String filePath, double progress, int pages) {
        super(title, expValue, filePath, progress);
        this.pages = pages;
    }

    public Reading(String title, int expValue, double progress, String description, WorkContainer parent,String filePath,
                   int pages) {
        super(title, expValue, filePath, progress,description,parent);
        this.pages = pages;
    }
}
