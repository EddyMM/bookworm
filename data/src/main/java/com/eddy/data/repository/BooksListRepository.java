package com.eddy.data.repository;

import android.content.Context;

import com.eddy.data.BooksListDataSource;
import com.eddy.data.dao.BookDao;
import com.eddy.data.models.entities.Book;
import com.eddy.data.models.rest.BooksResponse;
import com.eddy.data.models.rest.BooksResults;
import com.eddy.data.repository.interfaces.IBooksListRepository;
import com.eddy.data.rest.BooksApi;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

public class BooksListRepository implements IBooksListRepository {

    private static BooksListRepository BOOKS_LIST_REPOSITORY;
    private static final Object LOCK = new Object();
    private BooksListDataSource booksListDataSource;
    private BookDao bookDao;

    public BooksListRepository(BooksListDataSource booksListDataSource, BookDao bookDao) {
        this.booksListDataSource = booksListDataSource;
        this.bookDao = bookDao;
    }

    public synchronized static BooksListRepository getInstance(
            Context context, BooksListDataSource booksListDataSource, BookDao bookDao) {
        if (BOOKS_LIST_REPOSITORY == null) {
            synchronized (LOCK) {
                BOOKS_LIST_REPOSITORY = new BooksListRepository(booksListDataSource, bookDao);
            }
        }

        return BOOKS_LIST_REPOSITORY;
    }

    @Override
    public List<Book> fetchBooks(String categoryCode) {

        Call<BooksResponse> booksResponseCall = BooksApi.getInstance()
                .listBooks(categoryCode);

        List<Book> books = null;

        try {
            Response<BooksResponse> response = booksResponseCall.execute();
            BooksResponse booksResponse = response.body();
            BooksResults booksResults = Objects.requireNonNull(booksResponse)
                    .getBooksResults();

            books =  booksResults.getBooks();
        } catch (IOException e) {
            Timber.e(e);
        }

        return books;
    }

    private boolean fetchNeeded(String categoryCode) {
        return bookDao.countBooksByCategoryCode(categoryCode);
    }
}
