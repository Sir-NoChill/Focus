package com.focus.userModel.lists.workContainers;

import com.focus.userModel.lists.WorkContainer;

public class List extends WorkContainer {
    private boolean sequential;
    public List(String title, int expValue, boolean sequential, String description) {
        super(title, expValue,description);
        this.sequential = sequential;
    }

    public List(String title, int expValue, boolean sequential, String description,WorkContainer parent) {
        super(title, expValue,description,parent);
        this.sequential = sequential;
    }

    public boolean isSequential() {
        return sequential;
    }

    public void setSequential(boolean sequential) {
        this.sequential = sequential;
    }
}
