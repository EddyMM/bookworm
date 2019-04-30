package com.eddy.bookworm.categories;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.eddy.bookworm.BuildConfig;
import com.eddy.data.models.entities.Category;
import com.eddy.data.repository.CategoriesRepository;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


public class ListNamesViewModel extends AndroidViewModel {

    public ListNamesViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Category>> getListNamesLiveData () {
        MutableLiveData<List<Category>> listNamesLiveData = new MutableLiveData<>();

        Executor executor = Executors.newFixedThreadPool(3);
        executor.execute(() -> {
            CategoriesRepository categoriesRepository = new CategoriesRepository();
            List<Category> categories = categoriesRepository
                    .fetchCategories(BuildConfig.NEW_YORK_TIMES_API_KEY);
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> listNamesLiveData.setValue(categories));
        });

        return listNamesLiveData;
    }

}
