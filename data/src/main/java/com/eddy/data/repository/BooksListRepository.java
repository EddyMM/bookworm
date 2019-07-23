package com.eddy.data.repository;

import com.eddy.data.errors.BooksListSyncThrowable;
import com.eddy.data.models.entities.Book;
import com.eddy.data.models.entities.Category;
import com.eddy.data.models.rest.BooksResponse;
import com.eddy.data.models.rest.BooksResults;
import com.eddy.data.rest.BooksApi;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

public class BooksListRepository {

    private MutableLiveData<List<Book>> booksLiveData;
    private MutableLiveData<Boolean> loadingInProgressLiveData;
    private MutableLiveData<Throwable> booksErrorLiveData;
    private Category category;

    public BooksListRepository(Category category) {
        this.category = category;

        booksLiveData = new MutableLiveData<>();
        loadingInProgressLiveData = new MutableLiveData<>();
        booksErrorLiveData = new MutableLiveData<>();

        fetchBooks();
    }

    public LiveData<List<Book>> getBooksLiveData() {
        return booksLiveData;
    }

    public LiveData<Boolean> getLoadingInProgressLiveData() {
        return loadingInProgressLiveData;
    }

    public LiveData<Throwable> getBooksErrorLiveData() {
        return booksErrorLiveData;
    }

    public void fetchBooks() {
        setLoadingInProgressLiveData(true);

        Executors.newSingleThreadExecutor().execute(() -> {
            Call<BooksResponse> booksResponseCall = BooksApi.getInstance()
                    .listBooks(category.getCategoryCode());

            try {
                Response<BooksResponse> response = booksResponseCall.execute();
                BooksResponse booksResponse = response.body();
                if (!response.isSuccessful()) {
                    booksErrorLiveData.postValue(new BooksListSyncThrowable());
                }
                BooksResults booksResults = Objects.requireNonNull(booksResponse)
                        .getBooksResults();
                List<Book> books =  booksResults.getBooks();

                booksLiveData.postValue(books);
            } catch (IOException e) {
                Timber.e(e);
                booksErrorLiveData.postValue(new BooksListSyncThrowable());
            } finally {
                setLoadingInProgressLiveData(false);
            }
        });
    }

    private void setLoadingInProgressLiveData(boolean inProgress) {
        loadingInProgressLiveData.postValue(inProgress);
    }

    public void refresh() {
        fetchBooks();
    }
}
