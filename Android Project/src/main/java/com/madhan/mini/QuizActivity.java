package com.madhan.mini;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView tvQuestion, tvScore, tvQuestionNo, tvTimer;
    private RadioGroup radioGroup;
    private RadioButton rb1, rb2, rb3;
    private Button btnNext;


    int totalQuestions;
    int qCounter = 0;
    int score;

    ColorStateList dfRbColor;
    boolean answered;

    CountDownTimer countDownTimer;

    private QuestionModel currentQuestion;

    private List<QuestionModel>questionsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionsList =new ArrayList<>();
        tvQuestion =findViewById(R.id.textQuestion);
        tvScore =findViewById(R.id.textScore);
        tvQuestionNo =findViewById(R.id.textQuestionNo);
        tvTimer =findViewById(R.id.textTimer);

        radioGroup =findViewById(R.id.radioGroup);
        rb1 =findViewById(R.id.rb1);
        rb2 =findViewById(R.id.rb2);
        rb3 =findViewById(R.id.rb3);
        btnNext =findViewById(R.id.btnNext);

        dfRbColor = rb1.getTextColors();

        addQuestions();
        totalQuestions=questionsList.size();
        showNextQuestion();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answered == false){
                    if(rb1.isChecked() || rb2.isChecked() || rb3.isChecked()){
                        checkAnswer();
                        countDownTimer.cancel();

                    }else{
                        Toast.makeText(QuizActivity.this, "Please Select an Option", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    showNextQuestion();
                }
            }
        });
    }

    private void checkAnswer() {
        answered = true;
        RadioButton rbSelected = findViewById(radioGroup.getCheckedRadioButtonId());
        int answerNo = radioGroup.indexOfChild(rbSelected) +1;
        if (answerNo == currentQuestion.getCorrectAnsNo()){
            score++;
            tvScore.setText(("Score: "+score));
        }
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);
        switch(currentQuestion.getCorrectAnsNo()){
            case 1:
                rb1.setTextColor(Color.GREEN);
                break;
            case  2:
                rb2.setTextColor(Color.GREEN);
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                break;
        }
        if(qCounter<totalQuestions){
            btnNext.setText("Next");
        }else{
            btnNext.setText("Finish");
        }
    }

    private void showNextQuestion() {

        radioGroup.clearCheck();
        rb1.setTextColor(dfRbColor);
        rb2.setTextColor(dfRbColor);
        rb3.setTextColor(dfRbColor);

        if(qCounter<totalQuestions){
            timer();
            currentQuestion =questionsList.get(qCounter);
            tvQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());

            qCounter++;
            btnNext.setText("Submit");
            tvQuestionNo.setText("Question: "+qCounter+"/"+totalQuestions);
            answered = false;

        }else{
            finish();
        }
    }

    private void timer() {
        countDownTimer = new CountDownTimer(20000,1000) {
            @Override
            public void onTick(long l) {
                tvTimer.setText("00:" + l/1000);
            }

            @Override
            public void onFinish() {
                showNextQuestion();

            }
        }.start( );
    }

    private void addQuestions(){
        questionsList.add(new QuestionModel("How GFG is Used ?","To solve DSA Problems","To Learn New Language","Both 1 and 2",3));
        questionsList.add(new QuestionModel("What is GCM in Android ?","Google Check Message ","Google Cloud Manager","Google Cloud Messaging",3));
        questionsList.add(new QuestionModel("Abbrivation of API ?","Application Program Interfear","Application Programming Interface","Android Programming Interface",2));
        questionsList.add(new QuestionModel("Who is the CEO of GOOGLE ?","Sundar Pichai","Satya Nadela","Jeff Bezoz",1));
        questionsList.add(new QuestionModel("Who is the CEO of Amazon ?","Sundar Pichai","Satya Nadela","Jeff Bezoz",3));
        questionsList.add(new QuestionModel("Who is the Founder of Apple ?","Tim Cook","Satya Nadela","Jeff Bezoz",1));
        questionsList.add(new QuestionModel("Who is the CEO of Microsoft ?","Sundar Pichai","Satya Nadela","Jeff Bezoz",2));
        questionsList.add(new QuestionModel("Who is the Asian no 1 Millanior ?","Suresh Ambani","Mukesh Ambani","Anil Ambani",2));
        questionsList.add(new QuestionModel("What is the full form of GFG ?","Green For Geeks","Geeks For Geeks","Geeks For Greens",2));
        questionsList.add(new QuestionModel("Who is the CEO of  Alphabets ?","Sundar Pichai","Satya Nadela","Jeff Bezoz",1));

    }
}