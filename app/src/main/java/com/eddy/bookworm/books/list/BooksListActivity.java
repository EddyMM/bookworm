package com.eddy.bookworm.books.list;

import android.content.Intent;
import android.os.Bundle;

import com.eddy.bookworm.Utils;
import com.eddy.bookworm.books.list.base.BaseBookListActivity;
import com.eddy.bookworm.books.list.viewmodel.BooksListViewModel;
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

        booksListViewModel.getBooksSyncInProgress(category, forceFetchOnline)
            .observe(this, (inProgress) -> {
                if (inProgress) {
                    showProgressBar();
                } else {
                    Timber.d("Nothing");
                    booksListViewModel.getBooksLiveData()
                        .observe(this, bookWithBuyLinks -> {
                            if (bookWithBuyLinks != null) {
                                booksListAdapter.setBooks(bookWithBuyLinks);
                                Timber.d("Books from DB: %s", bookWithBuyLinks);
                            } else {
                                Timber.d("No books fetched");
                            }
                            hideProgressBar();
                        });
                }
            });
    }

    @Override
    public void onRefresh() {
        refresh(true);
    }
}
