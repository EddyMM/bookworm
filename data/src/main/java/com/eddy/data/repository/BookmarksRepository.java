package com.eddy.data.repository;

import android.content.Context;

import com.eddy.data.BookwormDatabase;
import com.eddy.data.dao.BookDao;
import com.eddy.data.models.entities.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;


public class BookmarksRepository {
    private static BookmarksRepository BOOKMARKS_REPOSITORY;
    private static final Object LOCK = new Object();

    private BookDao bookDao;
    private LiveData<List<Book>> bookmarkedBooks;


    private BookmarksRepository(Context context) {
        bookDao = BookwormDatabase.getInstance(context).booksDao();
        bookmarkedBooks = bookDao.getBooks();
    }

    public static BookmarksRepository getInstance(Context context) {
        if (BOOKMARKS_REPOSITORY == null) {
            synchronized (LOCK) {
                BOOKMARKS_REPOSITORY = new BookmarksRepository(context);
            }
        }

        return BOOKMARKS_REPOSITORY;
    }

    public void addBook(Book book) {
        Executors.newSingleThreadExecutor().execute(() -> {
            bookDao.addBook(book);
        });
    }

    public void removeBook(Book book) {
        Executors.newSingleThreadExecutor().execute(() -> {
            bookDao.deleteBook(book);
        });
    }

    public LiveData<List<Book>> getBookmarkedBooks() {
        return bookmarkedBooks;
    }

    public LiveData<Book> getBookmark(Book book) {
        return bookDao.getBook(book.getTitle(), book.getAuthor());
    }
}
