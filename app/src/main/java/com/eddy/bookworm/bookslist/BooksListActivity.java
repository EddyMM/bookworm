package com.eddy.bookworm.bookslist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.eddy.bookworm.Utils;
import com.eddy.bookworm.base.BaseBookwormActivity;
import com.eddy.bookworm.bookdetail.BookDetailActivity;
import com.eddy.bookworm.R;
import com.eddy.data.models.Book;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class BooksListActivity extends BaseBookwormActivity implements BooksAdapter.BooksListListener {

    public static final String LIST_NAME_ENCODED_EXTRA = "LIST_NAME_ENCODED_EXTRA";
    public static final String DISPLAY_NAME_ENCODED_EXTRA = "DISPLAY_NAME_ENCODED_EXTRA";

    private BooksAdapter booksAdapter;

    @BindView(R.id.books_list_rv)
    RecyclerView booksRecyclerView;

    @BindView(R.id.books_list_pb)
    ProgressBar booksListProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_list);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null) {
            String encodedListName = intent.getStringExtra(LIST_NAME_ENCODED_EXTRA);
            String displayListName = intent.getStringExtra(DISPLAY_NAME_ENCODED_EXTRA);

            setUpBooksListUI();

            if (encodedListName != null) {
                setTitle(displayListName);
                setUpBooksListViewModel(encodedListName);
            } else {
                // User is opening bookmarks
                setTitle(R.string.bookmarks);
                setUpBookmarksViewModel();
            }
        }
    }

    private void setUpBookmarksViewModel() {
        BookmarksViewModel booksListViewModel = ViewModelProviders
                .of(this)
                .get(BookmarksViewModel.class);

        showProgressBar();

        booksListViewModel.getDataSnapshotLiveData().observe(this, dataSnapshot -> {
            List<Book> books = Utils.toList(dataSnapshot.getChildren());

            if (books != null) {
                booksAdapter.setBooks(books);
            }
            else {
                Snackbar.make(findViewById(android.R.id.content),
                        "No bookmarks found",
                        Snackbar.LENGTH_LONG)
                        .show();
            }
            hideProgressBar();
        });
    }

    private void setUpBooksListUI() {
        booksAdapter = new BooksAdapter(this, this);
        booksRecyclerView.setAdapter(booksAdapter);

        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        booksRecyclerView.setLayoutManager(staggeredGridLayoutManager);
    }

    private void setUpBooksListViewModel(String encodedListName) {
        BooksListViewModel booksListViewModel = ViewModelProviders
                .of(this, new BooksListViewModelFactory(encodedListName))
                .get(BooksListViewModel.class);

        showProgressBar();

        booksListViewModel.booksLiveData.observe(this, books -> {
            if (books != null) {
                booksAdapter.setBooks(books);
            }
            else {
                Timber.d("No list names fetched");
            }

            hideProgressBar();
        });
    }

    protected void hideProgressBar() {
        booksListProgressBar.setVisibility(View.GONE);
    }

    protected void showProgressBar() {
        booksListProgressBar.setVisibility(View.VISIBLE);
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
    public void onClick(Book book) {
        Intent intent = new Intent(this, BookDetailActivity.class);
        intent.putExtra(BookDetailActivity.BOOK_DETAIL_EXTRA, book);

        startActivity(intent);
    }
}
