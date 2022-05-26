package com.focus.userModel.lists.materials;

import com.focus.userModel.lists.Material;

public class Video extends Material {
    private int[] duration;
    protected Video(String title, int expValue, String filePath, double progress, int hours, int minutes) {
        super(title, expValue, filePath, progress);
        duration = new int[] {hours,minutes};
    }
}
