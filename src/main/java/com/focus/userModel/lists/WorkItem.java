package com.focus.userModel.lists;

import annotations.Nullable;

import static com.focus.ExpCounter.getExpCounterInstance;

//Component
public abstract class WorkItem {

    protected int expValue;
    protected boolean complete;
    protected @Nullable WorkItem parent;
    protected String title;
    protected double progress; //ideally a percentage calculated dynamically


    protected WorkItem(String title, int expValue) {
        this.complete = false;
        this.expValue = expValue;
        this.title = title;
        this.parent = null;
        this.progress = 0;
    }

    public void complete() {
        getExpCounterInstance().add(this.expValue);
        this.complete = true;
    }

    public WorkItem getParent() {
        return parent;
    }

    public void setParent(WorkItem parent) {
        this.parent = parent;
    }

    public int getExpValue() {
        return expValue;
    }

    public void setExpValue(int expValue) {
        this.expValue = expValue;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
