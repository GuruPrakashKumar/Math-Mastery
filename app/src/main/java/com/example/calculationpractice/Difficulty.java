package com.example.calculationpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

public class Difficulty extends AppCompatActivity {
int level;
long time;
String type;
Button btnConfirm;
Intent iConfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);
        Intent iadd = getIntent();
        type = iadd.getStringExtra("typeSelected");
        String[] difficultyArrayAdapter = new String[] {"Easy","Medium","Hard","Expert","Nightmare"};
        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<>(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item,difficultyArrayAdapter);
        AutoCompleteTextView difficultySelectionView = findViewById(R.id.difficultySelectionView);
        difficultySelectionView.setAdapter(difficultyAdapter);

        String[] timeArrayAdapter = new String[] {"1 Minute","2 Minutes","3 Minutes","4 Minutes","5 Minutes"};
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item,timeArrayAdapter);
        AutoCompleteTextView timeSelectionView = findViewById(R.id.timeSelectionView);
        timeSelectionView.setAdapter(timeAdapter);

        btnConfirm = findViewById(R.id.btnCnf);
        iConfirm = new Intent(this,Addition.class);
//        if(type.equals("addition")){
//        }else{
//            iConfirm = new Intent(this,MainActivity.class);
//        }
        difficultySelectionView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                difficultySelectionView.setError(null);
            }
        });
        timeSelectionView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                timeSelectionView.setError(null);
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String difficulty = difficultySelectionView.getText().toString();
                if (TextUtils.isEmpty(difficulty)) {
                    difficultySelectionView.setError("This field is required");
                    return; // or show an error message and do not submit the form
                }
                String timeSelectedItem = timeSelectionView.getText().toString();
                if (TextUtils.isEmpty(timeSelectedItem)) {
                    timeSelectionView.setError("This field is required");
                    return; // or show an error message and do not submit the form
                }

                switch (difficulty) {
                    case "Easy":
                        level = 1;
                        break;
                    case "Medium":
                        level = 2;
                        break;
                    case "Hard":
                        level = 3;
                        break;
                    case "Expert":
                        level = 4;
                        break;
                    default:
                        level = 5;
                        break;
                }
                switch (timeSelectedItem) {
                    case "1 Minute":
                        time = 60000;
                        break;
                    case "2 Minutes":
                        time = 120000;
                        break;
                    case "3 Minutes":
                        time = 180000;
                        break;
                    case "4 Minutes":
                        time = 240000;
                        break;
                    default:
                        time = 300000;
                        break;
                }
                iConfirm.putExtra("difficultySelected",level);
                iConfirm.putExtra("timeSelected",time);
                iConfirm.putExtra("activityTypeSelected",type);
                startActivity(iConfirm);


            }
        });



    }
}