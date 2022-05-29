package com.focus.focusTimer;

import com.focus.userModel.lists.WorkItem;

import java.util.ArrayList;
import java.util.Collection;

import static com.focus.ExpCounter.getExpCounterInstance;
import static java.lang.Math.floor;

public class FocusTimer {
    private static final long DEFAULT_FOCUS_TIMER_LENGTH = 1000 * 60 * 60;
    private static final long DEFAULT_BREAK_LENGTH = 1000 * 60 * 5;
    private static final long DEFAULT_FOCUS_SESSION_LENGTH = 1000 * 60 * 20;
    private Collection<WorkItem> focusList;
    private Collection<Timer> timers;
    private int focusExpMod; // a modifier for the expValue of any focus item completed in focus time
    private long focusTimerGoalDaily;
    public int focusTimerSessionsUsed;
    private long focusTimerTotalLength;
    private long focusTimerBreakLength;
    private long idealFocusTimerFocusLength;
    private Timer currentTimer;

    //cHANGE THE STRUCTURE TO AL IST OF TIMERS

    //should the focus timer be a singleton?
    public FocusTimer() {
        this.focusList = new ArrayList<>();
        this.focusExpMod = 2;
        this.focusTimerGoalDaily = 0;
        this.focusTimerSessionsUsed = 0;
        this.focusTimerTotalLength = DEFAULT_FOCUS_TIMER_LENGTH;
        this.focusTimerBreakLength = DEFAULT_BREAK_LENGTH;
        this.idealFocusTimerFocusLength = DEFAULT_FOCUS_SESSION_LENGTH;
        this.currentTimer = null;
    }



    public void addGoal(WorkItem goal) {
        this.focusList.add(goal);
    }
    public void removeGoal(WorkItem goal) {
        if (focusList.contains(goal)) {
            this.focusList.remove(goal);
        }
    }

    public void generateTimers_NotTimeStrict() {
        double totalLength = (double) focusTimerTotalLength;
        double sessionLength = (double) idealFocusTimerFocusLength;
        int sessions = (int) floor( totalLength / sessionLength );
        if (sessions == 0) {
            sessions = 1;
        }
        int breaks = sessions - 1;


        while (breaks > 0) {
            Timer focusTimer = new Timer(idealFocusTimerFocusLength);
            Timer breakTimer = new Timer(focusTimerBreakLength);
            this.timers.add(focusTimer);
            this.timers.add(breakTimer);
            breaks --;
        }
        this.timers.add(new Timer(idealFocusTimerFocusLength));
    }

    public void generateTimers_TimeStrict() {
        double totalLength = (double) focusTimerTotalLength;
        double sessionLength = (double) idealFocusTimerFocusLength;
        double breakLength = (double) focusTimerBreakLength;

        int sessions = (int) floor( totalLength / sessionLength );
        if (sessions == 0) {
            sessions = 1;
        }
        int breaks = sessions - 1;

        totalLength = totalLength - (breaks * breakLength);
        sessionLength = totalLength / sessions;

        this.idealFocusTimerFocusLength = (long) sessionLength;

        while (breaks > 0) {
            Timer focusTimer = new Timer(idealFocusTimerFocusLength);
            Timer breakTimer = new Timer(focusTimerBreakLength);
            this.timers.add(focusTimer);
            this.timers.add(breakTimer);
            breaks --;
        }
        this.timers.add(new Timer(idealFocusTimerFocusLength));
    }

    public void startFocusTimer() {
        for (Timer timer : this.timers) {
            timer.startTimer();
            this.currentTimer = timer;
            while (!timer.isComplete()) {
                //wait and listen for pause etc
            }
        }
    }

    public void completeFocusItem(WorkItem workItem) {
        int expValue = workItem.getExpValue();
        workItem.complete();
        getExpCounterInstance().add(expValue * (focusExpMod - 1));
    }

    public Collection<WorkItem> getFocusList() {
        return focusList;
    }

    public void setFocusList(Collection<WorkItem> focusList) {
        this.focusList = focusList;
    }

    public int getFocusExpMod() {
        return focusExpMod;
    }

    public void setFocusExpMod(int focusExpMod) {
        this.focusExpMod = focusExpMod;
    }

    public void setFocusTimerGoalDaily(int focusTimerGoalDaily) {
        this.focusTimerGoalDaily = focusTimerGoalDaily;
    }

    public Collection<Timer> getTimers() {
        return timers;
    }

    public void setTimers(Collection<Timer> timers) {
        this.timers = timers;
    }

    public long getFocusTimerGoalDaily() {
        return focusTimerGoalDaily;
    }

    public void setFocusTimerGoalDaily(long focusTimerGoalDaily) {
        this.focusTimerGoalDaily = focusTimerGoalDaily;
    }

    public int getFocusTimerSessionsUsed() {
        return focusTimerSessionsUsed;
    }

    public void setFocusTimerSessionsUsed(int focusTimerSessionsUsed) {
        this.focusTimerSessionsUsed = focusTimerSessionsUsed;
    }

    public long getFocusTimerTotalLength() {
        return focusTimerTotalLength;
    }

    public void setFocusTimerTotalLength(long focusTimerTotalLength) {
        this.focusTimerTotalLength = focusTimerTotalLength;
    }

    public long getFocusTimerBreakLength() {
        return focusTimerBreakLength;
    }

    public void setFocusTimerBreakLength(long focusTimerBreakLength) {
        this.focusTimerBreakLength = focusTimerBreakLength;
    }

    public long getIdealFocusTimerFocusLength() {
        return idealFocusTimerFocusLength;
    }

    public void setIdealFocusTimerFocusLength(long idealFocusTimerFocusLength) {
        this.idealFocusTimerFocusLength = idealFocusTimerFocusLength;
    }
}
