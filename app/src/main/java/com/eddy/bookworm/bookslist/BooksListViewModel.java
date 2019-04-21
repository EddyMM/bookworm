package com.eddy.bookworm.bookslist;

import android.os.Handler;
import android.os.Looper;

import com.eddy.data.models.Book;
import com.eddy.domain.Library;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

class BooksListViewModel extends ViewModel {

    MutableLiveData<List<Book>> booksLiveData = new MutableLiveData<>();

    BooksListViewModel(String encodedListName) {
        Executor executor = Executors.newFixedThreadPool(3);
        executor.execute(() -> {
            List<Book> books = Library.fetchBooks(encodedListName);
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> booksLiveData.setValue(books));
        });
    }

}
