package com.focus.userModel.lists;

import annotations.Nullable;
import annotations.Visual;
import exceptions.InvalidCalculationException;
import exceptions.InvalidTaskNameException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static com.focus.ExpCounter.getExpCounterInstance;
import static java.util.Objects.isNull;

//WorkContainer
public abstract class WorkContainer extends WorkItem {
    private Collection<WorkItem> children;
    private double projectedHours;
    private @Nullable LocalDate dueDate;
    private boolean manualProgressCalculation;

    public WorkContainer(String title, int expValue, String description) {
        super(title, expValue, description);
        this.projectedHours = 0;
        this.children = new ArrayList<>();
        this.dueDate = null;
        this.manualProgressCalculation = false;
    }

    public WorkContainer(String title, int expValue, String description,WorkContainer parent) {
        super(title, expValue, description,parent);
        this.projectedHours = 0;
        this.children = new ArrayList<>();
        this.dueDate = null;
        this.manualProgressCalculation = false;
    }

    public WorkContainer(String title, int expValue) {
        super(title, expValue);
        this.projectedHours = 0;
        this.children = new ArrayList<>();
        this.dueDate = null;
        this.manualProgressCalculation = false;
    }

    public WorkContainer(String title, int expValue,LocalDate dueDate, String description) {
        super(title, expValue, description);
        this.projectedHours = 0;
        this.children = new ArrayList<>();
        this.dueDate = dueDate;
        this.manualProgressCalculation = false;
    }

    public WorkContainer(String title, int expValue,LocalDate dueDate, boolean manualProgressCalculation, String description) {
        super(title, expValue, description);
        this.projectedHours = 0;
        this.children = new ArrayList<>();
        this.dueDate = dueDate;
        this.manualProgressCalculation = manualProgressCalculation;
    }

    public WorkContainer(String title, int expValue,boolean manualProgressCalculation, String description) {
        super(title, expValue, description);
        this.projectedHours = 0;
        this.children = new ArrayList<>();
        this.dueDate = null;
        this.manualProgressCalculation = manualProgressCalculation;
    }

    @Override
    public void complete() {
        getExpCounterInstance().add(this.expValue);
        this.complete = true;
        for (WorkItem child : this.children) {
            getExpCounterInstance().add(child.getExpValue());
            child.setComplete(true);
        }
    }


    /**
     * Adds a WorkItem to another WorkItem
     * should throw an exception
     */
    public void addItem(WorkItem workItem) {
        if (!this.children.contains(workItem)) {
            this.children.add(workItem);
            if (!workItem.getClass().equals(this.getClass())) {
                workItem.setParent(this);
            } else {
                WorkContainer item = (WorkContainer) workItem;
                item.addParent(this);
            }
        }
    }

    //Should throw an exception
    public void removeItem(WorkItem workItem) {
        this.children.remove(workItem);
        workItem.setParent(null);
    }

    //for user ease
    public WorkItem searchWorkItem(String title) throws InvalidTaskNameException {
        for (WorkItem item : this.children) {
            if (Objects.equals(title,this.getTitle())) {
                return item;
            }
        }
        throw new InvalidTaskNameException();
    }

    public void addParent(WorkContainer newParent) {
        WorkContainer oldParent = (WorkContainer) super.getParent();
        if (isNull(oldParent)) {
            newParent.addItem(this);
            super.setParent(newParent);
        } else if (oldParent.equals(newParent)) {
            //pass
        } else if (!isNull(oldParent)) {
            oldParent.removeItem(this);
            newParent.addItem(this);
            super.setParent(newParent);
        }
    }

    //this could be more advanced, taking into account the amount completed of all the leafs
    public double calculatePercentComplete() throws InvalidCalculationException {
        if (!manualProgressCalculation) {
            double progress;
            double denominator = this.children.size();
            double numerator = 0;
            for (WorkItem child : this.children) {
                if (child.isComplete()) {
                    numerator++;
                }
            }
            if (numerator == 0) {
                progress = 0;
            } else {
                progress = numerator / denominator;
            }
            return progress;
        } else {
            throw new InvalidCalculationException();
        }
    }

    @Override
    @Visual
    //FIXME this needs to be fixed to add the tabbing between levels
    public void printWorkItem() {
        System.out.println("-" + this.getTitle() + "\n");
        for (WorkItem child : this.children) {
            child.printWorkItem();
        }
    }

    public void setParent(WorkContainer parent) {
        super.setParent(parent);
    }

    public double getProjectedHours() {
        return projectedHours;
    }

    public void setProjectedHours(double projectedHours) {
        this.projectedHours = projectedHours;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Collection<WorkItem> getChildren() {
        return this.children;
    }

    public WorkItem getParent() {
        return super.getParent();
    }

    public void setChildren(Collection<WorkItem> children) {
        this.children = children;
    }

    public boolean isManualProgressCalculation() {
        return manualProgressCalculation;
    }

    public void setManualProgressCalculation(boolean manualProgressCalculation) {
        this.manualProgressCalculation = manualProgressCalculation;
    }
}
