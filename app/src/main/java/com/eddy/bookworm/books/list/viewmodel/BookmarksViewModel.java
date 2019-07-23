package com.eddy.bookworm.books.list.viewmodel;

import android.app.Application;

import com.eddy.bookworm.base.BookwormApplication;
import com.eddy.data.BookwormDatabase;
import com.eddy.data.dao.BookDao;
import com.eddy.data.models.entities.Book;
import com.eddy.data.repository.BookmarksRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class BookmarksViewModel extends AndroidViewModel {

    private LiveData<List<Book>> bookmarkedBooks;

    public BookmarksViewModel(@NonNull Application application) {
        super(application);

        BookmarksRepository bookmarksRepository = BookmarksRepository.getInstance(
                application);
        bookmarkedBooks = bookmarksRepository.getBookmarkedBooks();
    }

    public LiveData<List<Book>> getBookmarkedBooksLiveData() {
        return bookmarkedBooks;
    }

    public void refresh() {
        // TODO: Refresh
    }
}
