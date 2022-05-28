package com.focus.userModel.lists.materials;

import com.focus.userModel.lists.Material;
import com.focus.userModel.lists.WorkContainer;

public class Video extends Material {
    private int[] duration;
    protected Video(String title, int expValue, String filePath, double progress, int hours, int minutes) {
        super(title, expValue, filePath, progress);
        duration = new int[] {hours,minutes};
    }

    public Video(String title, int expValue, String filePath, double progress, int hours, int minutes, String description,
                    WorkContainer parent) {
        super(title, expValue, filePath, progress,description,parent);
        duration = new int[] {hours,minutes};
    }
}
