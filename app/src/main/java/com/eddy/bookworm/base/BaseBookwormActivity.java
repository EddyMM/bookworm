package com.eddy.bookworm.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.eddy.bookworm.R;
import com.eddy.bookworm.firebase.SignInManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import timber.log.Timber;

public abstract class BaseBookwormActivity extends AppCompatActivity {

    private GoogleSignInClient googleSignInClient;
    private int logoutMenuItemId;
    private static final int SIGN_IN_REQUEST_CODE = 12;

    protected GoogleSignInClient getGoogleSignInClient() {
        return googleSignInClient;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initGoogleSignInClient();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SignInManager signInManager = SignInManager.getInstance();
        if (signInManager.userLoggedIn()) {
            MenuItem logoutMenuItem = menu.add(getString(R.string.logout));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                logoutMenuItem.setContentDescription(getString(R.string.logout));
            }
            logoutMenuItemId =  logoutMenuItem.getItemId();
        } else {
            menu.removeItem(logoutMenuItemId);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == logoutMenuItemId) {
            SignInManager signInManager = SignInManager.getInstance();
            if (signInManager.userLoggedIn()) {
                String displayName = signInManager.getCurrentUserName();
                signInManager.signOut(googleSignInClient);

                Snackbar.make(findViewById(android.R.id.content),
                        getString(R.string.logged_out_user, displayName),
                        Snackbar.LENGTH_SHORT)
                        .show();

                // Make sure logout option does not appear in the menu
                invalidateOptionsMenu();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_REQUEST_CODE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount googleSignInAccount = task.getResult(ApiException.class);
                fireBaseAuthWithGoogle(googleSignInAccount);
            } catch (ApiException e) {
                // Google Sign In failed
                Timber.w("Google sign in failed");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    protected void signInWithFirebase() {
        Intent intent = getGoogleSignInClient().getSignInIntent();
        startActivityForResult(intent, SIGN_IN_REQUEST_CODE);
    }

    private void initGoogleSignInClient() {
        // Configure sign-in options
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient =  GoogleSignIn.getClient(this, googleSignInOptions);
    }

    /**
     * Attempt to authenticate user using firebase google sign in
     *
     * @param googleSignInAccount The user's google account
     */
    protected void fireBaseAuthWithGoogle(GoogleSignInAccount googleSignInAccount) {
        Timber.d("FireBase AuthWithGoogle: %s", googleSignInAccount.getEmail());

        onBeginSignIn();

        AuthCredential authCredential = GoogleAuthProvider.getCredential(
                googleSignInAccount.getIdToken(),
                null);

        FirebaseAuth fbAuthInstance = FirebaseAuth.getInstance();
        fbAuthInstance.signInWithCredential(authCredential)
                .addOnCompleteListener(
                        this,
                        this::onSignInCompletion);
    }

    private void onSignInCompletion(Task<AuthResult> authResultTask) {
        onCompleteSignIn();

        if (authResultTask.isSuccessful()) {
            // Sign in success
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            String displayName = Objects.requireNonNull(firebaseAuth.getCurrentUser())
                    .getDisplayName();
            Timber.i("Sign in as %s successful", displayName);
            Snackbar.make(
                    findViewById(android.R.id.content),
                    getString(R.string.successful_sigin, displayName),
                    Snackbar.LENGTH_SHORT
            ).show();

            // Ensure logout option appears in the menu
            invalidateOptionsMenu();

            onSuccessfulSignIn();
        } else {
            Timber.e( authResultTask.getException());
            Snackbar.make(
                    findViewById(android.R.id.content),
                    getString(R.string.authenticaion_failed),
                    Snackbar.LENGTH_SHORT
            ).show();
        }
    }

    protected void onSuccessfulSignIn() {}

    protected abstract void onCompleteSignIn();

    protected abstract void onBeginSignIn();
}
