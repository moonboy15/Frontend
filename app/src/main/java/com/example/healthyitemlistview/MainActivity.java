package com.example.healthyitemlistview;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ListView foodListView;
    private List<Food> foodList;
    private List<Food> filteredFoodList;
    private FoodAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                    String foodDescription = foodObject.getString("food description");
                    String foodCategory = foodObject.getString("food category");
                    String foodType = foodObject.getString("food type");
                    int foodCalories = foodObject.getInt("food calories");
                    String foodBenefit = foodObject.getString("food benefit");

                    List<String> ingredients = new ArrayList<>();
                    List<String> measurements = new ArrayList<>();

                    for (int j = 1; j <= 3; j++){
                        String ingredientKey = "ingredient " + j;
                        String measurementKey = "measurement " + j;

                        if (foodObject.has(ingredientKey) && foodObject.has(measurementKey)) {
                            String ingredient = foodObject.getString(ingredientKey);
                            String measurement = foodObject.getString(measurementKey);

                            ingredients.add(ingredient);
                            measurements.add(measurement);
                        }
                    }

                    Food food = new Food(foodName, foodRecipe, foodDescription, foodCategory, foodType, foodCalories,foodBenefit, ingredients, measurements);
                    foodList.add(food);
                }

                filteredFoodList = new ArrayList<>(foodList);

                adapter = new FoodAdapter(filteredFoodList);
                foodListView.setAdapter(adapter);
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
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("healthyfood.json");

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

    private class Food {
        private String name;
        private String recipe;
        private String description;
        private String category;
        private String type;
        private int calories;
        private String benefit;
        private List<String> ingredients;
        private List<String> measurements;


        public Food(String name, String recipe, String description, String category, String type, int calories, String benefit, List<String> ingredients, List<String> measurements) {
            this.name = name;
            this.recipe = recipe;
            this.description = description;
            this.category = category;
            this.type = type;
            this.calories = calories;
            this.benefit = benefit;
            this.ingredients = ingredients;
            this.measurements = measurements;
        }

        public void setIngredients(List<String> ingredients) {
            this.ingredients = ingredients;
        }

        public void setMeasurements(List<String> measurements) {
            this.measurements = measurements;
        }

        public String getName() {
            return name;
        }

        public String getRecipe() {
            return recipe;
        }

        public String getDescription() {
            return description;
        }

        public String getCategory() {
            return category;
        }

        public String getType() {
            return type;
        }

        public int getCalories() {
            return calories;
        }

        public String getBenefit() {
            return benefit;
        }

        public List<String> getIngredients() {
            return ingredients;
        }

        public List<String> getMeasurements() {
            return measurements;
        }

    }

    private class FoodAdapter extends ArrayAdapter<Food> {

        public FoodAdapter(List<Food> foodList) {
            super(MainActivity.this, 0, foodList);
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

    private void openFoodDetailsLayout(Food food) {
        Intent intent = new Intent(MainActivity.this, FoodDetailsActivity.class);
        intent.putExtra("food_name", food.getName());
        intent.putExtra("food_recipe", food.getRecipe());
        intent.putExtra("food_description", food.getDescription());
        intent.putExtra("food_category", food.getCategory());
        intent.putExtra("food_type", food.getType());
        intent.putExtra("food_calories", food.getCalories());
        intent.putExtra("food_benefit", food.getBenefit());
        intent.putExtra("food_ingredients", new ArrayList<>(food.getIngredients()));
        intent.putExtra("food_measurements", new ArrayList<>(food.getMeasurements()));
        startActivity(intent);
    }
}








