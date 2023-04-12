package com.example.calculationpractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Random;


public class Addition extends AppCompatActivity {
//Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11;
TextView scoreView,quesNumber,quesText,timerTextView;
Toolbar additionActivityToolbar;
MediaPlayer totalScoringSound,correct_ans_sound,start_sound_effect;
TextInputEditText ansEditText;
TextInputLayout textInputLayout;
String activityTypeSelected;
Button btnPass,btnStart;
int level;
long time;
private int score;
private int i,questionLength, correctAnswer;
LottieAnimationView hourGlassAnim;
private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);

        additionActivityToolbar = findViewById(R.id.additionActivityToolbar);
        setSupportActionBar(additionActivityToolbar);

        btnPass = findViewById(R.id.btnPass);
        btnPass.setClickable(false);
        ansEditText = findViewById(R.id.ansEditText);
        textInputLayout = findViewById(R.id.textInputLayout);
        ansEditText.setText(String.valueOf(0));
        timerTextView = findViewById(R.id.timerTextView);
        scoreView = findViewById(R.id.scoreView);
        quesNumber = findViewById(R.id.quesNumber);
        quesText = findViewById(R.id.quesText);

        Intent iAdd = getIntent();
        level = iAdd.getIntExtra("difficultySelected", 1);
        time = iAdd.getLongExtra("timeSelected",60000);
        activityTypeSelected = iAdd.getStringExtra("activityTypeSelected");

        String tempTime = String.format(Locale.ENGLISH,"%02d : %02d",time/1000/60,(time/1000)%60);
        timerTextView.setText(tempTime);
        quesNumber.setText("");
        scoreView.setText("Score: 0");
        totalScoringSound=MediaPlayer.create(this,R.raw.total_scoring_sound);
        correct_ans_sound= MediaPlayer.create(this,R.raw.correct_ans_sound2);
        start_sound_effect= MediaPlayer.create(this,R.raw.start_sound_effect);

    }
    public void doThis(View view){
        Button currButton = (Button) view;
        int value = Integer.parseInt(currButton.getText().toString());
        ansEditText.setText(String.valueOf((Integer.parseInt(ansEditText.getText().toString())*10)+value));
    }
    public void dontThis(View view){
        ansEditText.setText(String.valueOf((Integer.parseInt(ansEditText.getText().toString())/10)));
    }
    public void startThis(View view) {
        start_sound_effect.start();
        btnStart = findViewById(R.id.btnStart);
        Animation scale = AnimationUtils.loadAnimation(this,R.anim.scale);
        btnStart.setAnimation(scale);
        TextView scoreView = findViewById(R.id.scoreView);


//        The static method random() of the Math class returns a pseudorandom double value in the range from 0.0 to 1.0
        Random rand = new Random();
        setScore(0);
        scoreView.setText("Score: " +getScore());
        btnPass.setClickable(true);
        hourGlassAnim = findViewById(R.id.hourGlassAnim);
        hourGlassAnim.playAnimation();
        //timer
        if(countDownTimer!=null){//so that no two timer can be run parallelly
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(time,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                //converting milliseconds to minute and seconds
                String sDuration = String.format(Locale.ENGLISH,"%02d : %02d",millisUntilFinished/1000/60,(millisUntilFinished/1000)%60);
                //set converted string on text view
                timerTextView.setText(sDuration);
            }
            @Override
            public void onFinish() {
                timerTextView.setText("00 : 00");
                showDialog();
                Toast.makeText(Addition.this, "complete", Toast.LENGTH_SHORT).show();
                ansEditText.setText("0");
                hourGlassAnim.cancelAnimation();

                btnPass.setClickable(false);

            }

        };
        countDownTimer.start();
        //logic of per level

        if (level == 1) {
            setQuestionLength(1);
            setScore(0);
            scoreView.setText("Score: "+getScore());
            generateNextEasyQuestion();
            btnPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    generateNextEasyQuestion();
                    //following code is used before that after 10 questions it stops process but now timer will end the process
//                    if(getQuestionLength()>10){
//                        Toast.makeText(Addition.this, "Total score = "+getScore(), Toast.LENGTH_SHORT).show();
//                        quesText.setText("thank you");
//                        showDialog();
//                    }else{
//                        generateNextEasyQuestion();
//                        Toast.makeText(Addition.this, "ques length"+getQuestionLength(), Toast.LENGTH_SHORT).show();
//                    }
                }
            });
            ansEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    btnPass.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            generateNextEasyQuestion();
//                            if(getQuestionLength()>11){
//                                Toast.makeText(Addition.this, "Total score = "+getScore(), Toast.LENGTH_SHORT).show();
//                                quesText.setText("thank you");
//                                showDialog();
//                            }
                        }
                    });
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(Integer.parseInt(ansEditText.getText().toString())== getCorrectAnswer()){
                        correct_ans_sound.start();
                        generateNextEasyQuestion();
                        ansEditText.setText("0");
                        setScore(getScore()+1);
                        scoreView.setText(String.valueOf("Score: "+getScore()));
                    }
//                    if(getQuestionLength()>11){
//                        Toast.makeText(Addition.this, "Total score = "+getScore(), Toast.LENGTH_SHORT).show();
//                        quesText.setText("thank you");
//                        showDialog();
//                    }else if(Integer.parseInt(ansEditText.getText().toString())== getCorrectAnswer()){
//                        if(getQuestionLength()<=10){
//                            generateNextEasyQuestion();
//                            setScore(getScore()+1);
//                            scoreView.setText(String.valueOf("Score: "+getScore()));
//                            ansEditText.setText(String.valueOf(0));
//                            Toast.makeText(Addition.this, "ques length = "+getQuestionLength(), Toast.LENGTH_SHORT).show();
//                        }else{
//                            Toast.makeText(Addition.this, "Total score = "+getScore(), Toast.LENGTH_SHORT).show();
//                            quesText.setText("thank you");
//                            showDialog();
//                        }
//                    }
                }
                @Override
                public void afterTextChanged(Editable s) {
                    //do nothing
                }
            });
        } else if (level == 2) {

            setQuestionLength(1);
            setScore(0);
            scoreView.setText("Score: "+getScore());
            generateNextMediumQuestion();
            btnPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    generateNextMediumQuestion();
                }
            });
            ansEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    btnPass.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            generateNextMediumQuestion();
                        }
                    });
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(Integer.parseInt(ansEditText.getText().toString())== getCorrectAnswer()){
                        correct_ans_sound.start();
                        generateNextMediumQuestion();
                        ansEditText.setText("0");
                        setScore(getScore()+1);
                        scoreView.setText(String.valueOf("Score: "+getScore()));
                    }
                }
                @Override
                public void afterTextChanged(Editable s) {
                    //do nothing
                }
            });
        } else if (level == 3) {
            quesNumber.setTextSize(35);
            quesText.setTextSize(31);
            setQuestionLength(1);
            setScore(0);
            scoreView.setText("Score: "+getScore());
            generateNextHardQuestion();
            btnPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    generateNextHardQuestion();
                }
            });
            ansEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    btnPass.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            generateNextHardQuestion();
                        }
                    });
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(Integer.parseInt(ansEditText.getText().toString())== getCorrectAnswer()){
                        correct_ans_sound.start();
                        generateNextHardQuestion();
                        ansEditText.setText("0");
                        setScore(getScore()+1);
                        scoreView.setText(String.valueOf("Score: "+getScore()));
                    }
                }
                @Override
                public void afterTextChanged(Editable s) {
                    //do nothing
                }
            });
        }else if(level==4){
            quesNumber.setTextSize(35);
            quesText.setTextSize(31);
            setQuestionLength(1);
            setScore(0);
            scoreView.setText("Score: "+getScore());
            generateNextExpertQuestion();
            btnPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    generateNextExpertQuestion();
                }
            });
            ansEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    btnPass.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            generateNextExpertQuestion();
                        }
                    });
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(Integer.parseInt(ansEditText.getText().toString())== getCorrectAnswer()){
                        correct_ans_sound.start();
                        generateNextExpertQuestion();
                        ansEditText.setText("0");
                        setScore(getScore()+1);
                        scoreView.setText(String.valueOf("Score: "+getScore()));
                    }
                }
                @Override
                public void afterTextChanged(Editable s) {
                    //do nothing
                }
            });
        }else if(level == 5){
            quesNumber.setTextSize(35);
            quesText.setTextSize(31);
            setQuestionLength(1);
            setScore(0);
            scoreView.setText("Score: "+getScore());
            generateNextNightmareQuestion();
            btnPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    generateNextNightmareQuestion();
                }
            });
            ansEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    btnPass.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            generateNextNightmareQuestion();
                        }
                    });
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(Integer.parseInt(ansEditText.getText().toString())== getCorrectAnswer()){
                        correct_ans_sound.start();
                        generateNextNightmareQuestion();
                        ansEditText.setText("0");
                        setScore(getScore()+1);
                        scoreView.setText(String.valueOf("Score: "+getScore()));
                    }
                }
                @Override
                public void afterTextChanged(Editable s) {
                    //do nothing
                }
            });
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }



    void generateNextEasyQuestion(){
        quesNumber.setText("Q"+getQuestionLength()+".");
        Random rand = new Random();

        if(activityTypeSelected.equals("addition")){
            int a = rand.nextInt(11);
            int b = 1 + rand.nextInt(10);
            setCorrectAnswer(a+b);
            quesText.setText(String.valueOf(a + " + " + b + " = "));
        }else if(activityTypeSelected.equals("multiplication")){
            int a = 1+ rand.nextInt(9);
            int b = 1 + rand.nextInt(9);
            setCorrectAnswer(a*b);
            quesText.setText(String.valueOf(a + " × " + b + " = "));
        }else if(activityTypeSelected.equals("subtraction")){
            int a = 1 + rand.nextInt(8);
            int b = rand.nextInt(a);
            setCorrectAnswer(a-b);
            quesText.setText(String.valueOf(a + " - " + b + " = "));
        }else if(activityTypeSelected.equals("division")){
            int divAns = 1 + rand.nextInt(4);
            int b = 1 + rand.nextInt(9);
            int a = divAns*b;
            setCorrectAnswer(divAns);
            quesText.setText(String.valueOf(a + " ÷ " + b + " = "));
        }else if(activityTypeSelected.equals("squaring")){
            int a = 2+rand.nextInt(9);
            setCorrectAnswer(a*a);
            quesText.setText(String.valueOf(a + "\u00B2 = "));
        }



        ansEditText.setText("0");
        setQuestionLength(getQuestionLength()+1);
    }
    void generateNextMediumQuestion(){
        quesNumber.setText("Q"+getQuestionLength()+".");
        Random rand = new Random();

        if(activityTypeSelected.equals("addition")){
            int a = 11 + (int) (Math.random() * 99);
            int b = 11 + (int) (Math.random() * 99);
            setCorrectAnswer(a+b);
            quesText.setText(String.valueOf(a + " + " + b + " = "));
        }else if(activityTypeSelected.equals("multiplication")){
            int a = 10 + rand.nextInt(47);
            int b = 10 + rand.nextInt(47);
            setCorrectAnswer(a*b);
            quesText.setText(String.valueOf(a + " × " + b + " = "));
        }else if(activityTypeSelected.equals("subtraction")){
            int a = 11 + rand.nextInt(61);
            int b = 10+ rand.nextInt(a-11);
            setCorrectAnswer(a-b);
            quesText.setText(String.valueOf(a + " - " + b + " = "));
        }else if(activityTypeSelected.equals("division")){
            int divAns = 3 + rand.nextInt(11);
            int b = 4 + rand.nextInt(20);
            int a = divAns*b;
            setCorrectAnswer(divAns);
            quesText.setText(String.valueOf(a + " ÷ " + b + " = "));
        }else if(activityTypeSelected.equals("squaring")){
            int a = 11+rand.nextInt(10);
            setCorrectAnswer(a*a);
            quesText.setText(String.valueOf(a + "\u00B2 = "));
        }



        ansEditText.setText("0");
        setQuestionLength(getQuestionLength()+1);
    }
    void generateNextHardQuestion(){
        quesNumber.setText("Q"+getQuestionLength()+".");
        Random rand = new Random();

        if(activityTypeSelected.equals("addition")){
            int a = rand.nextInt(250) + 110;
            int b = rand.nextInt(250) + 110;
            quesText.setText(String.valueOf(a + " + " + b + " = "));
            setCorrectAnswer(a+b);
        }else if(activityTypeSelected.equals("multiplication")){
            int a = 57 + rand.nextInt(150);
            int b = 67 + rand.nextInt(150);
            setCorrectAnswer(a*b);
            quesText.setText(String.valueOf(a + " × " + b + " = "));
        }else if(activityTypeSelected.equals("subtraction")){
            int a = 101 + rand.nextInt(388);
            int b = 71+ rand.nextInt(a-101);
            setCorrectAnswer(a-b);
            quesText.setText(String.valueOf(a + " - " + b + " = "));
        }else if(activityTypeSelected.equals("division")){
            int divAns = 6 + rand.nextInt(25);
            int b = 11 + rand.nextInt(29);
            int a = divAns*b;
            setCorrectAnswer(divAns);
            quesText.setText(String.valueOf(a + " ÷ " + b + " = "));
        }else if(activityTypeSelected.equals("squaring")){
            int a = 21+rand.nextInt(10);
            setCorrectAnswer(a*a);
            quesText.setText(String.valueOf(a + "\u00B2 = "));
        }


        ansEditText.setText("0");
        setQuestionLength(getQuestionLength()+1);
    }
    void generateNextExpertQuestion(){
        quesNumber.setText("Q"+getQuestionLength()+".");
        Random rand = new Random();

        if(activityTypeSelected.equals("addition")){
            int a = rand.nextInt(610) + 360;
            int b = rand.nextInt(690) + 360;
            setCorrectAnswer(a+b);
            quesText.setText(String.valueOf(a + " + " + b + " = "));
        }else if(activityTypeSelected.equals("multiplication")){
            int a = 167 + rand.nextInt(780);
            int b = 167 + rand.nextInt(780);
            setCorrectAnswer(a*b);
            quesText.setText(String.valueOf(a + " × " + b + " = "));
        }else if(activityTypeSelected.equals("subtraction")){
            int a = 689 + rand.nextInt(1249);
            int b = 489+ rand.nextInt(a-689);
            setCorrectAnswer(a-b);
            quesText.setText(String.valueOf(a + " - " + b + " = "));
        }else if(activityTypeSelected.equals("division")){
            int divAns = 12 + rand.nextInt(29);
            int b = 24 + rand.nextInt(94);
            int a = divAns*b;
            setCorrectAnswer(divAns);
            quesText.setText(String.valueOf(a + " ÷ " + b + " = "));
        }else if(activityTypeSelected.equals("squaring")){
            int a = 31+rand.nextInt(20);
            setCorrectAnswer(a*a);
            quesText.setText(String.valueOf(a + "\u00B2 = "));
        }


        ansEditText.setText("0");
        setQuestionLength(getQuestionLength()+1);
    }
    void generateNextNightmareQuestion(){
        quesNumber.setText("Q"+getQuestionLength()+".");
        Random rand = new Random();

        if(activityTypeSelected.equals("addition")){
            int a = rand.nextInt(7500) + 1288;
            int b = rand.nextInt(6800) + 1198;
            setCorrectAnswer(a+b);
            quesText.setText(String.valueOf(a + " + " + b + " = "));
        }else if(activityTypeSelected.equals("multiplication")){
            int a = 850 + rand.nextInt(7149);
            int b = 850 + rand.nextInt(9148);
            setCorrectAnswer(a*b);
            quesText.setText(String.valueOf(a + " × " + b + " = "));
        }else if(activityTypeSelected.equals("subtraction")){
            int a = 1289 + rand.nextInt(8249);
            int b = 1089+ rand.nextInt(a-1289);
            setCorrectAnswer(a-b);
            quesText.setText(String.valueOf(a + " - " + b + " = "));
        }else if(activityTypeSelected.equals("division")){
            int divAns = 23 + rand.nextInt(46);
            int b = 102 + rand.nextInt(134);
            int a = divAns*b;
            setCorrectAnswer(divAns);
            quesText.setText(String.valueOf(a + " ÷ " + b + " = "));
        }else if(activityTypeSelected.equals("squaring")){
            int a = 51+rand.nextInt(50);
            setCorrectAnswer(a*a);
            quesText.setText(String.valueOf(a + "\u00B2 = "));
        }


        ansEditText.setText("0");
        setQuestionLength(getQuestionLength()+1);
    }
    int getAnswerLength(int ans){
        int count = 0;
        while(ans!=0){
            ans/=10;
            count++;
        }
        return count;
    }

    //bug fixed when timer is running but back button is pressed and the app crashed
    @Override
    public void onBackPressed() {
        if(countDownTimer!=null){
            countDownTimer.cancel();
        }
        super.onBackPressed();
    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        // Stop the timer
//        countDownTimer.cancel();
//    }
    public int getQuestionLength() {
        return questionLength;
    }

    public void setQuestionLength(int questionLength) {
        this.questionLength = questionLength;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    public void showDialog(){
        final Dialog myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.quiz_over);
        totalScoringSound.start();
        myDialog.setCancelable(false);
        Button btnOk = myDialog.findViewById(R.id.btnOk);
        TextView totalScoreViewer = myDialog.findViewById(R.id.totalScoreViewer);
        totalScoreViewer.setText(String.valueOf("Your Total Score: "+getScore()));
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPass.setClickable(false);
                quesText.setText("Press Start");
                setQuestionLength(1);
                quesNumber.setText("");
                myDialog.dismiss();
            }
        });
        myDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        new MenuInflater(this).inflate(R.menu.instruction_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int itemId = item.getItemId();
//        if(itemId==R.id.opt_instruction){
//            showInstructionDialog();
//        }
//
//        return super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.opt_instruction) {// Handle click on action item
            showInstructionDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void showInstructionDialog(){
        final Dialog instructionDialog = new Dialog(this);
        instructionDialog.setContentView(R.layout.instruction_layout);
        instructionDialog.setCancelable(false);
        Button instructionOkBtn = instructionDialog.findViewById(R.id.instructionOkBtn);
        instructionOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instructionDialog.dismiss();
            }
        });
        instructionDialog.show();
    }

}