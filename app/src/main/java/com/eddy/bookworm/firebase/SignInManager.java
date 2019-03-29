package com.eddy.bookworm.firebase;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;

public class SignInManager {
    private static final SignInManager ourInstance = new SignInManager();
    private static FirebaseAuth fbAuthInstance;

    public static SignInManager getInstance() {
        fbAuthInstance = FirebaseAuth.getInstance();
        return ourInstance;
    }

    private SignInManager() {
    }

    public boolean userLoggedIn() {
        return fbAuthInstance.getCurrentUser() != null;
    }

    public String getCurrentUserName() {
        if (fbAuthInstance.getCurrentUser() != null) {
            return fbAuthInstance.getCurrentUser().getDisplayName();
        }
        return null;
    }

    public String getUserId(){
        return fbAuthInstance.getUid();
    }

    public void signOut(GoogleSignInClient googleSignInClient) {
        fbAuthInstance.signOut();
        googleSignInClient.signOut();
    }
}