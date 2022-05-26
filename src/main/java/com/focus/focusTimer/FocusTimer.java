package com.focus.focusTimer;

import com.focus.userModel.lists.WorkItem;

import java.util.ArrayList;
import java.util.Collection;

public class FocusTimer {
    private static final int DEFAULT_TIMER_LENGTH = 1000*60*30;
    private static final int DEFAULT_BREAK_LENGTH = 1000*60*5;
    private Collection<WorkItem> focusList;
    private Timer timer;
    private int focusExpMod; // a modifier for the expValue of any focus item completed in focus time

    public FocusTimer() {
        this.focusList = new ArrayList<>();
        this.timer = new Timer(DEFAULT_TIMER_LENGTH,DEFAULT_BREAK_LENGTH);
        this.focusExpMod = 2;
    }

    public void addGoal(WorkItem goal) {
        this.focusList.add(goal);
    }
    public void removeGoal(WorkItem goal) {
        if (focusList.contains(goal)) {
            this.focusList.remove(goal);
        }
    }
}
