package com.eddy.bookworm.firebase;

import com.eddy.data.Constants;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDatabaseManager {
    private static final FirebaseDatabaseManager ourInstance = new FirebaseDatabaseManager();

    public static FirebaseDatabaseManager getInstance() {
        return ourInstance;
    }

    private FirebaseDatabaseManager() { }

    public DatabaseReference getDatabaseReference() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        return firebaseDatabase.getReference()
                .child(Constants.DB_CHILD_USERS)
                .child(SignInManager.getInstance().getUserId())
                .child(Constants.LIBRARY_DB_REF);
    }
}
