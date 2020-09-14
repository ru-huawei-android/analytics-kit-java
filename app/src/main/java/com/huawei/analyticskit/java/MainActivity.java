package com.huawei.analyticskit.java;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvQuestion;

    private String[] questions;
    private Boolean[] answers = new Boolean[] { true, true, false, false, true };

    private int curQuestionIdx = 0;
    private int score = 0;

    private HiAnalyticsWrapper mHiAnalyticsWrapper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHiAnalyticsWrapper = new HiAnalyticsWrapper(this);
        mHiAnalyticsWrapper.setUpUserId();

        // Retrieve questions from resources
        questions = getResources().getStringArray(R.array.questions);

        // You can also use Context initialization
        tvQuestion = findViewById(R.id.tvQuestion);
        tvQuestion.setText(questions[curQuestionIdx]);

        Button btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(this);

        Button btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);

        Button btnAnswerTrue = findViewById(R.id.btnAnswerTrue);
        btnAnswerTrue.setOnClickListener(this);

        Button btnAnswerFalse = findViewById(R.id.btnAnswerFalse);
        btnAnswerFalse.setOnClickListener(this);

        Button btnPostScore = findViewById(R.id.btnPostScore);
        btnPostScore.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSettings:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivityForResult(intent, 0);
                break;

            case R.id.btnNext:
                curQuestionIdx = (curQuestionIdx + 1) % questions.length;
                nextQuestion();
                break;

            case R.id.btnAnswerTrue:
                checkAnswer(true);
                String questions = tvQuestion.getText().toString().trim();
                mHiAnalyticsWrapper.reportAnswerEvt(questions, "true");
                break;

            case R.id.btnAnswerFalse:
                checkAnswer(false);
                String question = tvQuestion.getText().toString().trim();
                mHiAnalyticsWrapper.reportAnswerEvt(question, "false");
                break;

            case R.id.btnPostScore:
                mHiAnalyticsWrapper.postScore(score);
                Toast.makeText(this, R.string.post_report_answer, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void nextQuestion() {
        tvQuestion.setText(questions[curQuestionIdx]);
    }

    private void checkAnswer(Boolean answer) {
        if (answer == answers[curQuestionIdx]) {
            score += 20;
            Toast.makeText(this, R.string.correct_answer, Toast.LENGTH_SHORT).show();
            // Report a customized Event
        } else {
            Toast.makeText(this, R.string.wrong_answer, Toast.LENGTH_SHORT).show();
            // Report a customized Event
        }
    }
}