package com.eddy.bookworm.listnames;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.eddy.bookworm.bookslist.BooksListActivity;
import com.eddy.bookworm.R;
import com.eddy.data.models.ListName;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ListNamesActivity extends AppCompatActivity implements ListNamesAdapter.ListNameListener {

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

        setUpViewModel();
    }

    private void setUpListNamesRecyclerView() {
        listNamesAdapter = new ListNamesAdapter(this, this);

        listNamesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        listNamesRecyclerView.setAdapter(listNamesAdapter);
    }

    private void setUpViewModel() {
        ListNamesViewModel listNamesViewModel = ViewModelProviders.of(this).get(
                ListNamesViewModel.class);

        showProgressBar();

        listNamesViewModel.listNamesLiveData.observe(this, listNames -> {
            if (listNames != null) {
                listNamesAdapter.setListNames(listNames);
            }
            else {
                Timber.d("No list names fetched");
            }

            hideProgressBar();
        });
    }

    private void hideProgressBar() {
        listNamesProgressBar.setVisibility(View.GONE);
    }

    private void showProgressBar() {
        listNamesProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(ListName listName) {
        Intent intent = new Intent(this, BooksListActivity.class);
        intent.putExtra(
                BooksListActivity.LIST_NAME_ENCODED_EXTRA,
                listName.getListNameEncoded()
        );

        startActivity(intent);
    }
}