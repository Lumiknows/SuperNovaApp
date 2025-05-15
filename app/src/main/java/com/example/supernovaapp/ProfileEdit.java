package com.example.supernovaapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileEdit extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView backButton;
    private ImageView profileMenu;
    private CircleImageView profilePhoto;
    private EditText username;
    private Uri selectedImageUri = null;

    private DBHelper dbHelper;
    private int userId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit);

        backButton = findViewById(R.id.back_button);
        profileMenu = findViewById(R.id.profile_menu);
        profilePhoto = findViewById(R.id.profile_photo);
        username = findViewById(R.id.username);
        Button cancelButton = findViewById(R.id.cancel_button);
        Button saveButton = findViewById(R.id.save_button);

        username.setEnabled(true);
        username.setFocusableInTouchMode(true);

        dbHelper = new DBHelper(this);

        // Get userId passed from ProfilePage
        userId = getIntent().getIntExtra("userId", -1);

        // Load current username from DB if userId valid
        if (userId != -1) {
            String currentUsername = dbHelper.getUsernameById(userId);
            if (currentUsername != null) {
                username.setText(currentUsername);
            }
        }

        cancelButton.setOnClickListener(v -> finish());

        backButton.setOnClickListener(v -> saveAndReturn());

        saveButton.setOnClickListener(v -> saveAndReturn());

        profileMenu.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(this, view);
            popupMenu.getMenuInflater().inflate(R.menu.profile_popup_menu, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if (id == R.id.logout) {
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                    return true;
                } else if (id == R.id.setting) {
                    Toast.makeText(this, "Setting clicked", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            });
            popupMenu.show();
        });

        profilePhoto.setOnClickListener(v -> openImageChooser());
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void saveAndReturn() {
        String newUsername = username.getText().toString().trim();
        if (newUsername.isEmpty()) {
            Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update username in DB
        if (userId != -1) {
            boolean updated = dbHelper.updateUsername(userId, newUsername);
            if (!updated) {
                Toast.makeText(this, "Failed to update username", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("username", newUsername);

        if (selectedImageUri != null) {
            resultIntent.putExtra("imageUri", selectedImageUri.toString());
        }

        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            profilePhoto.setImageURI(selectedImageUri);
        }
    }
}
