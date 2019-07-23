package com.eddy.data.repository;

import com.eddy.data.models.entities.Book;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class BookmarksRepository {
    private static BookmarksRepository BOOKMARKS_REPOSITORY;
    private static final Object LOCK = new Object();

    private List<Book> books = new ArrayList<>();
    private MutableLiveData<List<Book>> bookmarkedBooks = new MutableLiveData<>();

    private BookmarksRepository() { }

    public static BookmarksRepository getInstance() {
        if (BOOKMARKS_REPOSITORY == null) {
            synchronized (LOCK) {
                BOOKMARKS_REPOSITORY = new BookmarksRepository();
            }
        }

        return BOOKMARKS_REPOSITORY;
    }

    public void addBook(Book book) {
        books.add(book);
        bookmarkedBooks.postValue(books);
    }

    public void removeBook(Book book) {
        books.remove(book);
        bookmarkedBooks.postValue(books);
    }

    public boolean isBookmarked(Book book) {
        return books.contains(book);
    }

    public MutableLiveData<List<Book>> getBookmarkedBooks() {
        return bookmarkedBooks;
    }
}
