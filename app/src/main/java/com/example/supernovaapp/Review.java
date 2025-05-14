package com.example.supernovaapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Review extends AppCompatActivity {

    private EditText reviewText;
    private TextView submittedReview, gameTitle, gameStudio, gameHrs;
    private Button cancelBtn, reviewBtn;
    private SharedPreferences sharedPreferences;
    private ImageView gameImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review);  // This should point to your updated XML layout

        // Initialize views
        reviewText = findViewById(R.id.reviewText);
        submittedReview = findViewById(R.id.submittedReview);
        cancelBtn = findViewById(R.id.cancel);
        reviewBtn = findViewById(R.id.reviewed);
        gameImageView = findViewById(R.id.reviewGameImage);
        gameTitle = findViewById(R.id.reviewGameTitle);
        gameStudio = findViewById(R.id.reviewGameStudio);
        gameHrs = findViewById(R.id.reviewGameHrs);

        // Get data from Intent
        String title = getIntent().getStringExtra("gameTitle");
        String hours = getIntent().getStringExtra("gameHrs");
        String studio = getIntent().getStringExtra("gameStudio");
        int imageRes = getIntent().getIntExtra("gameImage", R.drawable.ready_or_not);  // Default image if not passed

        // Set the received data to the corresponding views
        gameTitle.setText(title);
        gameStudio.setText(studio);
        gameHrs.setText(hours);
        gameImageView.setImageResource(imageRes);

        // Initialize SharedPreferences to save the review
        sharedPreferences = getSharedPreferences("GameReviewPrefs", MODE_PRIVATE);

        // Use the game title as the key to store the review in SharedPreferences
        String reviewKey = "review_" + title;  // Unique key for each game based on the title

        // Load the saved review for the specific game if it exists
        String savedReview = sharedPreferences.getString(reviewKey, null);
        if (savedReview != null) {
            // If a review is already saved, display it
            submittedReview.setText(savedReview);
            submittedReview.setVisibility(View.VISIBLE);
        }

        // Set up Cancel button click listener
        cancelBtn.setOnClickListener(v -> finish());  // Close the activity and go back

        // Set up Review button click listener
        reviewBtn.setOnClickListener(v -> {
            // Get the entered review text
            String userReview = reviewText.getText().toString().trim();
            if (userReview.isEmpty()) {
                Toast.makeText(Review.this, "Please write a review before submitting!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Show the submitted review in the TextView above the EditText
            submittedReview.setText(userReview);
            submittedReview.setVisibility(View.VISIBLE);

            // Clear the EditText
            reviewText.setText("");

            // Save the review to SharedPreferences with the unique key
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(reviewKey, userReview);  // Save the entered review with the game title as key
            editor.apply();

            // Show a Toast to confirm submission
            Toast.makeText(Review.this, "Review submitted!", Toast.LENGTH_SHORT).show();

            // Close the review activity after submitting the review
            finish();
        });
    }
}
