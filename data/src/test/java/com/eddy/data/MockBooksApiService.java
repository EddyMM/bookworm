package com.eddy.data;

import com.eddy.data.models.entities.Category;
import com.eddy.data.models.rest.BooksResponse;
import com.eddy.data.models.rest.BooksResults;
import com.eddy.data.models.rest.CategoriesResponse;
import com.eddy.data.rest.BooksApiService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MockBooksApiService implements BooksApiService {

    @Override
    public Call<CategoriesResponse> categories() {

        return new Call<CategoriesResponse>() {
            @Override
            public Response<CategoriesResponse> execute() {
                CategoriesResponse categoriesResponse = new CategoriesResponse(
                        generateTestCategories());

                return Response.success(categoriesResponse);
            }

            @Override
            public void enqueue(Callback<CategoriesResponse> callback) { }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<CategoriesResponse> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
    }

    public List<Category> generateTestCategories() {
        List<Category> categories = null;

        try {
            String categoriesJson = TestUtils.readFile(
                    "src/test/java/com/eddy/data/mockdata/MockCategoriesData.json");

            Gson gson = new Gson();
            Type categoriesListType = new TypeToken<List<Category>>(){}.getType();
            categories = gson.fromJson(categoriesJson, categoriesListType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return categories;
    }

    public BooksResults generateTestBookResults() {
        BooksResults booksResults = null;

        try {
            String categoriesJson = TestUtils.readFile(
                    "src/test/java/com/eddy/data/mockdata/MockBooksData.json");

            Gson gson = new Gson();
            booksResults = gson.fromJson(categoriesJson, BooksResults.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return booksResults;
    }

    @Override
    public Call<BooksResponse> listBooks(String category) {
        BooksResults booksResults = generateTestBookResults();
        System.out.println(booksResults);

        return new Call<BooksResponse>() {

            @Override
            public Response<BooksResponse> execute() throws IOException {
                BooksResponse categoriesResponse = new BooksResponse(booksResults);

                return Response.success(categoriesResponse);
            }

            @Override
            public void enqueue(Callback<BooksResponse> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<BooksResponse> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
    }
}
