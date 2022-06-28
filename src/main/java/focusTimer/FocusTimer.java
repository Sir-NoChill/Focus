package focusTimer;

import elementStructure.Element;

import java.util.ArrayList;
import java.util.Collection;

import static java.lang.Math.floor;

public class FocusTimer {
    public static final long DEFAULT_FOCUS_TIMER_LENGTH = 1000 * 60 * 60;
    public static final long DEFAULT_BREAK_LENGTH = 1000 * 60 * 5;
    public static final long DEFAULT_FOCUS_SESSION_LENGTH = 1000 * 60 * 20;
    private Collection<Element> focusList;
    private Collection<Timer> timers; //IDEAS this might want to be a linked list for the memory access time
    private long timeFocused;
    private int focusExpMod; // a modifier for the expValue of any focus item completed in focus time
    private long focusTimerGoalDaily;
    public int focusTimerSessionsUsed;
    private long focusTimerTotalLength;
    private long focusTimerBreakLength;
    private long idealFocusTimerFocusLength;
    private Timer currentTimer;
    private boolean isRunning;
    private int numberOfBreaks; //TODO implement a variable number of breaks

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
        this.isRunning = false;
        this.timeFocused = 0;
        this.timers = new ArrayList<>();
        this.currentTimer = null;
        this.numberOfBreaks = 0;
    }


    public void generateTimers_NotTimeStrict() {
        this.timers.clear();
        double totalLength = (double) focusTimerTotalLength;
        double sessionLength = (double) idealFocusTimerFocusLength;
        int sessions = (int) floor( totalLength / sessionLength );
        if (sessions == 0) {
            sessions = 1;
        }
        int breaks = sessions - 1;

        while (breaks > 0) {
            Timer focusTimer = new Timer(idealFocusTimerFocusLength, Timer.TimerId.FOCUS);
            Timer breakTimer = new Timer(focusTimerBreakLength, Timer.TimerId.BREAK);
            this.timers.add(focusTimer);
            this.timers.add(breakTimer);
            breaks --;
        }
        this.timers.add(new Timer(idealFocusTimerFocusLength, Timer.TimerId.FOCUS));
    }

    public void generateTimers_TimeStrict() {
        this.timers.clear();
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
            Timer focusTimer = new Timer(idealFocusTimerFocusLength, Timer.TimerId.FOCUS);
            Timer breakTimer = new Timer(focusTimerBreakLength, Timer.TimerId.BREAK);
            this.timers.add(focusTimer);
            this.timers.add(breakTimer);
            breaks --;
        }
        this.timers.add(new Timer(idealFocusTimerFocusLength, Timer.TimerId.FOCUS));
    }

    //FIXME this is a UI method, user input should be placed in the While loop
    public void startFocusTimer() {
        for (Timer timer : this.timers) {
            timer.startTimer();
            this.isRunning = true;
            this.currentTimer = timer;
            timer.run();
            while (!timer.isComplete()) {
                //wait and listen for pause etc
                if (!isRunning && !this.currentTimer.getId().equals(Timer.TimerId.BREAK)) {
                    addTimeFocused();
                    this.currentTimer.pauseTimer();
                    break;
                } else if (!isRunning) {
                    this.currentTimer.pauseTimer();
                    break;
                }
            }
            if (!this.currentTimer.getId().equals(Timer.TimerId.BREAK)) {
                addTimeFocused();
            }
        }
        this.isRunning = false;
    }

    public void addTimeFocused() {
        this.timeFocused += this.currentTimer.getElapsedTime();
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

    public long getTimeFocused() {
        return timeFocused;
    }

    public void setTimeFocused(long timeFocused) {
        this.timeFocused = timeFocused;
    }

    public Timer getCurrentTimer() {
        return currentTimer;
    }

    public void setCurrentTimer(Timer currentTimer) {
        this.currentTimer = currentTimer;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
