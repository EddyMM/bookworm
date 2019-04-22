package com.eddy.bookworm.listnames;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.eddy.bookworm.R;
import com.eddy.bookworm.base.BaseBookwormActivity;
import com.eddy.bookworm.base.BookwormSwipeRefreshLayout;
import com.eddy.bookworm.bookslist.BooksListActivity;
import com.eddy.data.models.ListName;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ListNamesActivity extends BaseBookwormActivity implements
        ListNamesAdapter.ListNameListener, SwipeRefreshLayout.OnRefreshListener {

    private ListNamesViewModel listNamesViewModel;

    @BindView(R.id.list_names_rv)
    RecyclerView listNamesRecyclerView;

    @BindView(R.id.swipe_refresh_list_names)
    BookwormSwipeRefreshLayout swipeRefreshListNamesLayout;

    ListNamesAdapter listNamesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_names);

        ButterKnife.bind(this);

        setUpListNamesUI();

        setTitle(getString(R.string.categories_title));
        setUpViewModel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.categories_list_menu, menu);

        super.onCreateOptionsMenu(menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.bookmark_menu_item) {
            Intent intent = new Intent(this, BooksListActivity.class);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpListNamesUI() {
        listNamesAdapter = new ListNamesAdapter(this, this);

        listNamesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        listNamesRecyclerView.setAdapter(listNamesAdapter);

        swipeRefreshListNamesLayout.setOnRefreshListener(this);
    }

    private void setUpViewModel() {
       listNamesViewModel = ViewModelProviders.of(this).get(
                ListNamesViewModel.class);
        refresh();
    }

    private void refresh() {
        showProgressBar();

        listNamesViewModel.getListNamesLiveData().observe(this, listNames -> {
            if (listNames != null) {
                listNamesAdapter.setListNames(listNames);
            }
            else {
                Timber.d("No list names fetched");
            }

            hideProgressBar();
        });
    }

    protected void hideProgressBar() {
        swipeRefreshListNamesLayout.setRefreshing(false);
    }

    protected void showProgressBar() {
        swipeRefreshListNamesLayout.setRefreshing(true);
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
    public void onClick(ListName listName) {
        Intent intent = new Intent(this, BooksListActivity.class);
        intent.putExtra(
                BooksListActivity.LIST_NAME_ENCODED_EXTRA,
                listName.getListNameEncoded()
        );
        intent.putExtra(
                BooksListActivity.DISPLAY_NAME_ENCODED_EXTRA,
                listName.getDisplayName()
        );

        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        refresh();
    }

}
