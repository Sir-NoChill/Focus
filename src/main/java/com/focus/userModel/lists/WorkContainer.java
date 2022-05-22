package com.focus.userModel.lists;

import annotations.Nullable;
import exceptions.InvalidCalculationException;
import exceptions.InvalidTaskNameException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Objects.isNull;

//WorkContainer
public abstract class WorkContainer extends WorkItem {
    private Collection<WorkItem> children;
    private double projectedHours;
    private @Nullable LocalDate dueDate;
    private boolean manualProgressCalculation;

    public WorkContainer(String title, int expValue) {
        super(title, expValue);
        this.projectedHours = 0;
        this.children = new ArrayList<>();
        this.dueDate = null;
        this.manualProgressCalculation = false;
    }

    public WorkContainer(String title, int expValue,LocalDate dueDate) {
        super(title, expValue);
        this.projectedHours = 0;
        this.children = new ArrayList<>();
        this.dueDate = dueDate;
        this.manualProgressCalculation = false;
    }

    public WorkContainer(String title, int expValue,LocalDate dueDate, boolean manualProgressCalculation) {
        super(title, expValue);
        this.projectedHours = 0;
        this.children = new ArrayList<>();
        this.dueDate = dueDate;
        this.manualProgressCalculation = manualProgressCalculation;
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
            if (item.title.equals(title)) {
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
        }
        throw new InvalidCalculationException();
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

}
