package com.example.nicholastran.quiz.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.nicholastran.quiz.Models.FirebaseHelper;
import com.example.nicholastran.quiz.Models.Questions;
import com.example.nicholastran.quiz.R;

public class Home extends AppCompatActivity {

    FirebaseHelper fbHelper;
    TextView progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        progress = findViewById(R.id.overallProgress);
        fbHelper = new FirebaseHelper();

    }

    @Override
    protected void onStart() {
        super.onStart();

        // Get questions, answers
        fbHelper.getAllAnswers();
        fbHelper.getQuestions();
        fbHelper.getProgress();

        String currentProg = String.valueOf(Questions.currentProgress);
        progress.setText("You have completed " + currentProg + "%!");
    }

    public void goToChallenge(View view) {
        Intent intent = new Intent(this, CodingQuestions.class);
        startActivity(intent);
    }

    public void goToQuestion(View view) {

        Intent intent = new Intent(this, Question.class);
        startActivity(intent);
    }

    public void goToTopics(View view) {
        Intent intent = new Intent(this,Topics.class);
        startActivity(intent);
    }

    public void logOut(View view) {
        fbHelper.logOut();

        // Pop back to Sign Up
        this.finish();
    }
}
