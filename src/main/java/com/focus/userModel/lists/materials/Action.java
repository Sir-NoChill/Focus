package com.focus.userModel.lists.materials;

import com.focus.userModel.lists.Material;
import com.focus.userModel.lists.WorkContainer;

public class Action extends Material {
    public Action(String title, int expValue, double progress) {
        super(title, expValue, progress);
    }

    public Action(String title, int expValue, double progress, String description, WorkContainer parent) {
        super(title, expValue, progress,description,parent);
    }
}
