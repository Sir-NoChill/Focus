package focusTimer;

//https://stackoverflow.com/questions/10820033/make-a-simple-timer-in-java
public class Timer {
    private long startTime;
    private long timerLength;
    private long timerOriginalLength; //We want to be able to change this, so not final
    private long elapsedTime;
    private boolean isRunning;
    private TimerId id;

    public enum TimerId {
        FOCUS,
        BREAK
    }

    public Timer(long timerLength, TimerId id) {
        this.timerLength = timerLength;
        this.startTime = 0;
        this.elapsedTime = 0;
        isRunning = false;
        this.timerOriginalLength = timerLength;
        this.id = id;
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
    public void resetTimer() {
        setRunning(false);
        setTimerLength(timerOriginalLength);
    }

    //The timer running method will need to be implemented in the main method
    public boolean isComplete() {
        if (this.timerLength <= 0) {
            return true;
        } else {
            return false;
        }
    }

    //FIXME this is a UI method, user input should be placed in the while loop
    //IDEAS if this is a UI mechanism, then it should be implemented in the ui lol
    public void run() {
        startTimer();
        while (isRunning) {
            if ((startTime + timerLength) <= System.currentTimeMillis()) {
                pauseTimer();
                //TimerNotifier.playSound(); //TODO add the sound player to the timer
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

    public TimerId getId() {
        return id;
    }

    public void setId(TimerId id) {
        this.id = id;
    }
}
