package com.example.signuploginfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class UserProfile extends AppCompatActivity {

    TextView fullName, email, age, height, weight, sickness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_revision_draft);

        fullName = findViewById(R.id.fullName_profile);

        email = findViewById(R.id.email_profile);
        age = findViewById(R.id.age_profile);
        height = findViewById(R.id.height_profile);
        weight = findViewById(R.id.weight_profile);
        sickness = findViewById(R.id.sickness_profile);

        showAllUserData();

        ImageView BtnBack = findViewById(R.id.button_profile_to_categories);

        BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfile.this, CategoriesActivity.class);
                startActivity(intent);
            }
        });

    }

    private void showAllUserData() {

        Intent intent = getIntent();
        String profile_name = intent.getStringExtra("userName");
        String profile_email = intent.getStringExtra("userEmail");
        String profile_age = intent.getStringExtra("age");
        String profile_height = intent.getStringExtra("height");
        String profile_weight = intent.getStringExtra("weight");
        String profile_sickness = intent.getStringExtra("sickness");

        fullName.setText(profile_name);
        email.setText(profile_email);
        age.setText(profile_age);
        height.setText(profile_height);
        weight.setText(profile_weight);
        sickness.setText(profile_sickness);

    }
}