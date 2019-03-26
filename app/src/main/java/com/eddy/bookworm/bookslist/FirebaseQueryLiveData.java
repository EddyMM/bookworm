package com.eddy.bookworm.bookslist;

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
    private final EntriesEventListener listener = new EntriesEventListener();

    public FirebaseQueryLiveData(Query query) {
        this.query = query;
    }

    FirebaseQueryLiveData(DatabaseReference ref) {
        this.query = ref.orderByChild("lastUpdatedOn");
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

    private class EntriesEventListener implements ValueEventListener {
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

