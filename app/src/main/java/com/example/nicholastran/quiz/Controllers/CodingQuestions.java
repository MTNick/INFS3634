package com.example.nicholastran.quiz.Controllers;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.example.nicholastran.quiz.Models.Questions;
import com.example.nicholastran.quiz.R;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class CodingQuestions extends Activity {

    EditText userCode;
    TextView compilerOutput;
    TextView output;
    TextView question;
    int qNumber;
    SVProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coding_questions);
        userCode = findViewById(R.id.userCode);
        compilerOutput = findViewById(R.id.compileOutput);
        output = findViewById(R.id.output);
        question = findViewById(R.id.question);
        qNumber = 0;

        question.setText(Questions.challenges[0]);
    }

    // Go to next question
    public void nextQuestion(View view) {
        qNumber++;
        if (qNumber >= Questions.challenges.length) {
            this.finish();
        }
        else {
            question.setText(Questions.challenges[qNumber]);
        }
    }

    // Compile the user's code
    public void runCode(View view) {
        hud = new SVProgressHUD(CodingQuestions.this);
        hud.show();
        String input = userCode.getText().toString();
        firstServiceCall(input);
    }

    private void firstServiceCall(String userInput) {
        String code = "class Main {" +
                userInput +
                "}";
        String query = Uri.encode(code);
        String url = "https://api.judge0.com/submissions/?base64_encoded=false&wait=false&language_id=26&source_code=" + query;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String token = response.getString("token");
                            final String url2 = "https://api.judge0.com/submissions/" + token;
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    secondServiceCall(url2);
                                }
                            }, 3000);

                        }
                        catch (JSONException e) {
                            System.out.println("ERROR: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(jsonObjReq);
    }

    private void secondServiceCall(String url) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        hud.dismiss();
                        try {
                            output.setText("Output: " + response.getString("stdout"));
                            compilerOutput.setText("Compiler: " + response.getString("compile_output"));
                        }
                        catch (JSONException e) {
                            new SVProgressHUD(CodingQuestions.this).showErrorWithStatus(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        Volley.newRequestQueue(getApplicationContext()).add(jsonObjReq);
    }
}
