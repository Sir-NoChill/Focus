package com.focus.focusTimer;

import com.focus.userModel.lists.WorkItem;

//https://stackoverflow.com/questions/10820033/make-a-simple-timer-in-java
public class Timer {
    private long startTime;
    private long timerLength;
    private long timerOriginalLength; //We want to be able to change this, so not final

    private long elapsedTime;
    private boolean isRunning;

    public Timer(long timerLength) {
        this.timerLength = timerLength;
        this.startTime = 0;
        this.elapsedTime = 0;
        isRunning = false;
        this.timerOriginalLength = timerLength;
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

    public void run() {
        while (isRunning) {
            if ((startTime + timerLength) <= System.currentTimeMillis()) {
                stopTimer();
                TimerNotifier.playSound();
            }
        }
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
