package com.eddy.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.eddy.data.models.Book;
import com.eddy.data.models.BooksResponse;
import com.eddy.data.models.BooksResults;
import com.eddy.data.models.ListName;
import com.eddy.data.models.ListNamesResponse;

import java.io.IOException;
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

    public List<Book> getCategoriesSync(String category) {
        BooksApiService booksApiService = BooksApi.getInstance();
        Call<BooksResponse> booksResponseCall = booksApiService.listBooks(category);

        List<Book> bookList = null;

        try {
            Response<BooksResponse> booksResponse = booksResponseCall.execute();
            BooksResponse booksBody = booksResponse.body();
            if (booksBody != null) {
                BooksResults booksResults = booksBody.getBooksResults();
                if (booksResults != null) {
                    bookList = booksResults.getBooks();
                }
            }
        } catch (IOException e) {
            Timber.e(e);
        }

        return bookList;
    }

    public String getPreferredCategoryCode(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Constants.WIDGET_CATEGORY_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constants.PREFERRED_CATEGORY, "");
    }

    public void setPreferredCategoryCode(Context context, String categoryCode) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Constants.WIDGET_CATEGORY_PREFERENCE, Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            sharedPreferences.edit().putString(Constants.PREFERRED_CATEGORY, categoryCode)
                    .apply();
        }
    }

    public void removePreferredCategoryCode(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Constants.WIDGET_CATEGORY_PREFERENCE, Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            sharedPreferences.edit().remove(Constants.PREFERRED_CATEGORY)
                    .apply();
        }
    }
}
