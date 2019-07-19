package com.eddy.bookworm.categories;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.eddy.bookworm.AboutActivity;
import com.eddy.bookworm.PrivacyPolicyActivity;
import com.eddy.bookworm.R;
import com.eddy.bookworm.Utils;
import com.eddy.bookworm.base.customui.BookwormSwipeRefreshLayout;
import com.eddy.bookworm.books.list.BookmarksActivity;
import com.eddy.bookworm.books.list.BooksListActivity;
import com.eddy.data.SettingsActivity;
import com.eddy.data.models.entities.Category;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class CategoriesActivity extends AppCompatActivity implements
        CategoriesAdapter.ListNameListener, SwipeRefreshLayout.OnRefreshListener {

    private CategoriesViewModel categoriesViewModel;

    @BindView(R.id.list_names_rv)
    RecyclerView listNamesRecyclerView;

    @BindView(R.id.swipe_refresh_list_names)
    BookwormSwipeRefreshLayout swipeRefreshListNamesLayout;

    @BindView(R.id.no_internet_widgets)
    Group noInternetWidgets;

    CategoriesAdapter categoriesAdapter;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, CategoriesActivity.class);
    }

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
        switch (item.getItemId()) {
            case R.id.bookmark_menu_item:
                Intent booksintent = new Intent(this, BookmarksActivity.class);
                startActivity(booksintent);
                return true;
            case R.id.about_menu_item:
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                return true;
            case R.id.settings_menu_item:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.privacy_policy_menu_item:
                Intent privacyPolicyIntent = new Intent(this, PrivacyPolicyActivity.class);
                startActivity(privacyPolicyIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUpListNamesUI() {
        categoriesAdapter = new CategoriesAdapter(this, this);

        listNamesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        listNamesRecyclerView.setAdapter(categoriesAdapter);

        swipeRefreshListNamesLayout.setOnRefreshListener(this);
    }

    private void setUpViewModel() {
       categoriesViewModel = ViewModelProviders.of(this).get(
                CategoriesViewModel.class);


       categoriesViewModel.getCategoriesLiveData().observe(this, (categories) -> {
           if (categories != null) {
               showBooksList();
               categoriesAdapter.setCategories(categories);
           } else {
               Timber.d("No categories available");
           }
       });

       categoriesViewModel.getLoadingState().observe(this, (isLoading) -> {
           if (isLoading) {
               if (Utils.isConnected(this)) {
                   showProgressBar();
               } else {
                   showNoInternetUI();
               }
           } else {
               hideProgressBar();
           }
       });

       categoriesViewModel.getErrorLiveData().observe(this, (throwable) -> {
           Timber.e(throwable);
           Snackbar.make(listNamesRecyclerView,
                   throwable.getMessage(), Snackbar.LENGTH_SHORT).show();
       });
    }

    private void showNoInternetUI() {
        listNamesRecyclerView.setVisibility(View.GONE);
        noInternetWidgets.setVisibility(View.VISIBLE);
    }

    private void showBooksList() {
        listNamesRecyclerView.setVisibility(View.VISIBLE);
        noInternetWidgets.setVisibility(View.GONE);
    }

    protected void hideProgressBar() {
        swipeRefreshListNamesLayout.setRefreshing(false);
    }

    protected void showProgressBar() {
        swipeRefreshListNamesLayout.setRefreshing(true);
    }

    @Override
    public void onClick(Category category) {
        Intent intent = new Intent(this, BooksListActivity.class);
        intent.putExtra(BooksListActivity.CATEGORY_EXTRA, category);

        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        categoriesViewModel.refreshCategories();
    }
}
