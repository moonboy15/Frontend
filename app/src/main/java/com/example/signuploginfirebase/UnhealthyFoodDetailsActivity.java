package com.example.signuploginfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import androidx.core.content.ContextCompat;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UnhealthyFoodDetailsActivity extends AppCompatActivity {

    private RecyclerView unhealthyIngredientsRecyclerView;

    private UnhealthyIngredientsAdapter unhealthyIngredientsAdapter;
    private LinearLayout alternativeLayout;
    private Button seeAlternativeButton;
    private TextView alternative_textView;

    List<FoodUnhealthy> foodList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_food_details_unhealthy);

        foodList = getIntent().getParcelableArrayListExtra("food_list");

        alternativeLayout = findViewById(R.id.alternativeView_layout);
        seeAlternativeButton = findViewById(R.id.button_see_alternative);
        alternative_textView = findViewById(R.id.alternative_textView);

        seeAlternativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleAlternativeLayout();
            }
        });

        Intent intent = getIntent();
        String foodName = intent.getStringExtra("food_name");
        String foodImage = intent.getStringExtra("food_image");
        String foodCategory = intent.getStringExtra("food_category");
        String foodType = intent.getStringExtra("food_type");
        int foodCalories = intent.getIntExtra("food_calories", 0);
        String foodDisbenefit = intent.getStringExtra("food_disbenefit");
        String foodAlternative = intent.getStringExtra("food_alternative");
        String foodDescription = intent.getStringExtra("food_description");
        ArrayList<String> ingredientList = intent.getStringArrayListExtra("food_ingredients");


        TextView foodNameTextView = findViewById(R.id.textView_food_name_unhealthy);
        TextView foodCategoryTextView = findViewById(R.id.textView_food_category_unhealthy);
        TextView foodTypeTextView = findViewById(R.id.textView_food_type_unhealthy);
        TextView foodCaloriesTextView = findViewById(R.id.textView_food_calories_unhealthy);
        TextView foodDisbenefitTextView = findViewById(R.id.textView_food_disbenefit);
        TextView foodDescriptionTextView = findViewById(R.id.textView_food_description_unhealthy);
        TextView foodAlternativeTextView = findViewById(R.id.alternative_textView);

        foodNameTextView.setText(foodName);
        foodCategoryTextView.setText(foodCategory);
        foodTypeTextView.setText(foodType);
        foodCaloriesTextView.setText(String.valueOf(foodCalories));
        foodDisbenefitTextView.setText(foodDisbenefit);
        foodDescriptionTextView.setText(foodDescription);
        foodAlternativeTextView.setText(foodAlternative);

        ImageView foodImageView = findViewById(R.id.foodDetail_imageView_unhealthy);
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

        unhealthyIngredientsRecyclerView = findViewById(R.id.ingredientsRecyclerView_unhealthy);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        unhealthyIngredientsRecyclerView.setLayoutManager(layoutManager);
        unhealthyIngredientsRecyclerView.setHasFixedSize(true);

        UnhealthyIngredientsAdapter adapter = new UnhealthyIngredientsAdapter(ingredientList, new IngredientsAdapter.OnIngredientClickListener() {
            @Override
            public void onIngredientClick(String ingredient) {
                openFoodDetailsForIngredient(ingredient);
            }
        });
        unhealthyIngredientsRecyclerView.setAdapter(adapter);
        alternative_textView.setText(foodAlternative);
    }
    private void toggleAlternativeLayout() {
        if (alternativeLayout.getVisibility() == View.VISIBLE) {
            alternativeLayout.setVisibility(View.GONE);
        } else {
            alternativeLayout.setVisibility(View.VISIBLE);
        }
    }

    private void openFoodDetailsForIngredient(String ingredient){
        FoodUnhealthy clickedFood = null;
        for (FoodUnhealthy food: foodList){
            if(food.getName().toLowerCase().equals(ingredient.toLowerCase())){
                clickedFood = food;
                break;
            }
        }

        if (clickedFood != null){
            openFoodDetailsLayout(clickedFood);
        }else {
            Toast.makeText(this, "No food found for ingredient: "+  ingredient, Toast.LENGTH_SHORT).show();
        }
    }

    private void openFoodDetailsLayout(FoodUnhealthy food) {
        Intent intent = new Intent(this, UnhealthyFoodDetailsActivity.class);
        intent.putExtra("food_list", new ArrayList<>(foodList));
        intent.putExtra("food_name", food.getName());
        intent.putExtra("food_alternative", food.getAlternative());
        intent.putExtra("food_image", food.getImage());
        intent.putExtra("food_description", food.getDescription());
        intent.putExtra("food_category", food.getCategory());
        intent.putExtra("food_type", food.getType());
        intent.putExtra("food_calories", food.getCalories());
        intent.putExtra("food_disbenefit", food.getDisbenefit());
        intent.putExtra("food_ingredients", new ArrayList<>(food.getIngredients()));
        startActivity(intent);
    }
}
