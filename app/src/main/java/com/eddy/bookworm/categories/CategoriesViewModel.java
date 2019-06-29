package com.eddy.bookworm.categories;

import android.app.Application;

import com.eddy.data.InjectorUtils;
import com.eddy.data.models.entities.Category;
import com.eddy.data.repository.CategoriesRepository;

import java.util.List;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


public class CategoriesViewModel extends AndroidViewModel {
    public CategoriesViewModel(@NonNull Application application) {
        super(application);
    }

    private LiveData<List<Category>> categoriesLiveData;

    LiveData<List<Category>> getCategoriesLiveData() {
        return categoriesLiveData;
    }

    LiveData<Boolean> syncNeeded() {
        CategoriesRepository categoriesRepository = InjectorUtils
                .getCategoriesRepository(getApplication());

        MutableLiveData<Boolean> syncNeeded = new MutableLiveData<>();

        Executors.newSingleThreadExecutor().execute(() -> {
            boolean fetchNeeded = categoriesRepository.fetchNeeded();
            syncNeeded.postValue(fetchNeeded);
        });

        return syncNeeded;
    }

    LiveData<Boolean> fetchCategories(boolean forceFetchOnline) {
        CategoriesRepository categoriesRepository = InjectorUtils
                .getCategoriesRepository(getApplication());
        categoriesLiveData = categoriesRepository.getCategories(forceFetchOnline);

        return categoriesRepository.getSyncInProgressLiveData();
    }
}
