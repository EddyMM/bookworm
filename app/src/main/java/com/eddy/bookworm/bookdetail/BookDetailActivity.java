package com.eddy.bookworm.bookdetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eddy.bookworm.R;
import com.eddy.bookworm.signin.SignInManager;
import com.eddy.bookworm.base.BaseBookwormActivity;
import com.eddy.data.models.Book;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BookDetailActivity extends BaseBookwormActivity implements View.OnClickListener {

    public static final String BOOK_DETAIL_EXTRA = "BOOK_DETAIL_EXTRA";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

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

    protected void hideProgressBar() {
        signInProgressBar.setVisibility(View.GONE);
    }

    protected void showProgressBar() {
        signInProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onBeginSignIn() {
        showProgressBar();
    }

    @Override
    protected void onSuccessfulSignIn() {
        saveBook();
    }

    @Override
    protected void onCompleteSignIn() {
        hideProgressBar();
    }

    /*
     * Saves the bookmarked book to Firebase Realtime DB
     */
    private void saveBook() {
        /// TODO: Save in FB RT DB
    }

    @Override
    public void onClick(View v) {
        super.signInWithFirebase();
    }
}
