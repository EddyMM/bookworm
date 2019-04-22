package com.eddy.bookworm.listnames;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.eddy.data.models.ListName;
import com.eddy.domain.Library;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


public class ListNamesViewModel extends AndroidViewModel {

    public ListNamesViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<ListName>> getListNamesLiveData () {
        MutableLiveData<List<ListName>> listNamesLiveData = new MutableLiveData<>();

        Executor executor = Executors.newFixedThreadPool(3);
        executor.execute(() -> {
            List<ListName> listNames = Library.fetchCategories();
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> listNamesLiveData.setValue(listNames));
        });

        return listNamesLiveData;
    }

}
