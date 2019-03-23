package com.eddy.data;

import com.eddy.data.models.ListNamesResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BooksApiService {

    @GET("lists/names.json")
    Call<ListNamesResponse> listNames();

}
