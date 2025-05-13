package com.example.supernovaapp;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class GameView4 extends AppCompatActivity {

    private VideoView videoView;
    private ImageButton playBtn;
    private ImageButton pauseBtn;
    private ImageButton muteBtn;
    private ImageButton unmuteBtn;
    private ImageButton likeBtn;
    private ImageButton wishBtn;
    private Button addToCartBtn;

    private boolean isPlaying = false;
    private boolean isMuted = false;
    private boolean isLiked = false;
    private boolean isWished = false;
    private boolean isAddedToCart = false;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_view4);

        videoView = findViewById(R.id.video_view);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.phantomblade);
        videoView.setVideoURI(uri);

        playBtn = findViewById(R.id.playBtn);
        pauseBtn = findViewById(R.id.pauseBtn);
        muteBtn = findViewById(R.id.muteBtn);
        unmuteBtn = findViewById(R.id.unmuteBtn);

        likeBtn = findViewById(R.id.like_button);
        wishBtn = findViewById(R.id.wish_button);
        addToCartBtn = findViewById(R.id.addtocart);

        // Play Button
        playBtn.setOnClickListener(v -> {
            videoView.start();
            playBtn.setVisibility(View.INVISIBLE);
            pauseBtn.setVisibility(View.VISIBLE);
            isPlaying = true;
        });

        // Pause Button
        pauseBtn.setOnClickListener(v -> {
            videoView.pause();
            playBtn.setVisibility(View.VISIBLE);
            pauseBtn.setVisibility(View.INVISIBLE);
            isPlaying = false;
        });

        // Mute/Unmute
        muteBtn.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                mediaPlayer.setVolume(0f, 0f);
                muteBtn.setVisibility(View.INVISIBLE);
                unmuteBtn.setVisibility(View.VISIBLE);
                isMuted = true;
            }
        });

        unmuteBtn.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                mediaPlayer.setVolume(1f, 1f);
                muteBtn.setVisibility(View.VISIBLE);
                unmuteBtn.setVisibility(View.INVISIBLE);
                isMuted = false;
            }
        });

        videoView.setOnPreparedListener(mp -> {
            mediaPlayer = mp;
            if (isMuted) {
                mediaPlayer.setVolume(0f, 0f);
            }
        });

        // Like Button Toggle
        likeBtn.setOnClickListener(v -> {
            isLiked = !isLiked;
            likeBtn.setImageResource(isLiked ? R.drawable.like_shaded : R.drawable.like);
        });

        // Wish Button Toggle
        wishBtn.setOnClickListener(v -> {
            isWished = !isWished;
            wishBtn.setImageResource(isWished ? R.drawable.bookmarked : R.drawable.bookmark);
        });

        // Add to Cart Toggle
        addToCartBtn.setOnClickListener(v -> {
            if (!isAddedToCart) {
                // Remove text and set the check icon
                addToCartBtn.setText("Added to Cart"); // Remove text
                addToCartBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0); // Clear any previous icon
            } else {
                // Reset to default state with text
                addToCartBtn.setText("Add to Cart");
                addToCartBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0); // Clear icon
            }
            isAddedToCart = !isAddedToCart;
        });


        // Start video on launch
        videoView.start();
        isPlaying = true;
        playBtn.setVisibility(View.INVISIBLE);
        pauseBtn.setVisibility(View.VISIBLE);
        muteBtn.setVisibility(View.VISIBLE);
        unmuteBtn.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoView.isPlaying()) {
            videoView.pause();
            playBtn.setVisibility(View.VISIBLE);
            pauseBtn.setVisibility(View.INVISIBLE);
            isPlaying = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.stopPlayback();
    }
}