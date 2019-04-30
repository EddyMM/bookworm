package com.eddy.data.rest;

import com.eddy.data.Constants;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BooksApi {

    private BooksApi() {}

    public static BooksApiService getInstance(String apiKey) {
        Retrofit retrofit;

        // Build a client with an interceptor to add the API key
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request initialRequest = chain.request();
                    HttpUrl initialHttpUrl = initialRequest.url();

                    HttpUrl modifiedHttpUrl = initialHttpUrl.newBuilder()
                            .addQueryParameter(Constants.API_KEY_REQUEST_KEY,
                                    apiKey)
                            .build();
                    Request modifiedRequest = initialRequest.newBuilder()
                            .url(modifiedHttpUrl)
                            .build();

                    return chain.proceed(modifiedRequest);
                })
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.NEW_YORK_TIMES_API_BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        return retrofit.create(BooksApiService.class);
    }
}
