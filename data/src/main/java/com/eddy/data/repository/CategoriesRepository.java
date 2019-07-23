package com.eddy.data.repository;

import com.eddy.data.errors.CategoriesSyncThrowable;
import com.eddy.data.models.entities.Category;
import com.eddy.data.models.rest.CategoriesResponse;
import com.eddy.data.rest.BooksApi;
import com.eddy.data.rest.BooksApiService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

public class CategoriesRepository {

    private final MutableLiveData<List<Category>> categoriesLiveData;
    private final MutableLiveData<Boolean> loadingInProgress;
    private final MutableLiveData<Throwable> categoriesError;

    public CategoriesRepository() {
        categoriesError = new MutableLiveData<>();
        loadingInProgress = new MutableLiveData<>();
        categoriesLiveData = new MutableLiveData<>();

        fetchCategories();
    }

    public MutableLiveData<List<Category>> getCategoriesLiveData() {
        return categoriesLiveData;
    }

    public MutableLiveData<Boolean> getLoadingInProgress() {
        return loadingInProgress;
    }

    public MutableLiveData<Throwable> getCategoriesError() {
        return categoriesError;
    }

    public void fetchCategories() {
        setLoadingInProgress(true);

        Executors.newSingleThreadExecutor().execute(() -> {
            BooksApiService booksApiService = BooksApi.getInstance();
            Call<CategoriesResponse> listNameResponseCall = booksApiService.categories();

            try {
                Response<CategoriesResponse> response = listNameResponseCall.execute();
                CategoriesResponse categoriesResponse = response.body();
                if (response.errorBody() != null) {
                    Timber.e("Error Response: %s", response.errorBody().string());
                    categoriesError.postValue(
                            new CategoriesSyncThrowable());
                }
                List<Category> categories = Objects.requireNonNull(categoriesResponse)
                        .getCategories();
                categoriesLiveData.postValue(categories);
                setLoadingInProgress(false);
            } catch (IOException e) {
                Timber.e(e);
                categoriesError.postValue(new CategoriesSyncThrowable());
                setLoadingInProgress(false);
            }
        });
    }

    private void setLoadingInProgress(boolean inProgress) {
        loadingInProgress.postValue(inProgress);
    }
}
