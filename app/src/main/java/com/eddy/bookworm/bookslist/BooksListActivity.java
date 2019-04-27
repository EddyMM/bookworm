package com.eddy.bookworm.bookslist;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.eddy.bookworm.R;
import com.eddy.bookworm.Utils;
import com.eddy.bookworm.base.BaseBookwormActivity;
import com.eddy.bookworm.base.BookwormSwipeRefreshLayout;
import com.eddy.bookworm.bookdetail.BookDetailActivity;
import com.eddy.bookworm.firebase.SignInManager;
import com.eddy.bookworm.models.ParcelableBook;
import com.eddy.bookworm.models.mappers.ParcelableBookMapper;
import com.eddy.data.models.BookEntity;
import com.eddy.domain.Book;
import com.eddy.domain.mappers.BookMapper;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class BooksListActivity extends BaseBookwormActivity implements
        BooksAdapter.BooksListListener, SwipeRefreshLayout.OnRefreshListener {

    public static final String LIST_NAME_ENCODED_EXTRA = "LIST_NAME_ENCODED_EXTRA";
    public static final String DISPLAY_NAME_ENCODED_EXTRA = "DISPLAY_NAME_ENCODED_EXTRA";

    private String encodedListName;

    private BooksAdapter booksAdapter;
    private BooksListViewModel booksListViewModel;

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

        Intent intent = getIntent();
        if (intent != null) {
            encodedListName = intent.getStringExtra(LIST_NAME_ENCODED_EXTRA);
            String displayListName = intent.getStringExtra(DISPLAY_NAME_ENCODED_EXTRA);

            setUpBooksListUI();

            if (encodedListName != null) {
                setTitle(displayListName);
                setUpBooksListViewModel();
            } else {
                // User is opening bookmarks
                setTitle(R.string.bookmarks);
                if (SignInManager.getInstance().userLoggedIn()) {
                    setUpBookmarksViewModel();
                }
            }
        }
    }

    private void setUpBookmarksViewModel() {
        BookmarksViewModel booksListViewModel = ViewModelProviders
                .of(this)
                .get(BookmarksViewModel.class);

        refresh(booksListViewModel.getDataSnapshotLiveData(this));
    }

    private void setUpBooksListUI() {
        booksAdapter = new BooksAdapter(this, this);
        booksRecyclerView.setAdapter(booksAdapter);

        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        booksRecyclerView.setLayoutManager(staggeredGridLayoutManager);

        swipeRefreshBookListLayout.setOnRefreshListener(this);
    }

    private void setUpBooksListViewModel() {
        booksListViewModel = ViewModelProviders.of(this)
                .get(BooksListViewModel.class);
        refresh(booksListViewModel.getBooksLiveData(encodedListName));
    }

    protected void hideProgressBar() {
        swipeRefreshBookListLayout.setRefreshing(false);
    }

    protected void showProgressBar() {
        swipeRefreshBookListLayout.setRefreshing(true);
    }

    private void refresh(LiveData<List<ParcelableBook>> bookLiveData) {
        if (!Utils.isConnected(this)) {
            showNoInternetUI();
        } else {
            showBooksList();
        }
        showProgressBar();

        bookLiveData.observe(this, books -> {
                    if (books != null) {
                        booksAdapter.setBooks(books);
                    } else {
                        Timber.d("No list names fetched");
                    }

                    hideProgressBar();
                });
    }

    private void showNoInternetUI() {
        booksRecyclerView.setVisibility(View.GONE);
        noInternetWidgets.setVisibility(View.VISIBLE);
    }

    private void showBooksList() {
        booksRecyclerView.setVisibility(View.VISIBLE);
        noInternetWidgets.setVisibility(View.GONE);
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
    public void onRefresh() {
        LiveData<List<ParcelableBook>> booksLiveData;
        if (encodedListName != null) {
            booksLiveData = booksListViewModel.getBooksLiveData(encodedListName);
        } else {
            BookmarksViewModel booksListViewModel = ViewModelProviders
                    .of(this)
                    .get(BookmarksViewModel.class);
            booksLiveData = booksListViewModel.getDataSnapshotLiveData(BooksListActivity.this);
        }
        refresh(booksLiveData);
    }
}
