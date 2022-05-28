package com.focus.userModel;

import com.focus.userModel.lists.WorkContainer;
import com.focus.userModel.lists.WorkItem;
import com.focus.userModel.techTree.TechNode;
import com.focus.userModel.techTree.TechTree;
import exceptions.InvalidListNameException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class State {
    private Collection<WorkItem> taskLists;
    private TechTree techTree;

    public State() {
        taskLists = new ArrayList<>();
        techTree = new TechTree(new ArrayList<TechNode>());
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

    public WorkContainer searchTaskLists(String listTitle) throws InvalidListNameException {
        ArrayList<WorkContainer> listList = new ArrayList<>();
        for (WorkItem taskList : this.taskLists) {
            if (taskList.getClass().toString().equals("Epic") || taskList.getClass().toString().equals("Project")
            || taskList.getClass().toString().equals("List") || taskList.getClass().toString().equals("Task")) {
                listList.add((WorkContainer) taskList);
            }
        }
        for (WorkContainer taskList : listList) {
            if (taskList.getTitle().equals(listTitle)) {
                return taskList;
            }
        }
        throw new InvalidListNameException();
    }

    public WorkItem searchWorkItems(String name) throws InvalidListNameException {
        for (WorkItem taskList : this.taskLists) {
            if (Objects.equals(name,taskList.getTitle())) {
                return taskList;
            }
        }
        throw new InvalidListNameException();
    }
}
