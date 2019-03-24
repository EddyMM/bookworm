package com.eddy.bookworm;

import android.app.Application;

import com.eddy.bookworm.BuildConfig;

import timber.log.Timber;

public class BookwormApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
