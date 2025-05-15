package com.example.supernovaapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilePage extends AppCompatActivity {

    private static final int EDIT_PROFILE_REQUEST = 1001;

    private ImageView backButton;
    private ImageView profileMenu;
    private CircleImageView profilePhoto;
    private TextView usernameText;
    private TextView bioText;

    private DBHelper dbHelper;
    private int userId = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        dbHelper = new DBHelper(this);

        backButton = findViewById(R.id.back_button);
        profileMenu = findViewById(R.id.profile_menu);
        profilePhoto = findViewById(R.id.profile_photo);
        usernameText = findViewById(R.id.username);
        bioText = findViewById(R.id.bio_text);

        backButton.setOnClickListener(v -> finish());

        userId = getIntent().getIntExtra("userId", -1);
        if (userId == -1) {
            Toast.makeText(this, "Error: User not logged in", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadUserInfo();

        profileMenu.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(this, view);
            popupMenu.getMenuInflater().inflate(R.menu.profile_popup_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.logout) {
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                    return true;
                } else if (item.getItemId() == R.id.setting) {
                    Toast.makeText(this, "Setting clicked", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (item.getItemId() == R.id.editprofile) {
                    openEditPage();
                    return true;
                }
                return false;
            });
            popupMenu.show();
        });

        profilePhoto.setOnClickListener(v -> openEditPage());
        usernameText.setOnClickListener(v -> openEditPage());
        bioText.setOnClickListener(v -> openEditPage());
    }

    private void openEditPage() {
        Intent intent = new Intent(this, ProfileEdit.class);
        intent.putExtra("userId", userId);
        startActivityForResult(intent, EDIT_PROFILE_REQUEST);
    }

    private void loadUserInfo() {
        // Load username from DB
        String username = dbHelper.getUsernameById(userId);
        if (username != null && !username.isEmpty()) {
            usernameText.setText(username);
        } else {
            usernameText.setText("JamezSunz"); // default
        }

        // Load profile image URI from DB
        String imageUriString = dbHelper.getProfilePicUriById(userId);
        if (imageUriString != null && !imageUriString.isEmpty()) {
            Uri imageUri = Uri.parse(imageUriString);
            profilePhoto.setImageURI(imageUri);
        } else {
            profilePhoto.setImageResource(R.drawable.profileavatar); // default avatar
        }

        // Load bio from DB
        String bio = dbHelper.getBioById(userId);
        if (bio != null && !bio.isEmpty()) {
            bioText.setText(bio);
        } else {
            bioText.setText("No information");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_PROFILE_REQUEST && resultCode == RESULT_OK && data != null) {
            String newUsername = data.getStringExtra("username");
            String newBio = data.getStringExtra("bio");
            String imageUriString = data.getStringExtra("imageUri");

            if (newUsername != null && !newUsername.isEmpty()) {
                usernameText.setText(newUsername);
                dbHelper.updateUsername(userId, newUsername);
            }

            if (newBio != null) {
                bioText.setText(newBio);
                dbHelper.updateBio(userId, newBio);
            }

            if (imageUriString != null && !imageUriString.isEmpty()) {
                Uri imageUri = Uri.parse(imageUriString);
                profilePhoto.setImageURI(imageUri);
                dbHelper.updateProfileImageUri(userId, imageUriString);
            }
        }
    }
}
