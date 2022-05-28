package com.focus.userModel.techTree;

import annotations.Nullable;

import static com.focus.ExpCounter.getExpCounterInstance;

public class TechNode {
    private @Nullable TechNode parent;
    private int expRequirement;
    private boolean complete;
    private int expRewardRunning;
    private String reward;
    private String description;

    public TechNode(int expRequirement, String reward, String description) {
        this.expRequirement = expRequirement;
        this.reward = reward;
        this.description = description;
        this.expRewardRunning = expRequirement;
    }

    public void contribute(int expAmount) {
        if (this.expRewardRunning - expAmount > 0) {
            getExpCounterInstance().subtract(expAmount);
            this.expRewardRunning -= expAmount;
        } else if (this.expRewardRunning - expAmount < 0) {
            getExpCounterInstance().subtract(expAmount);
            expAmount -= this.expRewardRunning;
            this.complete = true;
            this.expRewardRunning = 0;
            getExpCounterInstance().add(expAmount);
        } else {
            getExpCounterInstance().subtract(expAmount);
            this.expRewardRunning -= expAmount;
            this.complete = true;
        }
    }

    public double getPercentComplete() {
        return (this.expRewardRunning / this.expRequirement) * 100;
    }

    public void printReward() {
        System.out.println(this.reward + "\n" +
                "- " + this.description + "\n- " +
                this.expRequirement + "\n- " +
                this.expRewardRunning + "\n- " +
                this.getPercentComplete() + "\n- " +
                this.parent.getReward());
    }

    public TechNode getParent() {
        return parent;
    }

    public void setParent(TechNode parent) {
        this.parent = parent;
    }

    public int getExpRequirement() {
        return expRequirement;
    }

    public void setExpRequirement(int expRequirement) {
        this.expRequirement = expRequirement;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
