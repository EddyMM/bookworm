package com.eddy.bookworm.bookslist;

import android.app.Application;

import com.eddy.bookworm.FirebaseDatabaseManager;
import com.eddy.data.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class BookmarksViewModel extends AndroidViewModel {
    private static final DatabaseReference JOURNAL_REF =
            FirebaseDatabaseManager.getInstance().getDatabaseReference()
            .child(Constants.BOOKMARKS_DB_REF);

    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(JOURNAL_REF);

    public BookmarksViewModel(@NonNull Application application) {
        super(application);
    }

    @NonNull
    LiveData<DataSnapshot> getDataSnapshotLiveData() {
        return liveData;
    }

}
