package com.eddy.bookworm.base;

import android.app.Application;

import com.eddy.bookworm.BuildConfig;
import com.eddy.data.settings.SettingsFragment;

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
        if (!BuildConfig.DEBUG) {
            io.fabric.sdk.android.Fabric.with(this,
                    new com.crashlytics.android.Crashlytics());
        }
    }
}
