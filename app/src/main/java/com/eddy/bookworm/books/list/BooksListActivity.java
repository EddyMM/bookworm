package com.eddy.bookworm.books.list;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.eddy.bookworm.Utils;
import com.eddy.bookworm.books.detail.BookDetailActivity;
import com.eddy.bookworm.books.list.base.BaseBookListActivity;
import com.eddy.bookworm.books.list.viewmodel.BooksListViewModel;
import com.eddy.data.models.BookWithBuyLinks;
import com.eddy.data.models.entities.Category;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import timber.log.Timber;

public class BooksListActivity extends BaseBookListActivity
        implements SwipeRefreshLayout.OnRefreshListener {

    public static final String CATEGORY_EXTRA = "category";

    private Category category;
    private BooksListViewModel booksListViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent != null) {
            category = intent.getParcelableExtra(CATEGORY_EXTRA);
            String displayListName = category.getDisplayName();

            setUpBooksListUI(this, this);

            if (category != null) {
                setTitle(displayListName);
                setUpBooksListViewModel();
            }
        }
    }

    private void setUpBooksListViewModel() {
        booksListViewModel = ViewModelProviders.of(this)
                .get(BooksListViewModel.class);
        refresh(false);
    }

    private void refresh(boolean forceFetchOnline) {
        booksListViewModel.syncNeeded(category).observe(this, (syncNeeded) -> {
            if (!Utils.isConnected(this) && (syncNeeded || forceFetchOnline)) {
                showNoInternetUI();
            } else {
                showBooksList();
            }
        });

        if (forceFetchOnline) {
            booksListAdapter.setBooks(null);
        }

        showProgressBar();

        booksListViewModel.getBooksLiveData(category, forceFetchOnline)
            .observe(this, bookWithBuyLinks -> {
                if (bookWithBuyLinks != null) {
                    booksListAdapter.setBooks(bookWithBuyLinks);
                    hideProgressBar();
                    Timber.d("Books from DB: %s", bookWithBuyLinks);
                } else {
                    Timber.d("No books fetched");
                }
            });
    }

    @Override
    public void onClick(BookWithBuyLinks bookWithBuyLinks, ImageView bookImageView) {
        Intent intent = new Intent(this, BookDetailActivity.class);
        intent.putExtra(BookDetailActivity.BOOK_WITH_BUY_LINKS_DETAIL_EXTRA, bookWithBuyLinks);

        if (bookImageView != null) {
            Bundle bundle = getBookTransition(bookImageView);
            startActivity(intent, bundle);
        } else {
            startActivity(intent);
        }
    }

    private Bundle getBookTransition(View view) {
        Bundle bundle = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            bundle = ActivityOptions
                    .makeSceneTransitionAnimation(
                            this,
                            view,
                            view.getTransitionName())
                    .toBundle();
        }
        return bundle;
    }

    @Override
    public void onRefresh() {
        refresh(true);
    }
}
