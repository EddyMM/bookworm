package com.eddy.data;

import com.eddy.data.models.Book;
import com.eddy.data.models.BooksResponse;
import com.eddy.data.models.ListName;
import com.eddy.data.models.ListNamesResponse;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

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
                    Timber.e("Null best seller response");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ListNamesResponse> call, @NonNull Throwable t) {
                Timber.e(t);
            }
        });

        return listNamesLiveData;
    }

    @Override
    public LiveData<List<Book>> getBooks(String encodedListName) {
        final MutableLiveData<List<Book>> booksLiveData = new MutableLiveData<>();

        BooksApiService booksApiService = BooksApi.getInstance();
        Call<BooksResponse> booksResponseCall = booksApiService.listBooks(encodedListName);
        booksResponseCall.enqueue(new Callback<BooksResponse>() {
            @Override
            public void onResponse(@NonNull Call<BooksResponse> call, @NonNull Response<BooksResponse> response) {
                BooksResponse booksResponse = response.body();

                if (booksResponse != null) {
                    List<Book> books = booksResponse.getBooksResults().getBooks();
                    booksLiveData.setValue(books);
                } else {
                    Timber.e("Null best seller response");
                }
            }

            @Override
            public void onFailure(@NonNull Call<BooksResponse> call, @NonNull Throwable t) {
                Timber.e(t);
            }
        });

        return booksLiveData;
    }
}
