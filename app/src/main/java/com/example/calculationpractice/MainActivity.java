package com.example.calculationpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.button.MaterialButtonToggleGroup;

public class MainActivity extends AppCompatActivity {
//Button btnEasy,btnMedium,btnHard;
ImageButton additionActivityBtn;
CardView additionCard,multiplicationCard,subtractionCard,divisionCard,squaringCard;

Animation scale,cardTap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        additionActivityBtn.findViewById(R.id.additionActivityBtn);
//        additionActivityBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(iadd);
//            }
//        });
        additionCard = findViewById(R.id.additionCard);
        multiplicationCard = findViewById(R.id.multiplicationCard);
        subtractionCard = findViewById(R.id.subtractionCard);
        divisionCard = findViewById(R.id.divisionCard);
        squaringCard = findViewById(R.id.squaringCard);
        scale = AnimationUtils.loadAnimation(this,R.anim.scale);
        cardTap = AnimationUtils.loadAnimation(this,R.anim.cardtap);

    }

    public void additionActivity(View view) {
        //following commented code was for toggleButtonGroup --- can be used as a study purpose
//        MaterialButtonToggleGroup toggleBtnGroup = findViewById(R.id.toggleBtnGroup);
//        int level=0;
//        Toast.makeText(this, String.valueOf(toggleBtnGroup.getCheckedButtonId()), Toast.LENGTH_SHORT).show();
//        if(toggleBtnGroup.getCheckedButtonId()==R.id.btnEasy){
//            level = 1;
//        }else if(toggleBtnGroup.getCheckedButtonId()==R.id.btnMedium){
//            level=2;
//        }else if(toggleBtnGroup.getCheckedButtonId()==R.id.btnHard){
//            level=3;
//        }
        Intent iadd = new Intent(this,Difficulty.class);
        iadd.putExtra("typeSelected","addition");
        additionCard.startAnimation(cardTap);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(iadd);
            }
        },100);

    }

    public void multiplicationActivity(View view){
        Intent iadd = new Intent(this,Difficulty.class);
        iadd.putExtra("typeSelected","multiplication");
        multiplicationCard.startAnimation(cardTap);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(iadd);
            }
        },100);
    }
    public void subtractionActivity(View view){
        Intent iadd = new Intent(this,Difficulty.class);
        iadd.putExtra("typeSelected","subtraction");
        subtractionCard.startAnimation(cardTap);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(iadd);
            }
        },100);
    }
    public void divisionActivity(View view){
        Intent iadd = new Intent(this,Difficulty.class);
        iadd.putExtra("typeSelected","division");
        divisionCard.startAnimation(cardTap);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(iadd);
            }
        },100);
    }
    public void squaringActivity(View view){
        Intent iadd = new Intent(this,Difficulty.class);
        iadd.putExtra("typeSelected","squaring");
        squaringCard.startAnimation(cardTap);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(iadd);
            }
        },100);
    }
}
