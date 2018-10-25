package com.example.nicholastran.quiz.Controllers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.example.nicholastran.quiz.Models.FirebaseHelper;
import com.example.nicholastran.quiz.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kaopiz.kprogresshud.KProgressHUD;


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
        String pWord = password.getText().toString();
        String cPWord = confirmPassword.getText().toString();
        String uName = email.getText().toString();

        if (pWord.equals(cPWord) && (!pWord.isEmpty() && !uName.isEmpty())) {
            final KProgressHUD hud = KProgressHUD.create(SignUp.this).show();
            mAuth.createUserWithEmailAndPassword(uName, pWord).addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                hud.dismiss();
                                new SVProgressHUD(SignUp.this).showSuccessWithStatus("Welcome 😍");
                                System.out.println("SUCCESSFUL SIGN UP");
                                fbHelper.getQuestions();
                                Intent intent = new Intent(getApplicationContext(), Home.class);
                                startActivity(intent);
                            }
                            else {
                                hud.dismiss();
                                String fail = task.getException().getMessage();
                                new SVProgressHUD(SignUp.this).showErrorWithStatus(fail);
                            }
                        }
                    }
            );
        }
        else {
            new SVProgressHUD(this).showErrorWithStatus("Passwords must match and not be empty");
        }

    }
}
