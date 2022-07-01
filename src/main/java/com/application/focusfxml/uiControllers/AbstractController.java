package com.application.focusfxml.uiControllers;

import com.Profile;

public abstract class AbstractController {
    public static Profile profile;

    public static void setProfile(Profile profile) {
        AbstractController.profile = profile;
    }
}
