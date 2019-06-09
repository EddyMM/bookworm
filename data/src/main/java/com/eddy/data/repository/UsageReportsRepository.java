package com.eddy.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.eddy.data.R;

public class UsageReportsRepository {

    private static UsageReportsRepository USAGE_REPORTS_REPOSITORY;
    private static final Object LOCK = new Object();

    private Context context;

    private UsageReportsRepository(Context context) {
        this.context = context;
    }

    public static UsageReportsRepository getInstance(Context context) {
        synchronized (LOCK) {
            if (USAGE_REPORTS_REPOSITORY == null) {
                USAGE_REPORTS_REPOSITORY = new UsageReportsRepository(context);
            }
        }
        return USAGE_REPORTS_REPOSITORY;
    }

    private SharedPreferences getAppSharedPrefs() {
        return PreferenceManager
                .getDefaultSharedPreferences(context);
    }

    public boolean isAllowed() {
        return getAppSharedPrefs().getBoolean(
                context.getString(R.string.usage_report_pref_key), false);
    }

    public boolean askedOnce() {
        return getAppSharedPrefs().getBoolean(
                context.getString(R.string.usage_report_asked_once_key), false);
    }

    public void setAskedOnce() {
        getAppSharedPrefs().edit()
                .putBoolean(context.getString(R.string.usage_report_asked_once_key), true)
                .apply();
    }

    public void setAllowed(boolean allowed) {
        getAppSharedPrefs().edit()
                .putBoolean(context.getString(R.string.usage_report_pref_key), allowed)
                .apply();
    }
}
