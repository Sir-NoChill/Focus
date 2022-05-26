package com.focus.userModel;

import com.focus.userModel.lists.WorkItem;
import com.focus.userModel.techTree.TechTree;

import java.util.ArrayList;
import java.util.Collection;

public class State {
    private Collection<WorkItem> taskLists;
    private TechTree techTree;

    public State() {
        taskLists = new ArrayList<>();
        techTree = new TechTree(new TechTree.TechNode(0,"",""));
    }

    public void addTaskList(WorkItem workItem) {
        this.taskLists.add(workItem);
    }

    //Requires: workItem is in this.taskLists (Should be called with searchWorkItem)
    public void removeTaskList(WorkItem workItem) {
        this.taskLists.remove(workItem);
    }

    public void setTechTree(TechTree techTree) {
        this.techTree = techTree;
    }

    public Collection<WorkItem> getTaskLists() {
        return taskLists;
    }

    public void setTaskLists(Collection<WorkItem> taskLists) {
        this.taskLists = taskLists;
    }

    public TechTree getTechTree() {
        return techTree;
    }
}
