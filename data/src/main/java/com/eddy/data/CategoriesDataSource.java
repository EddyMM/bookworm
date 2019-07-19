package com.eddy.data;

import android.content.Context;
import android.content.Intent;

import com.eddy.data.errors.CategoriesSyncThrowable;
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

    private final MutableLiveData<Boolean> syncInProgress = new MutableLiveData<>();

    private final MutableLiveData<Throwable> categoriesSyncError = new MutableLiveData<>();

    private CategoriesDataSource(Context context) {
        this.context = context;

        fetchedCategories = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> getSyncInProgress() {
        return syncInProgress;
    }

    public MutableLiveData<Throwable> getCategoriesSyncError() {
        return categoriesSyncError;
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
        setSyncInProgress(true);
        Intent intent = new Intent(context, SyncCategoriesIntentService.class);
        context.startService(intent);
    }

    void fetchCategories() {
        BooksApiService booksApiService = BooksApi.getInstance();
        Call<CategoriesResponse> listNameResponseCall = booksApiService.categories();

        try {
            Response<CategoriesResponse> response = listNameResponseCall.execute();
            CategoriesResponse categoriesResponse = response.body();
            if (response.errorBody() != null) {
                Timber.e("Error Response: %s", response.errorBody().string());
                categoriesSyncError.postValue(
                        new CategoriesSyncThrowable());
            }
            List<Category> categories = Objects.requireNonNull(categoriesResponse)
                    .getCategories();
            fetchedCategories.postValue(categories);
            setSyncInProgress(false);
        } catch (IOException e) {
            Timber.e(e);
            categoriesSyncError.postValue(new CategoriesSyncThrowable());
            setSyncInProgress(false);
        }
    }

    public void setSyncInProgress(boolean inProgress) {
        syncInProgress.postValue(inProgress);
    }
}
