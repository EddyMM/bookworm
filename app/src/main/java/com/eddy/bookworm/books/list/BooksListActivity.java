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

    public static final String CATEGORY_EXTRA = "category";

    private Category category;

    private BooksListViewModel booksListViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

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
        refresh(booksListViewModel.getBooksLiveData(category));
    }

    private void refresh(LiveData<List<BookWithBuyLinks>> bookLiveData) {
        booksListViewModel.syncNeeded(category).observe(this, (syncNeeded) -> {
            if (!Utils.isConnected(this) && syncNeeded) {
                showNoInternetUI();
            } else {
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
    public void onClick(BookWithBuyLinks bookWithBuyLinks, ImageView bookImageView) {
        Intent intent = new Intent(this, BookDetailActivity.class);
        intent.putExtra(BookDetailActivity.BOOK_WITH_BUY_LINKS_DETAIL_EXTRA, bookWithBuyLinks);

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
        LiveData<List<BookWithBuyLinks>> booksLiveData = new MutableLiveData<>();
        if (category != null) {
            booksLiveData = booksListViewModel.getBooksLiveData(category);
        }
        refresh(booksLiveData);
    }
}
