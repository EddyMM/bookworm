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

    public synchronized static CategoriesRepository getInstance(
            CategoriesDataSource categoriesDataSource, CategoryDao categoryDao) {
        if (CATEGORIES_REPOSITORY == null) {
            synchronized (LOCK) {
                CATEGORIES_REPOSITORY = new CategoriesRepository(categoriesDataSource,
                        categoryDao);
            }
        }

        return CATEGORIES_REPOSITORY;
    }

    private CategoriesRepository(CategoriesDataSource categoriesDataSource,
                                 CategoryDao categoryDao) {
        this.categoriesDataSource = categoriesDataSource;
        this.categoryDao = categoryDao;

        LiveData<List<Category>> categoriesLiveData = categoriesDataSource.
                getFetchedCategories();

        // If syncing in the background, no need to observe data changes

        categoriesLiveData.observeForever((categories) -> {
            Timber.d("Categories from API: %s", categories.toString());
            Executors.newFixedThreadPool(1).execute(() ->
                    categoryDao.addCategories(categories));
        });
    }

    private boolean fetchNeeded() {
        return categoryDao.countCategories() <= 0;
    }

    public LiveData<List<Category>> getCategories() {
        Executors.newFixedThreadPool(1).execute(() -> {
            if (fetchNeeded()) {
                Timber.d("Fetching data afresh");
                syncCategories();
            } else {
                categoriesDataSource.setSyncInProgress(false);
            }
        });

        return categoryDao.getCategories();
    }

    public LiveData<Boolean> getSyncInProgressLiveData() {
        return categoriesDataSource.getSyncInProgress();
    }

    public LiveData<Throwable> getCategoriesErrorLiveData() {
        return categoriesDataSource.getCategoriesSyncError();
    }

    public void syncCategories() {
        categoriesDataSource.syncCategories();
    }
}
