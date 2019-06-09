package com.eddy.bookworm.base;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.eddy.bookworm.BuildConfig;
import com.eddy.data.SettingsFragment;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class BookwormApplication extends Application implements SettingsFragment.IUsageReport {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    public void onUsageReportAllowed() {
        Fabric.with(this, new Crashlytics());
    }
}
