package com.eddy.bookworm.listnames;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.eddy.bookworm.base.BaseBookwormActivity;
import com.eddy.bookworm.R;
import com.eddy.bookworm.bookslist.BooksListActivity;
import com.eddy.data.models.ListName;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ListNamesActivity extends BaseBookwormActivity implements ListNamesAdapter.ListNameListener {

    @BindView(R.id.list_names_rv)
    RecyclerView listNamesRecyclerView;

    @BindView(R.id.list_names_progress_bar)
    ProgressBar listNamesProgressBar;

    ListNamesAdapter listNamesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_names);

        ButterKnife.bind(this);

        setUpListNamesRecyclerView();

        setTitle(getString(R.string.categories_title));
        setUpViewModel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.categories_list_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.bookmark_menu_item) {
            Toast.makeText(this, "Book", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, BooksListActivity.class);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpListNamesRecyclerView() {
        listNamesAdapter = new ListNamesAdapter(this, this);

        listNamesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        listNamesRecyclerView.setAdapter(listNamesAdapter);
    }

    private void setUpViewModel() {
        ListNamesViewModel listNamesViewModel = ViewModelProviders.of(this).get(
                ListNamesViewModel.class);

        onBeginSignIn();

        listNamesViewModel.listNamesLiveData.observe(this, listNames -> {
            if (listNames != null) {
                listNamesAdapter.setListNames(listNames);
            }
            else {
                Timber.d("No list names fetched");
            }

            onCompleteSignIn();
        });
    }

    protected void onCompleteSignIn() {
        listNamesProgressBar.setVisibility(View.GONE);
    }

    protected void onBeginSignIn() {
        listNamesProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onSuccessfulSignIn() {

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
}
