package com.eddy.bookworm.books.list;

import android.os.Bundle;

import com.eddy.bookworm.R;
import com.eddy.bookworm.books.list.base.BaseBookListActivity;
import com.eddy.bookworm.books.list.viewmodel.BookmarksViewModel;
import com.eddy.data.models.BookWithBuyLinks;

import java.util.List;

import androidx.lifecycle.LiveData;
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

        refresh(bookmarksViewModel.getBookmarkedBooksLiveData());
    }

    private void refresh(LiveData<List<BookWithBuyLinks>> bookLiveData) {
        showBooksList();
        showProgressBar();

        bookLiveData.observe(this, bookWithBuyLinks -> {
            if (bookWithBuyLinks != null) {
                booksListAdapter.setBooks(bookWithBuyLinks);
                Timber.d("Books from DB: %s", bookWithBuyLinks);
            } else {
                Timber.d("No list names fetched");
            }

            hideProgressBar();
        });
    }

    @Override
    public void onRefresh() {
        refresh(bookmarksViewModel.getBookmarkedBooksLiveData());
    }
}
