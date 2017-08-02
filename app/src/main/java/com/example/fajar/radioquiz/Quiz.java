package com.example.fajar.radioquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Quiz extends AppCompatActivity {

    //deklarasi variable
    private QuizBank mQuizLibrary = new QuizBank();
    private TextView sScore;
    private TextView sQuestionText;
    private RadioButton mchoice1;
    private RadioButton mchoice2;
    private RadioButton mchoice3;
    private RadioButton mchoice4;

    private String manswer;
    private int mScore = 0;
    private int mQuestionNumber = 0;
    private int [] mQuestionFinish = {1,2,3,4,5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        setTitle("Quiz");

        //inisialisasi variable
        sScore = (TextView) findViewById(R.id.score);
        sQuestionText = (TextView) findViewById(R.id.question);
        mchoice1 = (RadioButton) findViewById(R.id.choice1);
        mchoice2 = (RadioButton) findViewById(R.id.choice2);
        mchoice3 = (RadioButton) findViewById(R.id.choice3);
        mchoice4 = (RadioButton) findViewById(R.id.choice4);

        updateQuestion();
        //menampilkan skor dari berapa pertanyaan
        updateScore(mScore);
    }

    //method untuk mengatur pertanyaan
    private void updateQuestion() {
        mchoice1.setChecked(false);
        mchoice2.setChecked(false);
        mchoice3.setChecked(false);
        mchoice4.setChecked(false);

        if (mQuestionNumber < mQuizLibrary.getLength()) {
            //mengeset disetiap komponen untuk jadi pertanyaan dan opsional
            sQuestionText.setText(mQuizLibrary.getQuestion(mQuestionNumber));
            mchoice1.setText(mQuizLibrary.getChoice(mQuestionNumber, 1));
            mchoice2.setText(mQuizLibrary.getChoice(mQuestionNumber, 2));
            mchoice3.setText(mQuizLibrary.getChoice(mQuestionNumber, 3));
            mchoice4.setText(mQuizLibrary.getChoice(mQuestionNumber, 4));
            manswer = mQuizLibrary.getCorret(mQuestionNumber);
            mQuestionNumber++;
        } else {
            Toast.makeText(Quiz.this, "Ini pertanyaan terakhir", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Quiz.this, HighScores.class);
            i.putExtra("score", mScore);
            startActivity(i);
        }
    }


    private  void setRandom(){
        mQuestionNumber = (int) Math.round(Math.random() *mQuizLibrary.getLength());
        for(int i =0; i< mQuestionFinish.length; i++){
            if(mQuestionNumber==mQuestionFinish[i] && mQuestionFinish[i]!=0){
                updateQuestion();
                mQuestionFinish[i]=0;
                break;
            }
        }
    }

    //method untuk mengatur skor
    private void updateScore(int point) {
        sScore.setText("" + mScore + "/" +  mQuizLibrary.getLength());
    }

    //method untuk button ketika diklik
    public void onclick (View view){
        RadioButton answer = (RadioButton) view;
        if (answer.getText() == manswer) {
            mScore = mScore + 1;
            Toast.makeText(Quiz.this, "Jawaban benar!", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(Quiz.this, "Jawaban salah!", Toast.LENGTH_SHORT).show();
        updateScore(mScore);
        updateQuestion();
    }
}
