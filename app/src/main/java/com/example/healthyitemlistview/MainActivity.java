package com.example.healthyitemlistview;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import java.util.List;

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

        // Load and parse the JSON file
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
                    String foodCategory = foodObject.getString("food category");

                    Food food = new Food(foodName, foodRecipe, foodCategory);
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

    private class Food {
        private String name;
        private String recipe;
        private String category;

        public Food(String name, String recipe, String category) {
            this.name = name;
            this.recipe = recipe;
            this.category = category;
        }

        public String getName() {
            return name;
        }

        public String getRecipe() {
            return recipe;
        }

        public String getCategory() {
            return category;
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
            TextView foodRecipeTextView = convertView.findViewById(R.id.foodRecipeTextView);
            TextView foodCategoryTextView = convertView.findViewById(R.id.foodCategoryTextView);

            foodNameTextView.setText(food.getName());
            foodRecipeTextView.setText(food.getRecipe());
            foodCategoryTextView.setText(food.getCategory());

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
        intent.putExtra("food_category", food.getCategory());
        startActivity(intent);
    }
}







