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
import com.eddy.data.models.BookWithBuyLinks;
import com.eddy.data.models.entities.Book;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BookDetailActivity extends AppCompatActivity {

    public static final String BOOK_WITH_BUY_LINKS_DETAIL_EXTRA = "BOOK_WITH_BUY_LINKS_DETAIL_EXTRA";

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
    private BookWithBuyLinks bookWithBuyLinks;
    private boolean signInForBookmarkAction; // Flag used to determine whether action to
    // perform on successful sign in is bookmarking or not

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        fab.setOnClickListener((view) -> {
            // Add bookmark tag
        });

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {
            bookWithBuyLinks = intent.getParcelableExtra(BOOK_WITH_BUY_LINKS_DETAIL_EXTRA);

            updateUI(bookWithBuyLinks);
        }
    }

    private void updateUI(BookWithBuyLinks bookWithBuyLinks) {
        if (bookWithBuyLinks != null) {
            showBookmarkState();

            Book book = bookWithBuyLinks.getBook();

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
                    this, bookWithBuyLinks.getBuyLinks());
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

    private void determineFabAction() {
        // ON or OFF?
    }

    private void showBookmarkState() {
        // Bookmarked or Not?
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
}
