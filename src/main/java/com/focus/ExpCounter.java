package com.focus;

// Singleton Pattern?
// Observer for tasks
public class ExpCounter {
    private int expCount;
    public static ExpCounter counter;

    private ExpCounter() {
        this.expCount = 0;
    }

    public static ExpCounter getExpCounterInstance() {
        if (counter == null) {
            counter = new ExpCounter();
        }
        return counter;
    }

    public int getExpCount() {
        return expCount;
    }

    public void add(int amount) {
        expCount += amount;
    }

    public void subtract(int amount) {
        expCount -= amount;
    }

    public void resetExpCounter() {
        this.expCount = 0;
    }
}
