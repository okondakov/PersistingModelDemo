package com.ls.modelpersistingdemo;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created on 21.05.2015.
 */
public final class MyApplication extends Application {

    private static Context sharedContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedContext = this;
    }

    @NonNull
    public static Context getSharedContext() {
        if (sharedContext == null) {
            throw new IllegalStateException(
                    "getSharedContext() called before Application.onCreate()");
        }
        return sharedContext;
    }
}
