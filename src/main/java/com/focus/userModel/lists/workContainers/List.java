package com.focus.userModel.lists.workContainers;

import com.focus.userModel.lists.WorkContainer;

public class List extends WorkContainer {
    private boolean sequential;
    public List(String title, int expValue, boolean sequential) {
        super(title, expValue);
        this.sequential = sequential;
    }

    public boolean isSequential() {
        return sequential;
    }

    public void setSequential(boolean sequential) {
        this.sequential = sequential;
    }
}
