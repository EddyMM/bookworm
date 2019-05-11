package com.eddy.data;

import android.content.Context;
import android.content.Intent;

import com.eddy.data.models.entities.Category;
import com.eddy.data.models.rest.CategoriesResponse;
import com.eddy.data.rest.BooksApi;
import com.eddy.data.rest.BooksApiService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

public class CategoriesDataSource {
    private static CategoriesDataSource CATEGORIES_DATA_SOURCE;
    private static final Object LOCK = new Object();
    private final Context context;

    private final MutableLiveData<List<Category>> fetchedCategories;

    private CategoriesDataSource(Context context) {
        this.context = context;

        fetchedCategories = new MutableLiveData<>();
    }

    public synchronized static CategoriesDataSource getInstance(Context context) {
        if (CATEGORIES_DATA_SOURCE == null) {
            synchronized (LOCK) {
                CATEGORIES_DATA_SOURCE = new CategoriesDataSource(context);
            }
        }

        return CATEGORIES_DATA_SOURCE;
    }

    public MutableLiveData<List<Category>> getFetchedCategories() {
        return fetchedCategories;
    }

    public void syncCategories() {
        Intent intent = new Intent(context, SyncCategoriesIntentService.class);
        context.startService(intent);
    }

    public void fetchCategories() {
        BooksApiService booksApiService = BooksApi.getInstance();
        Call<CategoriesResponse> listNameResponseCall = booksApiService.categories();

        try {
            Response<CategoriesResponse> response = listNameResponseCall.execute();
            CategoriesResponse categoriesResponse = response.body();
            List<Category> categories = Objects.requireNonNull(categoriesResponse)
                    .getCategories();
            fetchedCategories.postValue(categories);
        } catch (IOException e) {
            Timber.e(e);
        }
    }
}
