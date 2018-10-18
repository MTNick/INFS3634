package com.example.nicholastran.quiz.Models;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;

public class FirebaseHelper {

    private FirebaseAuth mAuth;
    private String response;
    FirebaseUser user;
    private DatabaseReference dbRef;

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
                                System.out.println("SUCCESSFUL SIGN UP");
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

    // Sign in with email
    public FirebaseUser signInWithEmail(String username, String password) {
        if (!username.isEmpty() && !password.isEmpty()) {
            mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                user = mAuth.getCurrentUser();
                                System.out.println("SUCCESSFUL SIGN IN");
                            }
                            else {
                                user = null;
                            }
                        }
                    }
            );
        }
        else {
            user = null;
        }

        return user;
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

    // Log user out
    public void logOut() {
        mAuth.signOut();
    }

    // Get questions from Firebase and add it to the questions arraylist
    public void getQuestions() {
        dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get questions
                System.out.println("VALUE: " + dataSnapshot.getValue().toString());
                // Add to the questions arraylist
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // Check whether the answer is correct or not
    public Boolean isAnswerCorrect(int qNumber) {
        // Check questions against questions.get(qNumber).answer
        return false;
    }
}