package com.example.supernovaapp;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfilePage extends AppCompatActivity {

    private ImageView backButton;  // Declare back button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);  // Make sure profile_page.xml is correct

        // Initialize the back button
        backButton = findViewById(R.id.back_button);  // Ensure this ID is correct in XML

        // Set the back button to close the activity when clicked
        backButton.setOnClickListener(v -> finish());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();  // This will perform the default back button behavior
    }
}
