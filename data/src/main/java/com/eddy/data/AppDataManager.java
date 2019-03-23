package com.eddy.data;

import android.util.Log;

import com.eddy.data.models.ListName;
import com.eddy.data.models.ListNamesResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class AppDataManager implements DataManager {

    @Override
    public List<ListName> getListNames() {
        final List<ListName> listNames = new ArrayList<>();

        BooksApiService booksApiService = BooksApi.getInstance();
        Call<ListNamesResponse> listNameResponseCall = booksApiService.listNames();

        try {
            Response<ListNamesResponse> response = listNameResponseCall.execute();

            if(response.isSuccessful()) {
                ListNamesResponse listNamesResponse = response.body();

                if (listNamesResponse != null) {
                    List<ListName> books = listNamesResponse.getListNames();
                    listNames.addAll(books);
                    Log.i(AppDataManager.class.toString(), "LIST NAMES: "+ listNames.toString());
                } else {
                    Log.e(AppDataManager.class.toString(), "Null best seller response");
                }
            }
        } catch (IOException e) {
            Log.e(AppDataManager.class.toString(), e.getMessage());
        }

        return listNames;
    }
}
