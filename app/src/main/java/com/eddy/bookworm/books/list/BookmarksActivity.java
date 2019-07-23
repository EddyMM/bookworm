package com.eddy.bookworm.books.list;

import android.os.Bundle;

import com.eddy.bookworm.R;
import com.eddy.bookworm.books.list.base.BaseBookListActivity;
import com.eddy.bookworm.books.list.viewmodel.BookmarksViewModel;

import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import timber.log.Timber;

public class BookmarksActivity  extends BaseBookListActivity
        implements SwipeRefreshLayout.OnRefreshListener {

    private BookmarksViewModel bookmarksViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.bookmarks));
        setUpBooksListUI(this, this);
        setUpBookmarksViewModel();
    }

    private void setUpBookmarksViewModel() {
        bookmarksViewModel = ViewModelProviders.of(this)
                .get(BookmarksViewModel.class);

        bookmarksViewModel.getBookmarkedBooksLiveData()
                .observe(this, (books) -> {
                    if (books != null) {
                        booksListAdapter.setBooks(books);
                        Timber.d("Books from DB: %s", books);
                    } else {
                        Timber.d("No list names fetched");
                    }
                });
    }

    @Override
    public void onRefresh() {
        bookmarksViewModel.refresh();
    }
}
