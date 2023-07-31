package com.example.signuploginfirebase;


import static com.google.firebase.database.core.RepoManager.clear;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

public class FoodDetailsActivity extends AppCompatActivity {

    private RecyclerView ingredientsRecyclerView;
    private LinearLayout recipeLayout;
    private Button seeRecipeButton;
    private TextView recipe_textView;
    List<Food> foodList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_food_details);

        foodList = getIntent().getParcelableArrayListExtra("food_list");

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
        ArrayList<String> ingredientsImageList = intent.getStringArrayListExtra("food_ingredients_image");


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

        List<String> filteredIngredientsImageList = new ArrayList<>();
        for (int i = 0; i < ingredientList.size(); i++) {
            if (i < ingredientsImageList.size()) {
                filteredIngredientsImageList.add(ingredientsImageList.get(i));
            } else {
                filteredIngredientsImageList.add(null);
            }
        }

        IngredientsAdapter adapter = new IngredientsAdapter(ingredientList, measurementList, filteredIngredientsImageList, new IngredientsAdapter.OnIngredientClickListener() {
            @Override
            public void onIngredientClick(String ingredient) {
                openFoodDetailsForIngredient(ingredient);
            }
        });
        ingredientsRecyclerView.setAdapter(adapter);
        recipe_textView.setText(foodRecipe);

        ImageView BtnBack = findViewById(R.id.button_details_back);

        BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void toggleRecipeLayout() {
        if (recipeLayout.getVisibility() == View.VISIBLE) {
            recipeLayout.setVisibility(View.GONE);
        } else {
            recipeLayout.setVisibility(View.VISIBLE);
        }
    }
    private boolean isIngredientClickInProgress = false;
    private void openFoodDetailsForIngredient(String ingredient) {
        if (isIngredientClickInProgress) {
            return;
        }

        isIngredientClickInProgress = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isIngredientClickInProgress = false;
            }
        }, 500);

        boolean foundFood = false;
        for (Food food : foodList) {
            if (food.getName().equalsIgnoreCase(ingredient)) {
                foundFood = true;
                // Update the activity with the new food details
                TextView foodNameTextView = findViewById(R.id.textView_food_name);
                TextView foodCategoryTextView = findViewById(R.id.textView_food_category);
                TextView foodTypeTextView = findViewById(R.id.textView_food_type);
                TextView foodCaloriesTextView = findViewById(R.id.textView_food_calories);
                TextView foodBenefitTextView = findViewById(R.id.textView_food_benefit);
                TextView foodDescriptionTextView = findViewById(R.id.textView_food_description);
                ImageView foodImageView = findViewById(R.id.foodDetail_imageView);

                foodNameTextView.setText(food.getName());
                foodCategoryTextView.setText(food.getCategory());
                foodTypeTextView.setText(food.getType());
                foodCaloriesTextView.setText(String.valueOf(food.getCalories()));
                foodBenefitTextView.setText(food.getBenefit());
                foodDescriptionTextView.setText(food.getDescription());

                String foodImage = food.getImage();
                if (foodImage != null) {
                    int resourceId = getResources().getIdentifier(foodImage, "drawable", getPackageName());
                    if (resourceId != 0) {
                        Drawable foodImageDrawable = ContextCompat.getDrawable(this, resourceId);
                        foodImageView.setImageDrawable(foodImageDrawable);
                    } else {
                        foodImageView.setImageResource(R.drawable.no_image);
                    }
                } else {
                    foodImageView.setImageResource(R.drawable.no_image);
                }

                List<String> ingredientList = food.getIngredients();
                List<String> measurementList = food.getMeasurements();
                List<String> ingredientsImageList = food.getIngredientsImage();

                List<String> filteredIngredientsImageList = new ArrayList<>();
                for (int i = 0; i < ingredientList.size(); i++) {
                    if (i < ingredientsImageList.size()) {
                        filteredIngredientsImageList.add(ingredientsImageList.get(i));
                    } else {
                        filteredIngredientsImageList.add(null);
                    }
                }

                IngredientsAdapter adapter = new IngredientsAdapter(ingredientList, measurementList, filteredIngredientsImageList, new IngredientsAdapter.OnIngredientClickListener() {
                    @Override
                    public void onIngredientClick(String ingredient) {
                        openFoodDetailsForIngredient(ingredient);
                    }
                });
                ingredientsRecyclerView.setAdapter(adapter);
                recipe_textView.setText(food.getRecipe());

                break;
            }
        }
        if (!foundFood){
            Toast.makeText(this, "No food details for selected food/ingredient", Toast.LENGTH_SHORT).show();
        }
    }

    private void openFoodDetailsLayout(Food food) {
        Intent intent = new Intent(FoodDetailsActivity.this, FoodDetailsActivity.class);
        intent.putExtra("food_name", food.getName());
        intent.putExtra("food_recipe", food.getRecipe());
        intent.putExtra("food_image", food.getImage());
        intent.putExtra("food_description", food.getDescription());
        intent.putExtra("food_category", food.getCategory());
        intent.putExtra("food_type", food.getType());
        intent.putExtra("food_calories", food.getCalories());
        intent.putExtra("food_benefit", food.getBenefit());
        intent.putExtra("food_ingredients", new ArrayList<>(food.getIngredients()));
        intent.putExtra("food_measurements", new ArrayList<>(food.getMeasurements()));
        intent.putExtra("food_ingredients_image", new ArrayList<>(food.getIngredientsImage()));
        startActivity(intent);
    }

}
