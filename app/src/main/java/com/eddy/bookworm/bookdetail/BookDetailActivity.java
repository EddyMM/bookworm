package com.eddy.bookworm.bookdetail;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eddy.bookworm.firebase.FirebaseDatabaseManager;
import com.eddy.bookworm.R;
import com.eddy.bookworm.Utils;
import com.eddy.bookworm.base.BaseBookwormActivity;
import com.eddy.bookworm.firebase.SignInManager;
import com.eddy.data.Constants;
import com.eddy.data.models.Book;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

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

    @BindView(R.id.rank_this_week)
    TextView rankThisWeekTextView;

    @BindView(R.id.rank_last_week)
    TextView rankLastWeekTextView;

    @BindView(R.id.weeks_on_list)
    TextView weeksOnListTextView;

    @BindView(R.id.sign_in_pb)
    ProgressBar signInProgressBar;

    @BindView(R.id.fab)
    FloatingActionButton fab;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        fab.setOnClickListener((view) -> {
            SignInManager signInManager = SignInManager.getInstance();

            if(signInManager.userLoggedIn()) {
                determineFabAction();
            } else {
                Snackbar.make(view, getString(R.string.not_signed_message), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.snackbar_sigin), BookDetailActivity.this)
                        .show();
            }
        });

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {
            book = intent.getParcelableExtra(BOOK_DETAIL_EXTRA);

            if (book != null) {
                showBookmarkState();

                Picasso.get()
                        .load(book.getBookImageUrl())
                        .into(bookImageView);

                collapsingToolbarLayout.setTitle(book.getTitle());
                bookDescription.setText(book.getDescription());
                bookAuthor.setText(book.getAuthor());
                bookPublisher.setText(book.getPublisher());
                rankThisWeekTextView.setText(String.valueOf(book.getRankThisWeek()));
                rankLastWeekTextView.setText(String.valueOf(book.getRankLastWeek()));
                weeksOnListTextView.setText(String.valueOf(book.getWeeksOnList()));
            }
        }
    }

    private void determineFabAction() {
        FirebaseDatabaseManager firebaseDatabaseManager = FirebaseDatabaseManager.getInstance();
        DatabaseReference databaseReference =firebaseDatabaseManager.getDatabaseReference()
                .child(Constants.BOOKMARKS_DB_REF);

        Query query = databaseReference.orderByChild(getString(R.string.db_child_title))
                .equalTo(book.getTitle());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() <= 0) {
                    saveBook();
                } else {
                    Book book = Utils.toList(dataSnapshot.getChildren()).get(0);
                    Timber.d("Book: %s", book);
                    deleteBook(book);
                }

                query.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    private void showBookmarkState() {
        SignInManager signInManager = SignInManager.getInstance();
        if (signInManager.userLoggedIn()) {
            FirebaseDatabaseManager firebaseDatabaseManager = FirebaseDatabaseManager.getInstance();
            DatabaseReference databaseReference = firebaseDatabaseManager.getDatabaseReference()
                    .child(Constants.BOOKMARKS_DB_REF);

            Query query = databaseReference
                    .orderByChild(getString(R.string.db_child_title))
                    .equalTo(book.getTitle());

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() == null) {
                        fab.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                    } else {
                        fab.setImageResource(R.drawable.ic_bookmark_black_24dp);
                    }

                    query.removeEventListener(this);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } else {
            Snackbar.make(findViewById(android.R.id.content),
                    getString(R.string.snackbar_sigin_for_bookmark), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.snackbar_sigin), BookDetailActivity.this)
                    .show();
        }
    }

    private void deleteBook(Book bookmarkedBook) {
        try {
            FirebaseDatabaseManager firebaseDatabaseManager = FirebaseDatabaseManager.getInstance();
            DatabaseReference databaseReference =firebaseDatabaseManager.getDatabaseReference()
                    .child(Constants.BOOKMARKS_DB_REF);

            if (!TextUtils.isEmpty(bookmarkedBook.getKey())) {
                databaseReference.child(bookmarkedBook.getKey())
                        .removeValue()
                        .addOnCompleteListener(task -> {
                            fab.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                            refreshFab();

                            Snackbar.make(findViewById(android.R.id.content),
                                    getString(R.string.removed_bookmark_message),
                                    Snackbar.LENGTH_LONG)
                                    .show();
                            finish();
                        });
            }
        } catch (Exception e) {
            Timber.e(e);
            Snackbar.make(findViewById(android.R.id.content),
                    getString(R.string.error_removing_bookmark_message),
                    Snackbar.LENGTH_LONG)
                    .show();
        }

    }

    private void refreshFab() {
        // Refresh FAA
        fab.hide();
        fab.show();
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
        SignInManager signInManager = SignInManager.getInstance();

        try {
            FirebaseDatabaseManager firebaseDatabaseManager = FirebaseDatabaseManager.getInstance();
            DatabaseReference databaseReference =firebaseDatabaseManager.getDatabaseReference()
                    .child(Constants.BOOKMARKS_DB_REF);

            if (TextUtils.isEmpty(book.getKey())) {
                book.setKey(databaseReference.push().getKey());
            }

            databaseReference.child(book.getKey()).setValue(book);

            Snackbar.make(findViewById(android.R.id.content),
                    String.format("Saving bookmark for %s",
                            signInManager.getCurrentUserName()),
                    Snackbar.LENGTH_LONG)
                    .show();
            fab.setImageResource(R.drawable.ic_bookmark_black_24dp);
            refreshFab();
        } catch (Exception e) {
            Snackbar.make(findViewById(android.R.id.content),
                    "Error saving bookmark",
                    Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onClick(View v) {
        super.signInWithFirebase();
    }
}
