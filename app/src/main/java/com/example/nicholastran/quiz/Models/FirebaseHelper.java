package com.example.nicholastran.quiz.Models;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    double correct;
    double totalQuestions;

    // Constructor
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
        // Refresh each time this function runs, so clear arraylist
        Questions.questions.clear();

        dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                // Get questions
                for (DataSnapshot snapshot : dataSnapshot.child("questions").getChildren()) {
                    String question =  snapshot.child("question").getValue().toString();
                    String answer = snapshot.child("answer").getValue().toString();

                    // Make it of Questions class
                    Questions q = new Questions(question, answer);

                    // Add to the questions arraylist
                    Questions.questions.add(i, q);

                    i++;

                    if (Questions.questions.size() > 0) {
                        System.out.println("ADDED QUESTIONS");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("ERROR: " + databaseError.getMessage());
            }
        });
    }

    // Check whether the answer is correct or not
    public Boolean isAnswerCorrect(int qNumber, String answer) {
        if (answer.equals(Questions.questions.get(qNumber).getAnswer())) {
            addSuccess(String.valueOf(qNumber));
            return true;
        }
        else {
            System.out.println(answer + " is incorrect\n" + Questions.questions.get(qNumber).getAnswer() +
                    " is correct!");
            return false;
        }
    }

    // Get progress (do when get to home activity) and add to userProgress and currentProgress
    public void getProgress() {
        String uid = mAuth.getCurrentUser().getUid().toString();
        dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.child("progress").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                correct = 0;
                int i = 0;
                while (i < dataSnapshot.getChildrenCount()) {
                    Questions.userProgress.add(i, true);
                    correct++;
                    i++;
                }

                // Get total number of questions
                dbRef.child("totalQuestions").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        totalQuestions = Integer.valueOf(dataSnapshot.getValue().toString());
                        System.out.println("TQ: " + totalQuestions);
                        System.out.println("CORRECT: " + correct);
                        Questions.currentProgress = (correct / totalQuestions) * 100;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                while (i < totalQuestions) {
                    Questions.userProgress.add(i, false);
                    i++;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("ERROR: " + databaseError.getMessage());
            }
        });
    }

    /*
    // Check overall progress
    public int checkProgress() {
        return Questions.currentProgress;
    }
    */

    // Check if user got current question right
    public Boolean gotQuestionCorrect(int qNumber) {
        if (Questions.userProgress.get(qNumber)) {
            // Got correct
            return true;
        }
        else {
            // Haven't got correct
            return false;
        }
    }

    // Store progress in Firebase
    private void addSuccess(String qNumber) {
        dbRef = FirebaseDatabase.getInstance().getReference();
        String uid = mAuth.getUid();
        dbRef.child("progress").child(uid).child(qNumber).setValue("correct");
    }

    // Get all potential answers for all questions and add to allAnswers
    public void getAllAnswers() {
        Answers.allAnswers.clear();
        dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot question : dataSnapshot.child("answerOptions").getChildren()) {
                    Answers answer = new Answers(question.child("a").getValue().toString(),
                            question.child("b").getValue().toString(),
                            question.child("c").getValue().toString(),
                            question.child("d").getValue().toString());
                    Answers.allAnswers.add(i, answer);
                    System.out.println("GOT ANSWERS");
                    i++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}