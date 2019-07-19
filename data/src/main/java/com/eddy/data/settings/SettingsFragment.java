package com.eddy.data.settings;


import android.os.Bundle;

import com.eddy.data.R;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends androidx.preference.PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.bookworm_settings, rootKey);
    }

    public interface IUsageReport {
        void onUsageReportAllowed();
    }

    @Override
    public boolean onPreferenceTreeClick(androidx.preference.Preference preference) {
        String key = preference.getKey();

        if (key.equals(getString(R.string.usage_report_pref_key))) {
            boolean allowed = ((androidx.preference.SwitchPreference) preference)
                    .isChecked();

            if (allowed) {
                ((IUsageReport) requireContext().getApplicationContext()).onUsageReportAllowed();
            }
        }

        return super.onPreferenceTreeClick(preference);
    }
}
