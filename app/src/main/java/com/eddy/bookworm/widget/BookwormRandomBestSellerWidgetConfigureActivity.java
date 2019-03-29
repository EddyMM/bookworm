package com.eddy.bookworm.widget;

import android.os.Bundle;
import android.widget.Toast;

import com.eddy.bookworm.R;
import com.eddy.bookworm.base.BaseBookwormActivity;
import com.eddy.bookworm.listnames.ListNamesAdapter;
import com.eddy.bookworm.listnames.ListNamesViewModel;
import com.eddy.data.AppDataManager;
import com.eddy.data.models.ListName;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * The configuration screen for the {@link BookwormRandomBestSellerWidget BookwormRandomBestSellerWidget} AppWidget.
 */
public class BookwormRandomBestSellerWidgetConfigureActivity extends BaseBookwormActivity implements ListNamesAdapter.ListNameListener {

    @BindView(R.id.widget_select_category_rv)
    RecyclerView categoryRecyclerView;

    ListNamesAdapter listNamesAdapter;

    public BookwormRandomBestSellerWidgetConfigureActivity() {
        super();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);
        setContentView(R.layout.bookworm_random_best_seller_widget_configure);

        ButterKnife.bind(this);

        setTitle(getString(R.string.categories_title));
        setUpSelectCategoriesRecyclerView();
        setUpViewModel();
    }

    private void setUpSelectCategoriesRecyclerView() {
        listNamesAdapter = new ListNamesAdapter(this, this);

        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoryRecyclerView.setAdapter(listNamesAdapter);
    }

    private void setUpViewModel() {
        ListNamesViewModel listNamesViewModel = ViewModelProviders.of(this).get(
                ListNamesViewModel.class);

        listNamesViewModel.getListNamesLiveData().observe(this, listNames -> {
            if (listNames != null) {
                listNamesAdapter.setListNames(listNames);
            }
            else {
                Timber.d("No list names fetched");
            }
        });
    }

    @Override
    protected void onCompleteSignIn() {

    }

    @Override
    protected void onBeginSignIn() {

    }

    @Override
    public void onClick(ListName listName) {
        AppDataManager appDataManager = new AppDataManager();
        appDataManager.setPreferredCategoryCode(this, listName.getListNameEncoded());

        Toast.makeText(this,
                String.format("Saved %s as the widget category", listName.getListNameEncoded()),
                Toast.LENGTH_LONG)
        .show();

        setResult(RESULT_OK);
        finish();
    }
}

