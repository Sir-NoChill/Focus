package com.focus.userModel.lists.workContainers;

import com.focus.userModel.lists.WorkContainer;

public class Epic extends WorkContainer {
    public Epic(String title, int expValue, String description) {
        super(title, expValue, description);
    }

    public Epic(String title, int expValue, String description,WorkContainer parent) {
        super(title, expValue, description,parent);
    }
}
