package com.eddy.data;

import android.util.Log;

import com.eddy.data.models.ListName;
import com.eddy.data.models.ListNamesResponse;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppDataManager implements DataManager {

    @Override
    public LiveData<List<ListName>> getListNames() {
        final MutableLiveData<List<ListName>> listNamesLiveData = new MutableLiveData<>();

        BooksApiService booksApiService = BooksApi.getInstance();
        Call<ListNamesResponse> listNameResponseCall = booksApiService.listNames();
        listNameResponseCall.enqueue(new Callback<ListNamesResponse>() {
            @Override
            public void onResponse(@NonNull Call<ListNamesResponse> call, @NonNull Response<ListNamesResponse> response) {
                ListNamesResponse listNamesResponse = response.body();

                if (listNamesResponse != null) {
                    List<ListName> books = listNamesResponse.getListNames();
                    listNamesLiveData.setValue(books);
                } else {
                    Log.e(AppDataManager.class.toString(), "Null best seller response");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ListNamesResponse> call, @NonNull Throwable t) {
                Log.e(AppDataManager.class.toString(), t.getMessage());
            }
        });

        return listNamesLiveData;
    }
}
