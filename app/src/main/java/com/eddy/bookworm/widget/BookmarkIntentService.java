package com.eddy.bookworm.widget;

import android.app.IntentService;
import android.content.Intent;

import com.eddy.bookworm.FirebaseDatabaseManager;
import com.eddy.bookworm.Utils;
import com.eddy.data.Constants;
import com.eddy.data.models.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BookmarkIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public BookmarkIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        FirebaseDatabaseManager firebaseDatabaseManager = FirebaseDatabaseManager.getInstance();

        DatabaseReference bookmarksDbReference = firebaseDatabaseManager.getDatabaseReference()
                .child(Constants.BOOKMARKS_DB_REF);

        bookmarksDbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long noOfBooks = dataSnapshot.getChildrenCount();
                if(noOfBooks > 0) {
                    int randomBook = (int) Math.floor(Math.random() * noOfBooks);

                    Book book = Utils.toList(dataSnapshot.getChildren()).get(randomBook);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}
