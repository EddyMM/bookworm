package com.eddy.bookworm.bookslist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.eddy.bookworm.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class BooksListActivity extends AppCompatActivity {

    public static final String LIST_NAME_ENCODED_EXTRA = "LIST_NAME_ENCODED_EXTRA";

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

            setUpBooksListUI();
            setUpViewModel(encodedListName);
        }
    }

    private void setUpBooksListUI() {
        booksAdapter = new BooksAdapter(this);
        booksRecyclerView.setAdapter(booksAdapter);

        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        booksRecyclerView.setLayoutManager(staggeredGridLayoutManager);
    }

    private void setUpViewModel(String encodedListName) {
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

    private void hideProgressBar() {
        booksListProgressBar.setVisibility(View.GONE);
    }

    private void showProgressBar() {
        booksListProgressBar.setVisibility(View.VISIBLE);
    }
}
