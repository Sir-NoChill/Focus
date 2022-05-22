package com.focus.focusTimer;

import com.focus.userModel.lists.WorkItem;

import java.util.ArrayList;
import java.util.Collection;

public class Timer {
    private Collection<WorkItem> goals;

    private int breaks;
    private long breakLength;

    private long startTime;
    private long timerLength;
    private long timerOriginalLength; //We want to be able to change this, so not final

    private long elapsedTime;
    private boolean isRunning;

    public Timer(long timerLength, long breakLength) {
        this.timerLength = timerLength;
        this.startTime = 0;
        this.elapsedTime = 0;
        this.breakLength = breakLength;
        this.breaks = (int) timerLength / 1000 / 60 / 5;
        isRunning = false;
        this.timerOriginalLength = timerLength;
        this.goals = new ArrayList<>();
    }
    public void startTimer() {
        setRunning(true);
        setStartTime(System.currentTimeMillis());
    }
    public void pauseTimer() {
        setRunning(false);
        setElapsedTime(System.currentTimeMillis() - startTime);
        setTimerLength(timerLength - elapsedTime);
    }
    public void stopTimer() {
        setRunning(false);
        setTimerLength(timerOriginalLength);
    }
    public void addGoal(WorkItem goal) {
        this.goals.add(goal);
    }
    public void removeGoal(WorkItem goal) {
        if (goals.contains(goal)) {
            this.goals.remove(goal);
        }
    }

    public void completeGoal(WorkItem goal) {
        goal.complete();
    }
    //The timer running method will need to be implemented in the main method
    public boolean isComplete() {
        if (this.timerLength <= 0) {
            return true;
        } else {
            return false;
        }
    }
    public int getBreaks() {
        return breaks;
    }
    public void setBreaks(int breaks) {
        this.breaks = breaks;
    }
    public long getBreakLength() {
        return breakLength;
    }
    public void setBreakLength(long breakLength) {
        this.breakLength = breakLength;
    }
    public Collection<WorkItem> getGoals() {
        return goals;
    }
    public void setGoals(Collection<WorkItem> goals) {
        this.goals = goals;
    }

    public long getStartTime() {
        return startTime;
    }
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    public long getTimerLength() {
        return timerLength;
    }
    public void setTimerLength(long timerLength) {
        this.timerLength = timerLength;
    }
    public long getElapsedTime() {
        return elapsedTime;
    }
    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
    public boolean isRunning() {
        return isRunning;
    }
    public void setRunning(boolean running) {
        isRunning = running;
    }
}
