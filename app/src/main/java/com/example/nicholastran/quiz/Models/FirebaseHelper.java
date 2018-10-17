package com.example.nicholastran.quiz.Models;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseHelper {

    private FirebaseAuth mAuth;
    private String response;

    public FirebaseHelper() {
        mAuth = FirebaseAuth.getInstance();
    }

    // Create a new user using email
    public String signUpWithEmail(String username, String password) {

        if (!username.isEmpty() && !password.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                response = "success";
                            }
                            else {
                                response = task.getException().getMessage();
                            }
                        }
                    }
            );
        }
        else {
            response = "Please enter a valid username and password";
        }

        return response;
    }

    // Check if a user is already signed in
    public Boolean isAlreadySignedIn() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            return true;
        }
        else {
            return false;
        }
    }
}
