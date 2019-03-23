package com.eddy.bookworm;

import android.app.Application;

import com.eddy.data.AppDataManager;
import com.eddy.data.models.ListName;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ListNamesViewModel extends AndroidViewModel {

    LiveData<List<ListName>> listNamesLiveData;

    public ListNamesViewModel(@NonNull Application application) {
        super(application);

        AppDataManager appDataManager = new AppDataManager();
        listNamesLiveData = appDataManager.getListNames();
    }
}
