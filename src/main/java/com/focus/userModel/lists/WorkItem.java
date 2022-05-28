package com.focus.userModel.lists;

import annotations.Nullable;
import annotations.Visual;

//Component
public abstract class WorkItem {

    protected int expValue;
    protected boolean complete;
    protected @Nullable WorkItem parent;
    protected String title;
    protected double progress; //ideally a percentage calculated dynamically
    protected String description;
    protected int depth;



    protected WorkItem(String title, int expValue, String description) {
        this.complete = false;
        this.expValue = expValue;
        this.title = title;
        this.parent = null;
        this.progress = 0;
        this.description = description;
    }

    protected WorkItem(String title, int expValue, String description, WorkItem parent) {
        this.complete = false;
        this.expValue = expValue;
        this.title = title;
        this.depth = parent.getDepth() + 1;
        this.progress = 0;
        this.description = description;
        this.parent = parent;
    }

    protected WorkItem(String title, int expValue) {
        this.complete = false;
        this.expValue = expValue;
        this.title = title;
        this.parent = null;
        this.progress = 0;
        this.description = "";
    }

    @Visual
    public abstract void printWorkItem();

    public abstract void complete();

    public WorkItem getParent() {
        return parent;
    }

    //MODIFIES: this
    //EFFECTS: sets the parent and makes this.depth parent.depth +1
    public void setParent(WorkItem parent) {
        this.parent = parent;
        this.depth = parent.getDepth() + 1;
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

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
