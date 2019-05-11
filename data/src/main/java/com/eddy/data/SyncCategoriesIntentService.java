package com.eddy.data;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

public class SyncCategoriesIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public SyncCategoriesIntentService(String name) {
        super(name);
    }

    public SyncCategoriesIntentService() {
        super(SyncCategoriesIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        CategoriesDataSource categoriesDataSource = InjectorUtils.getCategoriesDataSource(this);
        categoriesDataSource.fetchCategories();
    }
}
