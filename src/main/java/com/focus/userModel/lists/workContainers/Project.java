package com.focus.userModel.lists.workContainers;

import com.focus.userModel.lists.WorkContainer;

public class Project extends WorkContainer {
    public Project(String title, int expValue, String description) {
        super(title, expValue,description);
    }

    public Project(String title, int expValue, String description,WorkContainer parent) {
        super(title, expValue,description,parent);
    }
}
