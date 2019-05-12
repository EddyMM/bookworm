package com.eddy.data.repository;

import com.eddy.data.BooksListDataSource;
import com.eddy.data.dao.BookCategoryDao;
import com.eddy.data.dao.BookDao;
import com.eddy.data.models.BookWithBuyLinks;
import com.eddy.data.models.entities.Book;
import com.eddy.data.models.entities.BookCategory;
import com.eddy.data.models.entities.Category;

import java.util.List;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;

public class BooksListRepository {

    private static BooksListRepository BOOKS_LIST_REPOSITORY;
    private static final Object LOCK = new Object();
    private BooksListDataSource booksListDataSource;
    private BookDao bookDao;

    public BooksListRepository(BooksListDataSource booksListDataSource, BookDao bookDao,
                               BookCategoryDao bookCategoryDao) {
        this.booksListDataSource = booksListDataSource;
        this.bookDao = bookDao;

        booksListDataSource.getBooksLiveData()
            .observeForever((categoryWithBooks) -> Executors.newSingleThreadExecutor()
                .execute(() -> {
                    List<Book> books = categoryWithBooks.getBooks();
                    String categoryCode = categoryWithBooks.getCategory().getCategoryCode();
                    long[] bookIds = bookDao.addBooks(books);

                    for (int n = 0; n < bookIds.length; n++) {
                        BookCategory bookCategory = new BookCategory(bookIds[n], categoryCode);
                        bookCategoryDao.addBookCategory(bookCategory);

                        bookDao.addBuyLinks(bookIds[n], books.get(n).getBuyLinks());
                    }
                }));
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

    private boolean fetchNeeded(Category category) {
        return (bookDao.countBooksByCategoryCode(category.getCategoryCode()) <= 0);
    }

    public LiveData<List<BookWithBuyLinks>> getBooksListLiveData(Category category) {
        initializeData(category);
        return bookDao.getBooksByCategoryCode(category.getCategoryCode());
    }

    private void initializeData(Category category) {
        Executors.newSingleThreadExecutor()
            .execute(() -> {
                if (fetchNeeded(category)) {
                    booksListDataSource.startSyncingBooks(category);
                }
            });
    }
}
