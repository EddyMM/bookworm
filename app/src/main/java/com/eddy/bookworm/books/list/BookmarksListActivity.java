package com.eddy.bookworm.books.list;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.eddy.bookworm.R;
import com.eddy.bookworm.Utils;
import com.eddy.bookworm.books.detail.BookDetailActivity;
import com.eddy.bookworm.books.list.base.BaseBookListActivity;
import com.eddy.bookworm.books.list.viewmodel.BookmarksViewModel;
import com.eddy.bookworm.firebase.SignInManager;
import com.eddy.bookworm.models.ParcelableBook;
import com.eddy.bookworm.models.mappers.ParcelableBookMapper;
import com.eddy.data.models.entities.Book;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.ButterKnife;

public class BookmarksListActivity extends BaseBookListActivity implements
        BooksListAdapter.BooksListListener, SwipeRefreshLayout.OnRefreshListener {

    private BookmarksViewModel booksListViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        // User is opening bookmarks
        setTitle(R.string.bookmarks);
        if (SignInManager.getInstance().userLoggedIn()) {
            setUpBooksListUI(this, this);
            setUpBookmarksViewModel();
        }
    }

    private void setUpBookmarksViewModel() {
        booksListViewModel = ViewModelProviders
                .of(this)
                .get(BookmarksViewModel.class);
        refresh();
    }

    private void refresh() {
        showProgressBar();

        booksListViewModel.getDataSnapshotLiveData().observe(this, dataSnapshot -> {
            List<Book> books = Utils.toList(dataSnapshot.getChildren());

            if (books != null) {
                List<ParcelableBook> parcelableBooks = new ParcelableBookMapper()
                        .transform(books);
                booksListAdapter.setBooks(parcelableBooks);
            }

            hideProgressBar();
        });
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    @Override
    protected void onCompleteSignIn() {
        hideProgressBar();
    }

    @Override
    protected void onBeginSignIn() {
        showProgressBar();
    }

    @Override
    public void onClick(ParcelableBook book, ImageView bookImageView) {
        Intent intent = new Intent(this, BookDetailActivity.class);
        intent.putExtra(BookDetailActivity.BOOK_DETAIL_EXTRA, book);

        if (bookImageView != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                Bundle bundle = ActivityOptions
                        .makeSceneTransitionAnimation(
                                this,
                                bookImageView,
                                bookImageView.getTransitionName())
                        .toBundle();

                startActivity(intent, bundle);
            }
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void onSuccessfulLogout() {
        super.onSuccessfulLogout();
        finish();
    }
}
