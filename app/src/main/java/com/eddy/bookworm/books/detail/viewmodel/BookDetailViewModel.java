package com.eddy.bookworm.books.detail.viewmodel;

import android.app.Application;

import com.eddy.data.models.entities.Book;
import com.eddy.data.repository.BookmarksRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class BookDetailViewModel extends ViewModel {

    @NonNull private final Application application;
    private final Book book;
    private final LiveData<Book> bookmark;
    private BookmarksRepository bookmarksRepository;

    BookDetailViewModel(@NonNull Application application, Book book) {
        bookmarksRepository = BookmarksRepository.getInstance(application);

        this.application = application;
        this.book = book;
        bookmark = bookmarksRepository.getBookmark(book);
    }

    public LiveData<Book> getBookmark() {
        return bookmark;
    }

    public void addBookmark(Book book) {
        bookmarksRepository.addBook(book);
    }

    public void removeBookmark(Book book) {
        bookmarksRepository.removeBook(book);
    }
}
