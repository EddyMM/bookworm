package com.eddy.bookworm.listnames;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

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
