package com.application.focusfxml.uiControllers;

import com.Profile;

public abstract class AbstractController {
    protected static Profile profile;

    public static void setProfile(Profile profile) {
        AbstractController.profile = profile;
    }
}
