package com.example.healthyitemlistview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FoodDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_food_details);

        Intent intent = getIntent();
        String foodName = intent.getStringExtra("food_name");
        String foodCategory = intent.getStringExtra("food_category");
        String foodType = intent.getStringExtra("food_type");
        int foodCalories = intent.getIntExtra("food_calories", 0);
        String foodBenefit = intent.getStringExtra("food_benefit");
        String foodDescription = intent.getStringExtra("food_description");

        TextView foodNameTextView = findViewById(R.id.textView_food_name);
        TextView foodCategoryTextView = findViewById(R.id.textView_food_category);
        TextView foodTypeTextView = findViewById(R.id.textView_food_type);
        TextView foodCaloriesTextView = findViewById(R.id.textView_food_calories);
        TextView foodBenefitTextView = findViewById(R.id.textView_food_benefit);
        TextView foodDescriptionTextView = findViewById(R.id.textView_food_description);

        foodNameTextView.setText(foodName);
        foodCategoryTextView.setText(foodCategory);
        foodTypeTextView.setText(foodType);
        foodCaloriesTextView.setText(String.valueOf(foodCalories));
        foodBenefitTextView.setText(foodBenefit);
        foodDescriptionTextView.setText(foodDescription);
    }
}



