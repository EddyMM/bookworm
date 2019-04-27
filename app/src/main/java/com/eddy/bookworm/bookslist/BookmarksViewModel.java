package com.eddy.bookworm.bookslist;

import android.app.Application;

import com.eddy.bookworm.Utils;
import com.eddy.bookworm.firebase.FirebaseDatabaseManager;
import com.eddy.bookworm.models.ParcelableBook;
import com.eddy.bookworm.models.mappers.ParcelableBookMapper;
import com.eddy.data.Constants;
import com.eddy.data.models.BookEntity;
import com.eddy.domain.Book;
import com.eddy.domain.mappers.BookMapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class BookmarksViewModel extends AndroidViewModel {
    private static final DatabaseReference JOURNAL_REF =
            FirebaseDatabaseManager.getInstance().getDatabaseReference()
            .child(Constants.BOOKMARKS_DB_REF);

    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(JOURNAL_REF);

    public BookmarksViewModel(@NonNull Application application) {
        super(application);
    }

    @NonNull
    public LiveData<List<ParcelableBook>> getDataSnapshotLiveData(LifecycleOwner lifecycleOwner) {

        MutableLiveData<List<ParcelableBook>> parcelableBookLiveData = new MutableLiveData<>();

        liveData.observe(lifecycleOwner, dataSnapshot -> {
            List<BookEntity> bookEntities = Utils.toList(dataSnapshot.getChildren());

            if (bookEntities != null) {
                List<Book> books = new BookMapper()
                        .transform(bookEntities);
                List<ParcelableBook> parcelableBooks = new ParcelableBookMapper()
                        .transform(books);
                parcelableBookLiveData.setValue(parcelableBooks);
            }
        });

        return parcelableBookLiveData;
    }

}
