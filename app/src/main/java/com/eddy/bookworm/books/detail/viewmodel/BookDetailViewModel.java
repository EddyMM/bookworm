package com.eddy.bookworm.books.detail.viewmodel;

import android.app.Application;

import com.eddy.data.models.entities.Book;
import com.eddy.data.repository.BookmarksRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BookDetailViewModel extends ViewModel {

    private final MutableLiveData<Boolean> bookmarkState;
    @NonNull private final Application application;
    private final Book book;
    private BookmarksRepository bookmarksRepository;

    BookDetailViewModel(@NonNull Application application, Book book) {
        this.application = application;
        this.book = book;
        bookmarkState = new MutableLiveData<>();

        loadBookmarkState();
    }

    public MutableLiveData<Boolean> getBookmarkState() {
        return bookmarkState;
    }

    private void loadBookmarkState() {
        bookmarksRepository = BookmarksRepository.getInstance();
        refreshBookmarkState();
    }

    public void addBookmark(Book book) {
        bookmarksRepository.addBook(book);
        refreshBookmarkState();
    }

    public void removeBookmark(Book book) {
        bookmarksRepository.removeBook(book);
        refreshBookmarkState();
    }

    private void refreshBookmarkState() {
        boolean bookmarkState = bookmarksRepository.isBookmarked(book);
        this.bookmarkState.setValue(bookmarkState);
    }
}
