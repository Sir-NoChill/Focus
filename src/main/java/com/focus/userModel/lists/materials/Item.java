package com.focus.userModel.lists.materials;

import com.focus.userModel.lists.Material;
import com.focus.userModel.lists.WorkContainer;

public class Item extends Material {
    public Item(String title, int expValue, String filePath, double progress) {
        super(title, expValue, filePath, progress);
    }

    public Item(String title, int expValue, double progress, String description, WorkContainer parent) {
        super(title, expValue, progress,description,parent);
    }


}
