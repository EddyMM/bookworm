package com.eddy.bookworm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.eddy.bookworm.base.BaseBookwormActivity;
import com.eddy.bookworm.listnames.ListNamesActivity;
import com.eddy.bookworm.signin.SignInManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IntroActivity extends BaseBookwormActivity {

    @BindView(R.id.intro_sign_in_pb)
    ProgressBar signInProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        SignInManager signInManager = SignInManager.getInstance();
        if (signInManager.userLoggedIn()) {
            openCategoriesList();
        }

        ButterKnife.bind(this);
    }

    @OnClick(R.id.sign_in_btn)
    public void signIn() {
        super.signInWithFirebase();

    }

    @Override
    protected void onSuccessfulSignIn() {
        super.onSuccessfulSignIn();
        openCategoriesList();
    }

    @Override
    protected void onCompleteSignIn() {
        hideProgressBar();
    }

    private void hideProgressBar() {
        signInProgressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onBeginSignIn() {
        showProgressBar();
    }

    private void showProgressBar() {
        signInProgressBar.setVisibility(View.VISIBLE);
    }

    private void openCategoriesList() {
        Intent intent = new Intent(this, ListNamesActivity.class);
        startActivity(intent);
        finish();
    }
}
