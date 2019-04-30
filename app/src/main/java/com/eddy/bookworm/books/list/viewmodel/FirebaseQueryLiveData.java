package com.eddy.bookworm.books.list.viewmodel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import timber.log.Timber;

public class FirebaseQueryLiveData extends LiveData<DataSnapshot> {

    private final Query query;
    private final BooksEventListener listener = new BooksEventListener();

    public FirebaseQueryLiveData(DatabaseReference ref) {
        this.query = ref.orderByKey();
    }

    @Override
    protected void onActive() {
        Timber.d("onActive");
        query.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Timber.d("onInactive");
        query.removeEventListener(listener);
    }

    private class BooksEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(dataSnapshot);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Timber.e(databaseError.toException(), "Can't listen to query %s", query);
        }
    }
}

