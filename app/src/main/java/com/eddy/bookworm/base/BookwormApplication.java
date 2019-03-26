package com.eddy.bookworm.base;

import android.app.Application;

import com.eddy.bookworm.BuildConfig;
import com.google.firebase.database.FirebaseDatabase;

import timber.log.Timber;

public class BookwormApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        // Enable disk persistence
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
