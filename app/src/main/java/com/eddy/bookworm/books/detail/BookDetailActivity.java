package com.eddy.bookworm.books.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eddy.bookworm.R;
import com.eddy.bookworm.books.detail.viewmodel.BookDetailViewModel;
import com.eddy.bookworm.books.detail.viewmodel.BookDetailViewModelFactory;
import com.eddy.data.models.entities.Book;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class BookDetailActivity extends AppCompatActivity {

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

    @BindView(R.id.buying_links_recycler_view)
    RecyclerView buyingLinksRecyclerView;

    @BindView(R.id.book_reviews_section)
    CardView bookReviewsSectionCardView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private Book book;
    private BookDetailViewModel bookDetailViewModel;
    private boolean isBookmarked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        fab.setOnClickListener((view) -> {
            // Toggle bookmark tag
            if (isBookmarked) {
                removeFromBookmarks();
            } else {
                addToBookmarks();
            }
        });

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {
            book = intent.getParcelableExtra(BOOK_DETAIL_EXTRA);

            Timber.d("BOOK: %s", book);

            updateUI();

            setupBookDetailViewModel();
        }
    }

    private void setupBookDetailViewModel() {
        bookDetailViewModel = ViewModelProviders.of(
                this, new BookDetailViewModelFactory(this, book))
                .get(BookDetailViewModel.class);

        bookDetailViewModel.getBookmarkState().observe(
                this, (isBookmarked) -> {
                    showBookmarkState(isBookmarked);
                    this.isBookmarked = isBookmarked;
                });
    }

    private void addToBookmarks() {
        bookDetailViewModel.addBookmark(book);
    }

    private void removeFromBookmarks() {
        bookDetailViewModel.removeBookmark(book);
    }

    private void updateUI() {
        if (book != null) {
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

            BuyingLinksAdapter buyingLinksAdapter = new BuyingLinksAdapter(
                    this, book.getBuyLinks());
            buyingLinksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            buyingLinksRecyclerView.setAdapter(buyingLinksAdapter);

            String reviewsUrl = book.getReviewUrl();
            if (TextUtils.isEmpty(reviewsUrl)) {
                // Hide bookWithBuyLinks reviews section if no Review URL available
                bookReviewsSectionCardView.setVisibility(View.GONE);
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(reviewsUrl));
                bookReviewsSectionCardView.setOnClickListener(
                        v -> startActivity(intent));
            }
        }
    }

    private void showBookmarkState(boolean isBookmarked) {
        if (isBookmarked) {
            fab.setImageResource(R.drawable.ic_bookmark_black_24dp);
        } else {
            fab.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
        }

        refreshFab();
    }

    private void refreshFab() {
        // Refresh FAA
        fab.hide();
        fab.show();
    }
}
