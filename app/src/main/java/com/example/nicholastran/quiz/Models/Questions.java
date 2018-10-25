package com.example.nicholastran.quiz.Models;

import java.util.ArrayList;

public class Questions {

    public static ArrayList<Questions> questions = new ArrayList<>();
    public static ArrayList<Boolean> userProgress = new ArrayList<>();
    public static double currentProgress;
    public static String[] challenges = {
            "Print all numbers from 1 to 3",
            "Write a sorting function to sort 0, -1, 5, 2, 6 in ascending order",
            "Make a class called Car. Instantiate a Car object called Mazda. Print the Mazda's color"
    };

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
