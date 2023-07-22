package com.example.signuploginfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.BuildConfig;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UserInfoRegistration extends AppCompatActivity {

    TextInputLayout regName,regEmail, regAge, regHeight, regWeight;
    Button regBtn;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    Spinner sicknessSpinner;
    String [] diseaseOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_registration);


        regName = findViewById(R.id.name);
        regEmail = findViewById(R.id.userEmail);
        regAge = findViewById(R.id.Age);
        regHeight = findViewById(R.id.height_in_cm);
        regWeight = findViewById(R.id.weight_in_kg);
        sicknessSpinner = findViewById(R.id.sickness_spinner);
        regBtn = findViewById(R.id.button_submit);

        diseaseOptions = getResources().getStringArray(R.array.diseaseOptions);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                diseaseOptions
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sicknessSpinner.setAdapter(adapter);

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = regName.getEditText().getText().toString();
                String userEmail = regEmail.getEditText().getText().toString();
                String age = regAge.getEditText().getText().toString();
                String height = regHeight.getEditText().getText().toString();
                String weight = regWeight.getEditText().getText().toString();
                String sickness = sicknessSpinner.getSelectedItem().toString();

                UserHelper helperClass = new UserHelper(userName, userEmail, age, height, weight, sickness);

                checkDuplicateAndRegister(helperClass);
            }
        });



        ImageView BtnBack = findViewById(R.id.button_userDetails_to_signup);

        BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void checkDuplicateAndRegister(UserHelper user) {
        Query query = reference.orderByChild("userEmail").equalTo(user.getUserEmail());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean isDuplicate = dataSnapshot.exists();

                if (isDuplicate) {
                    // Handle duplicate registration (e.g., show error message)
                    Toast.makeText(UserInfoRegistration.this, "User already exists", Toast.LENGTH_SHORT).show();
                } else {
                    // Register the user since no duplicate found
                    String encodedEmail = encodeEmail(user.getUserEmail());
                    reference.child(encodedEmail).setValue(user); // Assign the encoded email as the child key

                    Toast.makeText(UserInfoRegistration.this, "Registration successful", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(UserInfoRegistration.this, LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error case
                Toast.makeText(UserInfoRegistration.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String encodeEmail(String email) {
        return email.replace(".", ",");
    }


}
