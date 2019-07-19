package com.eddy.bookworm.books.list;

import android.content.Intent;
import android.os.Bundle;

import com.eddy.bookworm.Utils;
import com.eddy.bookworm.books.list.base.BaseBookListActivity;
import com.eddy.bookworm.books.list.viewmodel.BooksListViewModel;
import com.eddy.bookworm.books.list.viewmodel.BooksListViewModelFactory;
import com.eddy.data.models.entities.Category;
import com.google.android.material.snackbar.Snackbar;

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
        booksListViewModel = ViewModelProviders.of(
                this, new BooksListViewModelFactory(this, category)).get(
                        BooksListViewModel.class);

        // List of books
        booksListViewModel.getBooksLiveData().observe(this, (bookWithBuyLinks -> {
            if (bookWithBuyLinks != null) {
                booksListAdapter.setBooks(bookWithBuyLinks);
            } else {
                Timber.d("No books fetched");
            }
        }));

        // Loading state
        booksListViewModel.getBooksSyncInProgress().observe(this, (isLoading) -> {
            if (isLoading) {
                if (Utils.isConnected(this)) {
                    showBooksList();
                    showProgressBar();
                } else {
                    showNoInternetUI();
                }
            } else {
                hideProgressBar();
            }
        });

        // Errors
        booksListViewModel.getErrorLiveData().observe(this, (throwable ->
                Snackbar.make(booksRecyclerView, throwable.getMessage()
                , Snackbar.LENGTH_SHORT).show()));
    }


    @Override
    public void onRefresh() {
        booksListViewModel.refreshBooks();
    }
}
