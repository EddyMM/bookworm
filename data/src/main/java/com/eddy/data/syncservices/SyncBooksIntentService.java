package com.eddy.data.syncservices;

import android.app.IntentService;
import android.content.Intent;

import com.eddy.data.datasources.BooksListDataSource;
import com.eddy.data.models.entities.Category;

import androidx.annotation.Nullable;

public class SyncBooksIntentService extends IntentService {
    public static final String CATEGORY_EXTRA = "category";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public SyncBooksIntentService(String name) {
        super(name);
    }

    public SyncBooksIntentService() {
        super(SyncBooksIntentService.class.getName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        BooksListDataSource booksListDataSource = BooksListDataSource.getInstance(this);
        if (intent != null) {
            Category category = intent.getParcelableExtra(CATEGORY_EXTRA);
            booksListDataSource.fetchBooks(category);
        }
    }
}
