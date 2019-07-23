package com.eddy.bookworm.books.list.base;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.eddy.bookworm.R;
import com.eddy.bookworm.base.customui.BookwormSwipeRefreshLayout;
import com.eddy.bookworm.books.detail.BookDetailActivity;
import com.eddy.bookworm.books.list.BooksListAdapter;
import com.eddy.data.models.entities.Book;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseBookListActivity extends AppCompatActivity implements
        BooksListAdapter.BooksListListener {

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

        ButterKnife.bind(this);
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

    @Override
    public void onClick(Book book, ImageView imageView) {
        Intent intent = new Intent(this, BookDetailActivity.class);
        intent.putExtra(BookDetailActivity.BOOK_DETAIL_EXTRA, book);

        if (imageView != null) {
            Bundle bundle = getBookTransition(imageView);
            startActivity(intent, bundle);
        } else {
            startActivity(intent);
        }
    }

    private Bundle getBookTransition(View view) {
        Bundle bundle = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            bundle = ActivityOptions
                    .makeSceneTransitionAnimation(
                            this,
                            view,
                            view.getTransitionName())
                    .toBundle();
        }
        return bundle;
    }

    protected void showNoInternetUI() {
        booksRecyclerView.setVisibility(View.GONE);
        noInternetWidgets.setVisibility(View.VISIBLE);
    }

    protected void showBooksList() {
        booksRecyclerView.setVisibility(View.VISIBLE);
        noInternetWidgets.setVisibility(View.GONE);
    }
}
