package com.eddy.bookworm.bookslist;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.eddy.bookworm.R;
import com.eddy.bookworm.Utils;
import com.eddy.bookworm.base.BaseBookwormActivity;
import com.eddy.bookworm.base.BookwormSwipeRefreshLayout;
import com.eddy.bookworm.bookdetail.BookDetailActivity;
import com.eddy.bookworm.firebase.SignInManager;
import com.eddy.bookworm.models.ParcelableBook;
import com.eddy.bookworm.models.mappers.ParcelableBookMapper;
import com.eddy.data.models.Book;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BookmarksListActivity extends BaseBookwormActivity implements
        BooksAdapter.BooksListListener, SwipeRefreshLayout.OnRefreshListener {

    private BooksAdapter booksAdapter;

    private BookmarksViewModel booksListViewModel;

    @BindView(R.id.books_list_rv)
    RecyclerView booksRecyclerView;

    @BindView(R.id.swipe_refresh_book_list)
    BookwormSwipeRefreshLayout swipeRefreshBookListLayout;

    @BindView(R.id.no_internet_widgets)
    Group noInternetWidgets;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_list);

        ButterKnife.bind(this);

        // User is opening bookmarks
        setTitle(R.string.bookmarks);
        if (SignInManager.getInstance().userLoggedIn()) {
            setUpBooksListUI();
            setUpBookmarksViewModel();
        }
    }

    private void setUpBooksListUI() {
        booksAdapter = new BooksAdapter(this, this);
        booksRecyclerView.setAdapter(booksAdapter);

        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        booksRecyclerView.setLayoutManager(staggeredGridLayoutManager);

        swipeRefreshBookListLayout.setOnRefreshListener(this);
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
                booksAdapter.setBooks(parcelableBooks);
            }

            hideProgressBar();
        });
    }

    protected void hideProgressBar() {
        swipeRefreshBookListLayout.setRefreshing(false);
    }

    protected void showProgressBar() {
        swipeRefreshBookListLayout.setRefreshing(true);
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
