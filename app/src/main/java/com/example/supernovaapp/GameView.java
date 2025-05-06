package com.example.supernovaapp;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class GameView extends AppCompatActivity {

    private VideoView videoView;
    private ImageButton playBtn;  // Play Button
    private ImageButton pauseBtn;  // Pause Button
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_view);  // Make sure your layout is set

        // Initialize VideoView
        videoView = findViewById(R.id.video_view);

        // Set the video URI
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.wukong);  // Replace with your video name
        videoView.setVideoURI(uri);

        // Initialize Play/Pause Buttons
        playBtn = findViewById(R.id.playBtn);
        pauseBtn = findViewById(R.id.pauseBtn);

        // Set Play Button functionality
        playBtn.setOnClickListener(v -> {
            // Play the video
            videoView.start();
            playBtn.setVisibility(View.INVISIBLE);  // Hide Play Button
            pauseBtn.setVisibility(View.VISIBLE);  // Show Pause Button
            isPlaying = true;
        });

        // Set Pause Button functionality
        pauseBtn.setOnClickListener(v -> {
            // Pause the video
            videoView.pause();
            playBtn.setVisibility(View.VISIBLE);  // Show Play Button
            pauseBtn.setVisibility(View.INVISIBLE);  // Hide Pause Button
            isPlaying = false;
        });

        // Start the video (and show Pause Button initially)
        videoView.start();
        isPlaying = true;
        playBtn.setVisibility(View.INVISIBLE);  // Hide Play Button initially
        pauseBtn.setVisibility(View.VISIBLE);  // Show Pause Button initially
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Pause the video when the activity is paused
        if (videoView.isPlaying()) {
            videoView.pause();
            playBtn.setVisibility(View.VISIBLE);  // Show Play Button
            pauseBtn.setVisibility(View.INVISIBLE);  // Hide Pause Button
            isPlaying = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release the video resources when the activity is destroyed
        videoView.stopPlayback();
    }
}
