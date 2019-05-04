package com.eddy.data;

import com.eddy.data.models.entities.Book;
import com.eddy.data.models.rest.BooksResponse;
import com.eddy.data.models.rest.BooksResults;
import com.eddy.data.models.entities.Category;
import com.eddy.data.models.rest.CategoriesResponse;
import com.eddy.data.rest.BooksApi;
import com.eddy.data.rest.BooksApiService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

public class DataManager implements IDataManager {

    @Override
    public List<Category> getListNames() {
        BooksApiService booksApiService = BooksApi.getInstance();
        Call<CategoriesResponse> listNameResponseCall = booksApiService.listNames();

        List<Category> categories = null;

        try {
            Response<CategoriesResponse>  response = listNameResponseCall.execute();
            CategoriesResponse categoriesResponse = response.body();
            categories = Objects.requireNonNull(categoriesResponse).getCategories();
        } catch (IOException e) {
//            Timber.e(e);
        }

        return categories;
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
