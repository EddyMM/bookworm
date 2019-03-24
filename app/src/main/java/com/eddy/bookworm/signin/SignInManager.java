package com.eddy.bookworm.signin;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;

public class SignInManager {
    private static final SignInManager ourInstance = new SignInManager();

    public static SignInManager getInstance() {
        return ourInstance;
    }

    private SignInManager() {
    }

    public boolean userLoggedIn() {
        FirebaseAuth fbAuthInstance = FirebaseAuth.getInstance();
        return fbAuthInstance.getCurrentUser() != null;
    }

    public String getCurrentUserName() {
        FirebaseAuth fbAuthInstance = FirebaseAuth.getInstance();
        if (fbAuthInstance.getCurrentUser() != null) {
            return fbAuthInstance.getCurrentUser().getDisplayName();
        }
        return null;
    }

    public void signOut(GoogleSignInClient googleSignInClient) {
        FirebaseAuth fbAuthInstance = FirebaseAuth.getInstance();
        fbAuthInstance.signOut();
        googleSignInClient.signOut();
    }
}
