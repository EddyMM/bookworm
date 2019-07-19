package com.eddy.bookworm.categories;

import android.app.Application;

import com.eddy.data.InjectorUtils;
import com.eddy.data.models.entities.Category;
import com.eddy.data.repository.CategoriesRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


public class CategoriesViewModel extends AndroidViewModel {

    private final CategoriesRepository categoriesRepository;

    private LiveData<List<Category>> categoriesLiveData;
    private LiveData<Boolean> loadingLiveData;
    private LiveData<Throwable> errorLiveData;

    public CategoriesViewModel(@NonNull Application application) {
        super(application);

        categoriesRepository = InjectorUtils
                .getCategoriesRepository(getApplication());

        categoriesLiveData = categoriesRepository.getCategories();
        loadingLiveData = categoriesRepository.getSyncInProgressLiveData();
        errorLiveData = categoriesRepository.getCategoriesErrorLiveData();
    }

    LiveData<Throwable> getErrorLiveData() {
        return errorLiveData;
    }

    LiveData<List<Category>> getCategoriesLiveData() {
        return categoriesLiveData;
    }

    LiveData<Boolean> getLoadingState() {
        return loadingLiveData;
    }

    void refreshCategories() {
        categoriesRepository.syncCategories();
    }
}
