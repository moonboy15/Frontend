package com.example.signuploginfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class CategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        RelativeLayout buttonHealthyFood = findViewById(R.id.button_healthyFood);
        buttonHealthyFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoriesActivity.this, HealthyFoodActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout buttonBmiCalculator = findViewById(R.id.button_bmiCalculator);
        buttonBmiCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoriesActivity.this, BMICalculatorActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout buttonUnhealthyFood = findViewById(R.id.button_unhealthyFood);
        buttonUnhealthyFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoriesActivity.this, UnhealthyFoodActivity.class);
                startActivity(intent);
            }
        });

        ImageView BtnProfile = findViewById(R.id.button_to_profile);

        BtnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginActivity loginActivity = new LoginActivity();
                loginActivity.isUser();
//                Intent intent = new Intent(CategoriesActivity.this, UserProfile.class);
//
//                startActivity(intent);
            }
        });
    }
}
