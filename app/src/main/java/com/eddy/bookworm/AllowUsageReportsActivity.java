package com.eddy.bookworm;

import android.content.Intent;
import android.os.Bundle;

import com.eddy.bookworm.categories.CategoriesActivity;
import com.eddy.data.SettingsFragment;
import com.eddy.data.repository.UsageReportsRepository;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class AllowUsageReportsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allow_usage_reports);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.deny_button)
    public void denyUsageReports() {
        Timber.i("Usage reports denied");

        UsageReportsRepository usageReportsRepository = UsageReportsRepository
                .getInstance(this);
        usageReportsRepository.setAllowed(false);

        startCategoriesScreen();
    }

    @OnClick(R.id.allow_button)
    public void allowUsageReports() {
        Timber.i("Usage reports allowed");

        UsageReportsRepository usageReportsRepository = UsageReportsRepository
                .getInstance(this);
        usageReportsRepository.setAllowed(true);

        ((SettingsFragment.IUsageReport) this.getApplicationContext()).onUsageReportAllowed();

        startCategoriesScreen();
    }

    @OnClick(R.id.read_privacy_policy_text_view)
    public void readPrivacyPolicy() {
        Intent intent = PrivacyPolicyActivity.getStartedIntent(this);
        startActivity(intent);
    }

    private void startCategoriesScreen() {
        UsageReportsRepository usageReportsRepository = UsageReportsRepository
                .getInstance(this);
        usageReportsRepository.setAskedOnce();

        Intent intent = CategoriesActivity.getStartIntent(this);
        startActivity(intent);

        finish();
    }
}
