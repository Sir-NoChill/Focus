package elementStructure;

import java.util.Iterator;

public abstract class ElementSuperclass {
    protected boolean complete;
    protected int expValue;
    protected String title;
    protected String description;
    protected Iterator<Element> iterator;
    protected double progress;
    protected double time;

    public static final boolean DEFAULT_COMPLETE = false;
    public static final int DEFAULT_EXP_VALUE = 0;
    public static final double DEFAULT_PROGRESS = 0.0;
    public static final double DEFAULT_TIME = 1.0;

    ElementSuperclass() {
        this.complete = DEFAULT_COMPLETE;
        this.expValue = DEFAULT_EXP_VALUE;
        this.progress = DEFAULT_PROGRESS;
        this.time = DEFAULT_TIME;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public double getProgress() {
        return this.progress;
    }

    public boolean isComplete() {
        return complete;
    }

    public double getTime() {
        return time;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }
}
