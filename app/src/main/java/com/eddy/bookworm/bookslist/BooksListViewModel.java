package com.eddy.bookworm.bookslist;

import android.os.Handler;
import android.os.Looper;

import com.eddy.bookworm.models.ParcelableBook;
import com.eddy.bookworm.models.mappers.ParcelableBookMapper;
import com.eddy.domain.Book;
import com.eddy.domain.Library;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

class BooksListViewModel extends ViewModel {

    MutableLiveData<List<ParcelableBook>> booksLiveData = new MutableLiveData<>();

    BooksListViewModel(String encodedListName) {
        Executor executor = Executors.newFixedThreadPool(3);
        executor.execute(() -> {
            List<Book> books = Library.fetchBooks(encodedListName);
            List<ParcelableBook> parcelableBooks = new ParcelableBookMapper()
                    .transform(books);
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> booksLiveData.setValue(parcelableBooks));
        });
    }

}
