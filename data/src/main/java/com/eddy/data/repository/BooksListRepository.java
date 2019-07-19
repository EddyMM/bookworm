package com.eddy.data.repository;

import com.eddy.data.datasources.BooksListDataSource;
import com.eddy.data.dao.BookCategoryDao;
import com.eddy.data.dao.BookDao;
import com.eddy.data.models.BookWithBuyLinks;
import com.eddy.data.models.CategoryWithBooks;
import com.eddy.data.models.entities.Book;
import com.eddy.data.models.entities.BookCategory;
import com.eddy.data.models.entities.Category;

import java.util.List;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

public class BooksListRepository {

    private static BooksListRepository BOOKS_LIST_REPOSITORY;
    private static final Object LOCK = new Object();
    private BooksListDataSource booksListDataSource;
    private BookDao bookDao;

    public BooksListRepository(BooksListDataSource booksListDataSource, BookDao bookDao,
                               BookCategoryDao bookCategoryDao) {
        this.booksListDataSource = booksListDataSource;
        this.bookDao = bookDao;

        Observer<CategoryWithBooks> syncObserver = categoryWithBooks -> Executors.newSingleThreadExecutor()
                .execute(() -> {
                    List<Book> books = categoryWithBooks.getBooks();
                    String categoryCode = categoryWithBooks.getCategory().getCategoryCode();

                    // Remove old list books under this category
                    bookCategoryDao.deleteBooksByCategory(categoryCode);

                    // Add the fresh list of books
                    long[] bookIds = bookDao.addBooks(books);

                    for (int n = 0; n < bookIds.length; n++) {
                        BookCategory bookCategory = new BookCategory(bookIds[n], categoryCode);
                        bookCategoryDao.addBookCategory(bookCategory);

                        bookDao.addBuyLinks(bookIds[n], books.get(n).getBuyLinks());
                    }
                });

        booksListDataSource.getBooksLiveData()
            .observeForever(syncObserver);
    }

    public synchronized static BooksListRepository getInstance(
            BooksListDataSource booksListDataSource, BookDao bookWithBuyLinksDao,
            BookCategoryDao bookCategoryDao) {
        if (BOOKS_LIST_REPOSITORY == null) {
            synchronized (LOCK) {
                BOOKS_LIST_REPOSITORY = new BooksListRepository(
                        booksListDataSource, bookWithBuyLinksDao, bookCategoryDao);
            }
        }

        return BOOKS_LIST_REPOSITORY;
    }

    public LiveData<Boolean> getSyncInProgress() {
        return booksListDataSource.getSyncInProgressLiveData();
    }

    private boolean fetchNeeded(Category category) {
        return (bookDao.countBooksByCategoryCode(category.getCategoryCode()) <= 0);
    }

    public LiveData<List<BookWithBuyLinks>> getBooksListLiveData(Category category) {
        Executors.newSingleThreadExecutor()
            .execute(() -> {
                if (fetchNeeded(category)) {
                    initializeData(category);
                } else {
                    booksListDataSource.setSyncInProgressLiveData(false);
                }
        });

        return bookDao.getBooksByCategoryCode(category.getCategoryCode());
    }

    private void initializeData(Category category) {
        syncBooks(category);
    }

    public void updateBook(Book book) {
        Executors.newSingleThreadExecutor().execute(() -> bookDao.updateBook(book));
    }

    public LiveData<List<BookWithBuyLinks>> getBookmarkedBooks() {
        return bookDao.getBookmarkedBooks();
    }

    public void syncBooks(Category category) {
        booksListDataSource.startSyncingBooks(category);
    }

    public LiveData<Throwable> getBooksListError() {
        return booksListDataSource.getBooksListSyncError();
    }
}
