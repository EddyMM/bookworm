package com.eddy.bookworm.books.list;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.eddy.bookworm.models.ParcelableBook;
import com.eddy.bookworm.models.mappers.ParcelableBookMapper;
import com.eddy.data.models.Book;
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

    LiveData<List<ParcelableBook>> getBooksLiveData(String encodedListName) {

        MutableLiveData<List<ParcelableBook>> booksLiveData = new MutableLiveData<>();

        Executor executor = Executors.newFixedThreadPool(3);
        executor.execute(() -> {
            BooksListRepository booksListRepository = new BooksListRepository();
            List<Book> books = booksListRepository.fetchBooks(encodedListName);
            List<ParcelableBook> parcelableBooks = new ParcelableBookMapper()
                    .transform(books);
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> booksLiveData.setValue(parcelableBooks));
        });

        return booksLiveData;
    }
}