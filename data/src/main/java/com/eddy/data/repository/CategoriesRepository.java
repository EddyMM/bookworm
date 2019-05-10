package com.eddy.data.repository;

import com.eddy.data.models.entities.Category;
import com.eddy.data.models.rest.CategoriesResponse;
import com.eddy.data.repository.interfaces.ICategoriesRepository;
import com.eddy.data.rest.BooksApiService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

public class CategoriesRepository implements ICategoriesRepository {

    private BooksApiService booksApiService;

    public CategoriesRepository(BooksApiService booksApiService) {
        this.booksApiService = booksApiService;
    }

    @Override
    public List<Category> fetchCategories() {
        return fetchOnline();
    }

    private List<Category> fetchOnline() {
        Call<CategoriesResponse> listNameResponseCall = booksApiService.categories();
        List<Category> categories = null;

        try {
            Response<CategoriesResponse> response = listNameResponseCall.execute();
            CategoriesResponse categoriesResponse = response.body();
            categories = Objects.requireNonNull(categoriesResponse).getCategories();
        } catch (IOException e) {
            Timber.e(e);
        }

        return categories;
    }
}
