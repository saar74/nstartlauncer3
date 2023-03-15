package com.poc.nstartlauncher;

import android.app.Application;

import able.endpoint.android.AbleSDK;

public class NStartApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        AbleSDK.init(getApplicationContext(), "nstartlauncher2", "m50", true);
    }
}
