package com.example.nicholastran.quiz.Controllers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nicholastran.quiz.Controllers.MainActivity;
import com.example.nicholastran.quiz.Models.FirebaseHelper;
import com.example.nicholastran.quiz.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class SignUp extends AppCompatActivity {

    FirebaseHelper fbHelper;
    EditText email;
    EditText password;
    EditText confirmPassword;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fbHelper = new FirebaseHelper();
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        confirmPassword = findViewById(R.id.editTextConfirm);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (fbHelper.isAlreadySignedIn()) {
            System.out.println("Signed in");
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }
    }

    public void goToLogIn(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void signUp(View view) {
        System.out.println("PASSWORD: " + password.getText().toString());
        System.out.println("CONF PASSWORD: " + confirmPassword.getText().toString());
        String pWord = password.getText().toString();
        String cPWord = confirmPassword.getText().toString();
        String uName = email.getText().toString();

        if (pWord.equals(cPWord)) {
            mAuth.createUserWithEmailAndPassword(uName, pWord).addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                System.out.println("SUCCESSFUL SIGN UP");
                                System.out.println("Signed in");
                                fbHelper.getQuestions();
                                Intent intent = new Intent(getApplicationContext(), Home.class);
                                startActivity(intent);
                            }
                            else {
                                String fail = task.getException().getMessage();
                                Toast.makeText(SignUp.this, fail, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
        }
        else {
            Toast.makeText(this, "Both password fields must match.", Toast.LENGTH_SHORT).show();
        }

    }
}
