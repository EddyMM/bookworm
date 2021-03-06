package com.eddy.data.rest;

import com.eddy.data.Constants;

import java.nio.charset.Charset;
import java.util.Objects;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okio.ByteString;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class BooksApi {

    private BooksApi() {}

    static {
        System.loadLibrary("native-lib");
    }

    public static native String getAPIKey();

    public static BooksApiService getInstance() {
        Retrofit retrofit;

        String apiKey = Objects.requireNonNull(
                Objects.requireNonNull(ByteString.decodeBase64(getAPIKey()))
                        .string(Charset.defaultCharset()));

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
