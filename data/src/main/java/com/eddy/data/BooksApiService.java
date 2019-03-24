package com.eddy.data;

import com.eddy.data.models.BooksResponse;
import com.eddy.data.models.ListNamesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BooksApiService {

    @GET("lists/names.json")
    Call<ListNamesResponse> listNames();

    @GET("lists/current/{list_name}")
    Call<BooksResponse> listBooks(@Path("list_name") String listName);

}
