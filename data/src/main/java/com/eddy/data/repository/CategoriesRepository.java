package com.eddy.data.repository;

import com.eddy.data.CategoriesDataSource;
import com.eddy.data.dao.CategoryDao;
import com.eddy.data.models.entities.Category;

import java.util.List;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import timber.log.Timber;

public class CategoriesRepository {

    private static final Object LOCK = new Object();
    private static CategoriesRepository CATEGORIES_REPOSITORY;
    private final CategoriesDataSource categoriesDataSource;
    private final CategoryDao categoryDao;
    private LiveData<List<Category>> categoriesLiveData;

    public synchronized static CategoriesRepository getInstance(
            CategoriesDataSource categoriesDataSource, CategoryDao categoryDao) {
        if (CATEGORIES_REPOSITORY == null) {
            synchronized (LOCK) {
                CATEGORIES_REPOSITORY = new CategoriesRepository(categoriesDataSource, categoryDao);
            }
        }

        return CATEGORIES_REPOSITORY;
    }

    private CategoriesRepository(CategoriesDataSource categoriesDataSource, CategoryDao categoryDao) {
        this.categoriesDataSource = categoriesDataSource;
        this.categoryDao = categoryDao;

        categoriesLiveData = categoriesDataSource.getFetchedCategories();
        categoriesLiveData.observeForever((categories) -> {
            Timber.d("Categories from API: %s", categories.toString());
            Executors.newFixedThreadPool(1).execute(() ->
                    categoryDao.addCategories(categories));
        });
    }

    public boolean fetchNeeded() {
        return categoryDao.countCategories() <= 0;
    }

    public LiveData<List<Category>> getCategories() {
        Executors.newFixedThreadPool(1).execute(() -> {
            if (fetchNeeded()) {
                Timber.d("Fetching data afresh");
                categoriesDataSource.syncCategories();
            }
        });

        return categoryDao.getCategories();
    }
}
