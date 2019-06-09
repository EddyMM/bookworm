package com.eddy.bookworm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PrivacyPolicyActivity extends AppCompatActivity {

    @BindView(R.id.privacy_policy_text_view)
    TextView privacyPolicyTextView;

    public static Intent getStartedIntent(Context context) {
        return new Intent(context, PrivacyPolicyActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        setTitle(getString(R.string.privacy_policy_title));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ButterKnife.bind(this);

        loadPrivacyPolicy();
    }

    private void loadPrivacyPolicy() {
        Spanned privacyPolicySpanned = Html.fromHtml(getString(R.string.privacy_policy));
        privacyPolicyTextView.setText(privacyPolicySpanned);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId= item.getItemId();

        switch (itemId) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
