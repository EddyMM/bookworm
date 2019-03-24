package com.eddy.bookworm;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import com.eddy.data.models.Book;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class BookDetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String BOOK_DETAIL_EXTRA = "BOOK_DETAIL_EXTRA";
    private static final int SIGN_IN_REQUEST_CODE = 12;

    @BindView(R.id.book_detail_photo_iv)
    ImageView bookImageView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.book_detail_description)
    TextView bookDescription;

    @BindView(R.id.book_detail_author)
    TextView bookAuthor;

    @BindView(R.id.book_detail_publisher)
    TextView bookPublisher;

    @BindView(R.id.sign_in_pb)
    ProgressBar signInProgressBar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        initGoogleSignInClient();

        fab.setOnClickListener((view) -> {
            SignInManager signInManager = SignInManager.getInstance();

            if(signInManager.userLoggedIn()) {
                Snackbar.make(view,
                        String.format("Saving bookmark for %s",
                                signInManager.getCurrentUserName()),
                        Snackbar.LENGTH_LONG)
                        .show();
            } else {
                Snackbar.make(view, "Not signed in at the moment", Snackbar.LENGTH_LONG)
                        .setAction("Sign In", BookDetailActivity.this)
                        .show();
            }
        });

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {
            Book book = intent.getParcelableExtra(BOOK_DETAIL_EXTRA);

            Picasso.get()
                    .load(book.getBookImageUrl())
                    .into(bookImageView);

            collapsingToolbarLayout.setTitle(book.getTitle());
            bookDescription.setText(book.getDescription());
            bookAuthor.setText(book.getAuthor());
            bookPublisher.setText(book.getPublisher());
        }
    }

    private void initGoogleSignInClient() {
        // Configure sign-in options
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient =  GoogleSignIn.getClient(
                BookDetailActivity.this, googleSignInOptions);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.book_details_menu, menu);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            menu.removeItem(R.id.menu_logout);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            if (firebaseAuth.getCurrentUser() != null) {
                String displayName = firebaseAuth.getCurrentUser().getDisplayName();
                firebaseAuth.signOut();
                googleSignInClient.signOut();
                Snackbar.make(bookImageView,
                        String.format("Logged out: %s", displayName),
                        Snackbar.LENGTH_SHORT)
                        .show();

                // Make sure logout option does not appear in the menu
                invalidateOptionsMenu();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void signIn() {
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, SIGN_IN_REQUEST_CODE);
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

    /**
     * Attempt to authenticate user using firebase google sign in
     *
     * @param googleSignInAccount The user's google account
     */
    private void fireBaseAuthWithGoogle(GoogleSignInAccount googleSignInAccount) {
        Timber.d("FireBase AuthWithGoogle: %s", googleSignInAccount.getEmail());

        showProgressBar();

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
        hideProgressBar();

        if (authResultTask.isSuccessful()) {
            // Sign in success
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            String displayName = Objects.requireNonNull(firebaseAuth.getCurrentUser())
                    .getDisplayName();
            Timber.i("Sign in as %s successful", displayName);
            saveBook();
            Snackbar.make(
                    bookImageView,
                    String.format("Successfully signed in as %s", displayName),
                    Snackbar.LENGTH_SHORT
            ).show();

            // Ensure logout option appears in the menu
            invalidateOptionsMenu();
        } else {
            Timber.e( authResultTask.getException());
            Snackbar.make(
                    bookImageView,
                    "Authentication Failed.",
                    Snackbar.LENGTH_SHORT
            ).show();
        }

    }

    private void showProgressBar() {
        signInProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        signInProgressBar.setVisibility(View.GONE);
    }

    /*
     * Saves the bookmarked book to Firebase Realtime DB
     */
    private void saveBook() {
        /// TODO: Save in FB RT DB
    }


    @Override
    public void onClick(View v) {
        signIn();
    }
}
