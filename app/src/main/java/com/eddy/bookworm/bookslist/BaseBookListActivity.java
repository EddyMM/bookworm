package com.eddy.bookworm.bookslist;

import android.os.Bundle;

import com.eddy.bookworm.R;
import com.eddy.bookworm.base.BaseBookwormActivity;
import com.eddy.bookworm.base.BookwormSwipeRefreshLayout;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;

public abstract class BaseBookListActivity extends BaseBookwormActivity {

    @BindView(R.id.books_list_rv)
    RecyclerView booksRecyclerView;

    @BindView(R.id.swipe_refresh_book_list)
    BookwormSwipeRefreshLayout swipeRefreshBookListLayout;

    @BindView(R.id.no_internet_widgets)
    Group noInternetWidgets;

    protected BooksAdapter booksAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_list);
    }

    @Override
    protected void onCompleteSignIn() {

    }

    @Override
    protected void onBeginSignIn() {

    }

    protected void setUpBooksListUI(BooksAdapter.BooksListListener booksListListener,
                                    SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        booksAdapter = new BooksAdapter(this, booksListListener);
        booksRecyclerView.setAdapter(booksAdapter);

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
