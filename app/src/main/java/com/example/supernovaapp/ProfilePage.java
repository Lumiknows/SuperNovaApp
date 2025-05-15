package com.example.supernovaapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilePage extends AppCompatActivity {

    private static final String TAG = "ProfilePage";
    private static final int EDIT_PROFILE_REQUEST = 1001;

    private ImageView backButton;
    private ImageView profileMenu;
    private CircleImageView profilePhoto;
    private TextView usernameText;

    private DBHelper dbHelper;
    private int userId = -1;  // Will be set from intent extras

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        dbHelper = new DBHelper(this);

        backButton = findViewById(R.id.back_button);
        profileMenu = findViewById(R.id.profile_menu);
        profilePhoto = findViewById(R.id.profile_photo);
        usernameText = findViewById(R.id.username);

        backButton.setOnClickListener(v -> finish());

        // Get userId from Intent extras
        userId = getIntent().getIntExtra("userId", -1);
        Log.d(TAG, "Received userId: " + userId);

        if (userId == -1) {
            Toast.makeText(this, "Error: User not logged in", Toast.LENGTH_SHORT).show();
            // Optionally redirect to login screen here
            finish();
            return;
        }

        loadUsername();

        profileMenu.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(this, view);
            popupMenu.getMenuInflater().inflate(R.menu.profile_popup_menu, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if (id == R.id.logout) {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (id == R.id.setting) {
                    Toast.makeText(this, "Setting clicked", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.editprofile) {
                    Intent intent = new Intent(this, ProfileEdit.class);
                    intent.putExtra("userId", userId);
                    startActivityForResult(intent, EDIT_PROFILE_REQUEST);
                    return true;
                }
                return false;
            });
            popupMenu.show();
        });

        // Also open edit when clicking photo or username
        profilePhoto.setOnClickListener(v -> openEditPage());
        usernameText.setOnClickListener(v -> openEditPage());
    }

    private void openEditPage() {
        Intent intent = new Intent(this, ProfileEdit.class);
        intent.putExtra("userId", userId);
        startActivityForResult(intent, EDIT_PROFILE_REQUEST);
    }

    private void loadUsername() {
        String username = dbHelper.getUsernameById(userId);
        Log.d(TAG, "Loaded username: " + username);
        if (username != null && !username.isEmpty()) {
            usernameText.setText(username);
        } else {
            usernameText.setText("JamezSunz");  // fallback default
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_PROFILE_REQUEST && resultCode == RESULT_OK && data != null) {
            String newUsername = data.getStringExtra("username");
            String imageUriString = data.getStringExtra("imageUri");

            if (newUsername != null && !newUsername.isEmpty()) {
                usernameText.setText(newUsername);
            }

            if (imageUriString != null) {
                Uri imageUri = Uri.parse(imageUriString);
                profilePhoto.setImageURI(imageUri);
            }
        }
    }
}
