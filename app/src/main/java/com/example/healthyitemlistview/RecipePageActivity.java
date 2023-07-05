package com.example.healthyitemlistview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipePageActivity extends AppCompatActivity {

    private TextView textViewRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_page);

        textViewRecipe = findViewById(R.id.textView_recipe_scrollview);

        Intent intent = getIntent();
        String recipe = intent.getStringExtra("recipe");
        textViewRecipe.setText(recipe);
    }
}

