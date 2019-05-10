package com.eddy.data.repository;

import com.eddy.data.models.entities.Book;
import com.eddy.data.models.rest.BooksResponse;
import com.eddy.data.models.rest.BooksResults;
import com.eddy.data.repository.interfaces.IBooksListRepository;
import com.eddy.data.rest.BooksApiService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

public class BooksListRepository implements IBooksListRepository {

    private BooksApiService booksApiService;

    public BooksListRepository(BooksApiService booksApiService) {
        this.booksApiService = booksApiService;
    }

    @Override
    public List<Book> fetchBooks(String categoryCode) {
        Call<BooksResponse> booksResponseCall = booksApiService.listBooks(categoryCode);

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
}
