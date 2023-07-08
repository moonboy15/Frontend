package com.example.healthyitemlistview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FoodDetailsActivity extends AppCompatActivity {

    private RecyclerView ingredientsRecyclerView;
    private IngredientsAdapter ingredientsAdapter;
    private LinearLayout recipeLayout;
    private Button seeRecipeButton;
    private TextView recipe_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_food_details);

        recipeLayout = findViewById(R.id.recipeView_layout);
        seeRecipeButton = findViewById(R.id.button_see_recipe);
        recipe_textView = findViewById(R.id.recipe_textView);

        seeRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleRecipeLayout();
            }
        });

        Intent intent = getIntent();
        String foodName = intent.getStringExtra("food_name");
        String foodRecipe = intent.getStringExtra("food_recipe");
        String foodImage = intent.getStringExtra("food_image");
        String foodCategory = intent.getStringExtra("food_category");
        String foodType = intent.getStringExtra("food_type");
        int foodCalories = intent.getIntExtra("food_calories", 0);
        String foodBenefit = intent.getStringExtra("food_benefit");
        String foodDescription = intent.getStringExtra("food_description");
        ArrayList<String> ingredientList = intent.getStringArrayListExtra("food_ingredients");
        ArrayList<String> measurementList = intent.getStringArrayListExtra("food_measurements");


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

        ImageView foodImageView = findViewById(R.id.foodDetail_imageView);
        if (foodImage !=null) {
            int resourceId = getResources().getIdentifier(foodImage, "drawable", getPackageName());
            if (resourceId != 0) {
                Drawable foodImageDrawable = ContextCompat.getDrawable(this, resourceId);
                foodImageView.setImageDrawable(foodImageDrawable);
            }
            else{
                foodImageView.setImageResource(R.drawable.no_image);
            }
        }
        else {
            foodImageView.setImageResource(R.drawable.no_image);
        }

        ingredientsRecyclerView = findViewById(R.id.ingredientsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        ingredientsRecyclerView.setLayoutManager(layoutManager);
        ingredientsRecyclerView.setHasFixedSize(true);

        IngredientsAdapter adapter = new IngredientsAdapter(ingredientList, measurementList);
        ingredientsRecyclerView.setAdapter(adapter);

        recipe_textView.setText(foodRecipe);
    }
    private void toggleRecipeLayout() {
        if (recipeLayout.getVisibility() == View.VISIBLE) {
            recipeLayout.setVisibility(View.GONE);
        } else {
            recipeLayout.setVisibility(View.VISIBLE);
        }
    }
}

