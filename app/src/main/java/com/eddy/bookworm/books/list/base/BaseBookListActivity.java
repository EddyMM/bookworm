package com.eddy.bookworm.books.list.base;

import android.os.Bundle;

import com.eddy.bookworm.R;
import com.eddy.bookworm.base.customui.BookwormSwipeRefreshLayout;
import com.eddy.bookworm.books.list.BooksListAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;

public abstract class BaseBookListActivity extends AppCompatActivity {

    @BindView(R.id.books_list_rv)
    public RecyclerView booksRecyclerView;

    @BindView(R.id.swipe_refresh_book_list)
    public BookwormSwipeRefreshLayout swipeRefreshBookListLayout;

    @BindView(R.id.no_internet_widgets)
    public Group noInternetWidgets;

    protected BooksListAdapter booksListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_list);
    }

    protected void setUpBooksListUI(BooksListAdapter.BooksListListener booksListListener,
                                    SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        booksListAdapter = new BooksListAdapter(this, booksListListener);
        booksRecyclerView.setAdapter(booksListAdapter);

        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        getResources().getInteger(R.integer.no_of_columns),
                        StaggeredGridLayoutManager.VERTICAL);
        booksRecyclerView.setLayoutManager(staggeredGridLayoutManager);

        swipeRefreshBookListLayout.setOnRefreshListener(onRefreshListener);
    }

    protected void hideProgressBar() {
        swipeRefreshBookListLayout.setRefreshing(false);
    }

    protected void showProgressBar() {
        swipeRefreshBookListLayout.setRefreshing(true);
    }
}
