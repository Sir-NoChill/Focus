package com.focus.userModel.lists.materials;

import com.focus.userModel.lists.Material;

public class Reading extends Material {
    int pages;
    protected Reading(String title, int expValue, String filePath, double progress, int pages) {
        super(title, expValue, filePath, progress);
        this.pages = pages;
    }
}
