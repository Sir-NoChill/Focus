package com.focus.userModel.lists.workContainers;

import com.focus.userModel.lists.WorkContainer;

public class Task extends WorkContainer {
    public Task(String title, int expValue, String description) {
        super(title, expValue,description);
    }

    public Task(String title, int expValue, String description,WorkContainer parent) {
        super(title, expValue,description,parent);
    }
}
