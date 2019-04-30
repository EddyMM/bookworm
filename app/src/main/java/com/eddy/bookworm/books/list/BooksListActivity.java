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
import com.eddy.bookworm.models.ParcelableBook;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.ButterKnife;
import timber.log.Timber;

public class BooksListActivity extends BaseBookListActivity implements
        BooksListAdapter.BooksListListener, SwipeRefreshLayout.OnRefreshListener {

    public static final String LIST_NAME_ENCODED_EXTRA = "LIST_NAME_ENCODED_EXTRA";
    public static final String DISPLAY_NAME_ENCODED_EXTRA = "DISPLAY_NAME_ENCODED_EXTRA";

    private String encodedListName;

    private BooksListViewModel booksListViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null) {
            encodedListName = intent.getStringExtra(LIST_NAME_ENCODED_EXTRA);
            String displayListName = intent.getStringExtra(DISPLAY_NAME_ENCODED_EXTRA);

            setUpBooksListUI(this, this);

            if (encodedListName != null) {
                setTitle(displayListName);
                setUpBooksListViewModel();
            }
        }
    }

    private void setUpBooksListViewModel() {
        booksListViewModel = ViewModelProviders.of(this)
                .get(BooksListViewModel.class);
        refresh(booksListViewModel.getBooksLiveData(encodedListName));
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
                        booksListAdapter.setBooks(books);
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
        LiveData<List<ParcelableBook>> booksLiveData = new MutableLiveData<>();
        if (encodedListName != null) {
            booksLiveData = booksListViewModel.getBooksLiveData(encodedListName);
        }
        refresh(booksLiveData);
    }
}
