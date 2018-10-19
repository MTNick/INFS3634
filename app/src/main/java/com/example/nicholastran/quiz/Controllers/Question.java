package com.example.nicholastran.quiz.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nicholastran.quiz.Models.Answers;
import com.example.nicholastran.quiz.Models.FirebaseHelper;
import com.example.nicholastran.quiz.Models.Questions;
import com.example.nicholastran.quiz.R;

public class Question extends AppCompatActivity {

    TextView question;
    int qNumber;
    FirebaseHelper fbHelper;
    TextView correct;
    Button aButton;
    Button bButton;
    Button cButton;
    Button dButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        question = findViewById(R.id.questionText);
        correct = findViewById(R.id.textViewCorrect);
        fbHelper = new FirebaseHelper();

        qNumber = 0;
        String firstQuestion = Questions.questions.get(0).getQuestion();
        question.setText(firstQuestion);

        // Set the answer options
        Answers ans = Answers.allAnswers.get(0);
        aButton = findViewById(R.id.buttonAnswer1);
        bButton = findViewById(R.id.buttonAnswer2);
        cButton = findViewById(R.id.buttonAnswer3);
        dButton = findViewById(R.id.buttonAnswer4);

        aButton.setText(ans.getA());
        bButton.setText(ans.getB());
        cButton.setText(ans.getC());
        dButton.setText(ans.getD());
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if they've already done the question
        if (fbHelper.gotQuestionCorrect(qNumber)) {
            correct.setText("Completed!");
        }
    }

    public void skipClicked(View view) {
        nextQuestion();
    }

    public void answerClicked(View view) {
        Button b = (Button) view;
        String answerChosen = b.getText().toString();

        if (fbHelper.isAnswerCorrect(qNumber, answerChosen)) {
            // Correct answer
            Toast.makeText(this, "Correct :)", Toast.LENGTH_LONG).show();
            nextQuestion();
        }
        else {
            // Incorrect answer
            Toast.makeText(this, "Incorrect, try again :(", Toast.LENGTH_LONG).show();
        }
    }

    public void goToHome(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    private void nextQuestion() {
        qNumber++;
        if (qNumber > Questions.questions.size()) {
            // Change the question
            String nextQuestion = Questions.questions.get(qNumber).getQuestion();
            question.setText(nextQuestion);

            // Change the answers too
            Answers ans = Answers.allAnswers.get(qNumber);
            aButton.setText(ans.getA());
            bButton.setText(ans.getB());
            cButton.setText(ans.getC());
            dButton.setText(ans.getD());
        }
        else {
            // POP BACK TO HOME
            this.finish();
        }
    }
}
