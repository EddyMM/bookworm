package com.eddy.bookworm;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.eddy.bookworm.categories.CategoriesActivity;
import com.eddy.data.repository.UsageReportsRepository;

import java.util.concurrent.Executors;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import timber.log.Timber;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                // Simulate some delay for user to read intro
                Thread.sleep(3000);

                Looper looper = Looper.getMainLooper();
                Handler handler = new Handler(looper);
                handler.post(this::startAllowUsageReportsActivity);
            } catch (InterruptedException e) {
                Timber.e(e);
            }
        });
    }

    private void startAllowUsageReportsActivity() {
        UsageReportsRepository usageReportsRepository = UsageReportsRepository
                .getInstance(this);

        if (usageReportsRepository.askedOnce()) {
            Intent intent = new Intent(this, CategoriesActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, AllowUsageReportsActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
