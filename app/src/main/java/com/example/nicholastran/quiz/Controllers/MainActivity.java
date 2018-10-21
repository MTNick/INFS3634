package com.example.nicholastran.quiz.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nicholastran.quiz.Models.FirebaseHelper;
import com.example.nicholastran.quiz.R;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText name;
    EditText pass;
    FirebaseHelper fbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.editTextUsername);
        pass = findViewById(R.id.editTextPassword);
        fbHelper = new FirebaseHelper();

        // TEST
        //fbHelper.getQuestions();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (fbHelper.isAlreadySignedIn()) {
            // Already signed in
            System.out.println("SIGNED IN");
        }

        // TEST FUNCTIONS HERE
        //fbHelper.getAllAnswers();
    }

    public void logIn(View view) {
        String uName = name.getText().toString();
        String pWord = pass.getText().toString();
        FirebaseUser response = fbHelper.signInWithEmail(uName, pWord);
        if (response != null) {
            fbHelper.getQuestions();
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_LONG).show();
        }
    }

    public void signUp(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}
