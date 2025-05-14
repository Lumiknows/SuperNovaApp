package com.example.supernovaapp;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class GameView3 extends AppCompatActivity {

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
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_view3);

        db = new DBHelper(this);

        videoView = findViewById(R.id.video_view);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.residentevil);
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
                String title = ((TextView) findViewById(R.id.title)).getText().toString();
                String studio = ((TextView) findViewById(R.id.studio)).getText().toString();
                String price = ((TextView) findViewById(R.id.price)).getText().toString();
                String discount = "-";
                int imageResId = R.drawable.residentevil;

                int userId = getIntent().getIntExtra("userId", -1);
                Log.e("USER_ID_CHECK", "userId: " + userId);

                boolean success = db.insertToCart(userId, title, studio, price, discount, imageResId);
                if (success) {
                    Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show();
                    addToCartBtn.setText("Added to Cart"); // Remove text
                    addToCartBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0); // Clear any previous icon
                }
                else {
                    Toast.makeText(this, "Failed to add item", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Item already in cart", Toast.LENGTH_SHORT).show();
            }
            isAddedToCart = true;
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