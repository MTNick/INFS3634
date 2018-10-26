package com.example.nicholastran.quiz.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.nicholastran.quiz.R;

public class Topics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);
    }

    public void goToLoops(View view) {
        Intent intent = new Intent(this, Loops.class);
        startActivity(intent);
    }

    public void goToMethods(View view) {
        Intent intent = new Intent(this, Methods.class);
        startActivity(intent);
    }

    public void goToArrays (View view) {
        Intent intent = new Intent (this, Arrays.class);
        startActivity(intent);
    }
}
