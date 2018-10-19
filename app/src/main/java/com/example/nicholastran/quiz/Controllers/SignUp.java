package com.example.nicholastran.quiz.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nicholastran.quiz.Controllers.MainActivity;
import com.example.nicholastran.quiz.Models.FirebaseHelper;
import com.example.nicholastran.quiz.R;

public class SignUp extends AppCompatActivity {

    FirebaseHelper fbHelper;
    EditText email;
    EditText password;
    EditText confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fbHelper = new FirebaseHelper();
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        confirmPassword = findViewById(R.id.editTextConfirm);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (fbHelper.isAlreadySignedIn()) {
            System.out.println("Signed in");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void goToLogIn(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void signUp(View view) {
        if (password.equals(confirmPassword)) {
            String result = fbHelper.signUpWithEmail(email.getText().toString(), password.getText().toString());
            if (result.equals("success")) {
                System.out.println("Signed in");
                fbHelper.getQuestions();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Both password fields must match.", Toast.LENGTH_SHORT).show();
        }

    }
}
