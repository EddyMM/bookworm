package com.eddy.data.rest;

import com.eddy.data.models.rest.BooksResponse;
import com.eddy.data.models.rest.CategoriesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BooksApiService {

    @GET("lists/names.json")
    Call<CategoriesResponse> categories();

    @GET("lists/current/{list_name}")
    Call<BooksResponse> listBooks(@Path("list_name") String category);

}
