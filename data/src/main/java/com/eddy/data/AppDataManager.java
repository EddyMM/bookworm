package com.eddy.data;

import com.eddy.data.models.Book;
import com.eddy.data.models.BooksResponse;
import com.eddy.data.models.BooksResults;
import com.eddy.data.models.ListName;
import com.eddy.data.models.ListNamesResponse;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

public class AppDataManager implements DataManager {

    @Override
    public List<ListName> getListNames() {
        BooksApiService booksApiService = BooksApi.getInstance();
        Call<ListNamesResponse> listNameResponseCall = booksApiService.listNames();

        List<ListName> listNames = null;

        try {
            Response<ListNamesResponse>  response = listNameResponseCall.execute();
            ListNamesResponse listNamesResponse = response.body();
            listNames = Objects.requireNonNull(listNamesResponse).getListNames();
        } catch (IOException e) {
            Timber.e(e);
        }

        return listNames;
    }

    @Override
    public List<Book> getBooks(String encodedListName) {
        BooksApiService booksApiService = BooksApi.getInstance();
        Call<BooksResponse> booksResponseCall = booksApiService.listBooks(encodedListName);

        List<Book> books = null;

        try {
            Response<BooksResponse> response = booksResponseCall.execute();
            BooksResponse booksResponse = response.body();
            BooksResults booksResults = Objects.requireNonNull(booksResponse)
                    .getBooksResults();

            books =  booksResults.getBooks();
        } catch (IOException e) {
//            Timber.e(e);
        }

        return books;
    }
}
