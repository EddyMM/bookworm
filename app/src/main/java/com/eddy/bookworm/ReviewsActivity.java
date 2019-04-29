package com.eddy.bookworm;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class ReviewsActivity extends AppCompatActivity {

    public static final String REVIEWS_URL = "REVIEWS_URL";
    public static final String BOOK_TITLE = "BOOK_TITLE";

    @BindView(R.id.reviews_webview)
    WebView reviewsWebView;

    public static Intent getIntent(Context context, String bookTitle, String reviewUrl) {
        Intent intent = new Intent(context, ReviewsActivity.class);
        intent.putExtra(ReviewsActivity.REVIEWS_URL, reviewUrl);
        intent.putExtra(ReviewsActivity.BOOK_TITLE, bookTitle);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        ButterKnife.bind(this);

        String reviewUrl = getIntent().getStringExtra(REVIEWS_URL);
        String bookTitle = getIntent().getStringExtra(BOOK_TITLE);

        if (bookTitle != null) {
            setTitle(bookTitle);
        }

        if (reviewUrl != null) {
            loadWebPage(reviewUrl);
        }
    }

    private void loadWebPage(String url) {
        reviewsWebView.loadUrl(url);
    }
}
