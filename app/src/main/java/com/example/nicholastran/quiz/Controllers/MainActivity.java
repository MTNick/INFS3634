package com.example.nicholastran.quiz.Controllers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.example.nicholastran.quiz.Models.FirebaseHelper;
import com.example.nicholastran.quiz.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kaopiz.kprogresshud.KProgressHUD;

public class MainActivity extends AppCompatActivity {

    EditText name;
    EditText pass;
    FirebaseHelper fbHelper;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.editTextUsername);
        pass = findViewById(R.id.editTextPassword);
        fbHelper = new FirebaseHelper();
        mAuth = FirebaseAuth.getInstance();

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

        if (!uName.isEmpty() && !pWord.isEmpty()) {
            final KProgressHUD hud = KProgressHUD.create(MainActivity.this).show();
            mAuth.signInWithEmailAndPassword(uName, pWord).addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            System.out.println("CHECKING SUCCESS");
                            if (task.isSuccessful()) {
                                hud.dismiss();
                                new SVProgressHUD(MainActivity.this).showSuccessWithStatus("Welcome Back 😍");
                                System.out.println("SUCCESSFUL SIGN IN");
                                fbHelper.getQuestions();
                                Intent intent = new Intent(MainActivity.this, Home.class);
                                startActivity(intent);
                            }
                            else {
                                System.out.println("UNSUCCESSFUL");
                                hud.dismiss();
                                new SVProgressHUD(MainActivity.this).showErrorWithStatus(task.getException().getMessage().toString());
                            }
                        }
                    }
            );
        }
        else {
            new SVProgressHUD(this).showErrorWithStatus("Email and Password fields can't be empty");
        }
    }

    public void signUp(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}
