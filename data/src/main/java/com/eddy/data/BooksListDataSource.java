package com.eddy.data;

import android.content.Context;
import android.content.Intent;

import com.eddy.data.models.CategoryWithBooks;
import com.eddy.data.models.entities.Book;
import com.eddy.data.models.entities.Category;
import com.eddy.data.models.rest.BooksResponse;
import com.eddy.data.models.rest.BooksResults;
import com.eddy.data.rest.BooksApi;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

public class BooksListDataSource {

    private static BooksListDataSource BOOKS_LIST_DATA_SOURCE;
    private static final Object LOCK = new Object();
    private Context context;
    private MutableLiveData<CategoryWithBooks> booksLiveData;

    public BooksListDataSource(Context context) {
        this.context = context;
        booksLiveData = new MutableLiveData<>();
    }

    public synchronized static BooksListDataSource getInstance(Context context) {
        if (BOOKS_LIST_DATA_SOURCE == null) {
            synchronized (LOCK) {
                BOOKS_LIST_DATA_SOURCE = new BooksListDataSource(context);
            }
        }

        return BOOKS_LIST_DATA_SOURCE;
    }

    public MutableLiveData<CategoryWithBooks> getBooksLiveData() {
        return booksLiveData;
    }

    public void startSyncingBooks(Category category) {
        Intent intent = new Intent(context, SyncBooksIntentService.class);
        intent.putExtra(SyncBooksIntentService.CATEGORY_EXTRA, category);
        context.startService(intent);
    }

    public void fetchBooks(Category category) {
        Call<BooksResponse> booksResponseCall = BooksApi.getInstance()
                .listBooks(category.getCategoryCode());

        try {
            Response<BooksResponse> response = booksResponseCall.execute();
            BooksResponse booksResponse = response.body();
            BooksResults booksResults = Objects.requireNonNull(booksResponse)
                    .getBooksResults();
            List<Book> books =  booksResults.getBooks();

            booksLiveData.postValue(new CategoryWithBooks(category, books));
        } catch (IOException e) {
            Timber.e(e);
        }

    }
}
