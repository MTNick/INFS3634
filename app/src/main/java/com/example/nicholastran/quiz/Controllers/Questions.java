package com.example.nicholastran.quiz.Controllers;

import java.util.ArrayList;

public class Questions {

    public static ArrayList<Questions> questions = new ArrayList<>();
    public static ArrayList<Boolean> userProgress = new ArrayList<>();
    public static int currentProgress;

    private String question;
    private String answer;

    public Questions(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public static ArrayList<Questions> getQuestions() {
        return questions;
    }

    public static void setQuestions(ArrayList<Questions> questions) {
        Questions.questions = questions;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
