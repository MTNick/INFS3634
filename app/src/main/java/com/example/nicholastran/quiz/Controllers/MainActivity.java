package com.example.nicholastran.quiz.Controllers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nicholastran.quiz.Home;
import com.example.nicholastran.quiz.Models.FirebaseHelper;
import com.example.nicholastran.quiz.R;
import com.example.nicholastran.quiz.SignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText name;
    EditText pass;
    FirebaseHelper fbHelper;

    public static final String EXTRA_MESSAGE = "com.example.quiz" +
            ".MESSAGE";

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

    }

    public void logIn(View view) {

        Intent intent = new Intent(this, Home.class);
        EditText editText = findViewById(R.id.editTextUsername);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void signUp(View view) {

        String uName = name.getText().toString();
        String pWord = pass.getText().toString();
        String response = fbHelper.signUpWithEmail(uName, pWord);
        if (response.equals("success")) {
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, response, Toast.LENGTH_LONG);
        }
    }
}
