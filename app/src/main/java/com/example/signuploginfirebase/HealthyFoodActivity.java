package com.example.signuploginfirebase;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HealthyFoodActivity extends AppCompatActivity {

    private static final String TAG = HealthyFoodActivity.class.getSimpleName();

    private ListView foodListView;
    private List<Food> foodList;
    private List<Food> filteredFoodList;
    private FoodAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthy_main);

        foodListView = findViewById(R.id.foodListView);

        String json = loadJSONFromAsset();
        if (json != null) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray healthyArray = jsonObject.getJSONArray("healthy");

                foodList = new ArrayList<>();
                for (int i = 0; i < healthyArray.length(); i++) {
                    JSONObject foodObject = healthyArray.getJSONObject(i);
                    String foodName = foodObject.getString("food name");
                    String foodRecipe = foodObject.getString("food recipe");
                    String foodImage = foodObject.getString("food image");
                    String foodDescription = foodObject.getString("food description");
                    String foodCategory = foodObject.getString("food category");
                    String foodType = foodObject.getString("food type");
                    int foodCalories = foodObject.getInt("food calories");
                    String foodBenefit = foodObject.getString("food benefit");

                    List<String> ingredients = new ArrayList<>();
                    List<String> measurements = new ArrayList<>();
                    List<String> ingredientsImageList = new ArrayList<>();

                    for (int j = 1; j <= 20; j++){
                        String ingredientKey = "ingredient " + j;
                        String measurementKey = "measurement " + j;
                        String ingredientImageKey = "ingredient image "+ j;

                        if (foodObject.has(ingredientKey) && foodObject.has(measurementKey) && foodObject.has(ingredientImageKey)) {
                            String ingredient = foodObject.getString(ingredientKey);
                            String measurement = foodObject.getString(measurementKey);
                            String ingredientImage = foodObject.getString(ingredientImageKey);

                            ingredients.add(ingredient);
                            measurements.add(measurement);
                            ingredientsImageList.add(ingredientImage);
                        }
                    }

                    Food food = new Food(foodName, foodRecipe, foodDescription, foodImage, foodCategory, foodType, foodCalories,foodBenefit, ingredients, measurements, ingredientsImageList);
                    foodList.add(food);
                    food.setImage(foodImage);
                }
                Spinner spinnerTags = findViewById(R.id.spinner_tags);
                ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.food_benefits, android.R.layout.simple_spinner_item);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerTags.setAdapter(spinnerAdapter);
                spinnerTags.setSelection(spinnerAdapter.getPosition("All"));

                spinnerTags.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedBenefit = parent.getItemAtPosition(position).toString();
                        filterFoodByBenefit(selectedBenefit);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                filteredFoodList = new ArrayList<>(foodList);

                adapter = new FoodAdapter(filteredFoodList);
                foodListView.setAdapter(adapter);

                foodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Food clickedFood = filteredFoodList.get(position);
                        openFoodDetailsLayout(clickedFood);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        androidx.appcompat.widget.SearchView searchView = findViewById(R.id.searchEditText);
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterFoodList(newText);
                return true;
            }
        });

        Button buttonDish = findViewById(R.id.button_dish);
        Button buttonDrink = findViewById(R.id.button_drink);
        Button buttonIngredient = findViewById(R.id.button_ingredient);
        Button buttonSnack = findViewById(R.id.button_snack);
        Button buttonMisc = findViewById(R.id.button_misc);

        buttonDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterFoodByType("Dish");
            }
        });

        buttonDrink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {filterFoodByType("Drink");}
        });

        buttonIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterFoodByType("Ingredient");
            }
        });

        buttonSnack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterFoodByType("Snack");
            }
        });

        buttonMisc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterFoodByType("Misc.");
            }
        });

        ImageView BtnBack = findViewById(R.id.button_list_to_categories);

        BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("healthyfood 6.json");

            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    private void filterFoodList(String query) {
        filteredFoodList.clear();

        if (TextUtils.isEmpty(query)) {
            filteredFoodList.addAll(foodList);
        } else {
            query = query.toLowerCase();
            for (Food food : foodList) {
                if (food.getName().toLowerCase().contains(query)) {
                    filteredFoodList.add(food);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void filterFoodByType(String foodType){
        filteredFoodList.clear();

        for(Food food : foodList){
            if(food.getType().equalsIgnoreCase(foodType)){
                filteredFoodList.add(food);
            }
        }

        adapter.notifyDataSetChanged();
    }


    private class FoodAdapter extends ArrayAdapter<Food> {

        public FoodAdapter(List<Food> foodList) {
            super(HealthyFoodActivity.this, 0, foodList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item_food, parent, false);
            }

            Food food = getItem(position);

            TextView foodNameTextView = convertView.findViewById(R.id.foodNameTextView);
            TextView foodTypeTextView = convertView.findViewById(R.id.foodTypeTextView);
            TextView foodBenefitTextView = convertView.findViewById(R.id.foodBenefitTextView);
            ImageView foodImageView = convertView.findViewById(R.id.imageView_food);

            String resourceName = food.getImage();
            int resourceId = getResources().getIdentifier(resourceName, "drawable", getPackageName());

            if (resourceId != 0) {
                // Set the image resource
                foodImageView.setImageResource(resourceId);
            } else {
                // Set a default image resource or handle the case when the image resource is not found
                foodImageView.setImageResource(R.drawable.no_image);
            }

            foodNameTextView.setText(food.getName());
            foodTypeTextView.setText(food.getType());
            foodBenefitTextView.setText(food.getBenefit());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openFoodDetailsLayout(food);
                }
            });

            return convertView;
        }
    }

    private void filterFoodByBenefit(String benefit) {
        filteredFoodList.clear();

        if (benefit.equalsIgnoreCase("All")) {
            filteredFoodList.addAll(foodList);
        } else {
            for (Food food : foodList) {
                if (food.getBenefit().equalsIgnoreCase(benefit)) {
                    filteredFoodList.add(food);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }


    private void openFoodDetailsLayout(Food food) {
        Intent intent = new Intent(HealthyFoodActivity.this, FoodDetailsActivity.class);
        intent.putExtra("food_list", new ArrayList<>(foodList));
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
