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
    private TextView submittedReview, gameTitle, gameStudio, gameHrs, reviewUsername;
    private Button cancelBtn, reviewBtn;
    private SharedPreferences sharedPreferences;
    private ImageView gameImageView;

    private DBHelper db;
    private int userId;
    private String username;

    public static LibraryFragment newInstance(int userId, String username) {
        LibraryFragment fragment = new LibraryFragment();
        Bundle args = new Bundle();
        args.putInt("userId", userId);
        args.putString("username", username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review);

        // Initialize DB
        db = new DBHelper(this);

        // Initialize views
        reviewText = findViewById(R.id.reviewText);
        submittedReview = findViewById(R.id.submittedReview);
        cancelBtn = findViewById(R.id.cancel);
        reviewBtn = findViewById(R.id.reviewed);
        gameImageView = findViewById(R.id.reviewGameImage);
        gameTitle = findViewById(R.id.reviewGameTitle);
        gameStudio = findViewById(R.id.reviewGameStudio);
        gameHrs = findViewById(R.id.reviewGameHrs);
        reviewUsername = findViewById(R.id.reviewerName);

        // Get data from Intent
        String title = getIntent().getStringExtra("gameTitle");
        String hours = getIntent().getStringExtra("gameHrs");
        String studio = getIntent().getStringExtra("gameStudio");
        int imageRes = getIntent().getIntExtra("gameImage", R.drawable.ready_or_not);
        userId = getIntent().getIntExtra("userId", -1);
        username = getIntent().getStringExtra("username");

        // Refresh username from DB in case it's changed
        if (userId != -1) {
            String updatedUsername = db.getUsernameById(userId);
            if (updatedUsername != null && !updatedUsername.isEmpty()) {
                username = updatedUsername;
            }
        }

        // Set the received data
        gameTitle.setText(title);
        gameStudio.setText(studio);
        gameHrs.setText(hours);
        gameImageView.setImageResource(imageRes);
        reviewUsername.setText(username + "'s Review");

        // SharedPreferences for review storage
        sharedPreferences = getSharedPreferences("GameReviewPrefs", MODE_PRIVATE);
        String reviewKey = "review_" + title;

        String savedReview = sharedPreferences.getString(reviewKey, null);
        if (savedReview != null) {
            submittedReview.setText(savedReview);
            submittedReview.setVisibility(View.VISIBLE);
        }

        cancelBtn.setOnClickListener(v -> finish());

        reviewBtn.setOnClickListener(v -> {
            String userReview = reviewText.getText().toString().trim();
            if (userReview.isEmpty()) {
                Toast.makeText(Review.this, "Please write a review before submitting!", Toast.LENGTH_SHORT).show();
                return;
            }

            submittedReview.setText(userReview);
            submittedReview.setVisibility(View.VISIBLE);
            reviewText.setText("");

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(reviewKey, userReview);
            editor.apply();

            Toast.makeText(Review.this, "Review submitted!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
