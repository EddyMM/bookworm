package com.eddy.bookworm.books.list.viewmodel;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.eddy.data.InjectorUtils;
import com.eddy.data.models.entities.Book;
import com.eddy.data.repository.BooksListRepository;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class BooksListViewModel extends AndroidViewModel {

    public BooksListViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Book>> getBooksLiveData(String encodedListName) {

        MutableLiveData<List<Book>> booksLiveData = new MutableLiveData<>();

        Executor executor = Executors.newFixedThreadPool(3);
        executor.execute(() -> {
            BooksListRepository booksListRepository = InjectorUtils.getBooksListRepository(getApplication());
            List<Book> books = booksListRepository
                    .fetchBooks(encodedListName);
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> booksLiveData.setValue(books));
        });

        return booksLiveData;
    }
}
