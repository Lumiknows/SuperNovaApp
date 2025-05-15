package com.example.supernovaapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileEdit extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 2001;

    private CircleImageView profilePhotoEdit;
    private EditText usernameEdit;
    private EditText bioEdit;
    private Button saveButton;
    private Button cancelButton;

    private Uri selectedImageUri = null;
    private int userId = -1;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit);

        dbHelper = new DBHelper(this);

        profilePhotoEdit = findViewById(R.id.profile_photo);
        usernameEdit = findViewById(R.id.username);
        bioEdit = findViewById(R.id.bio_edit);
        saveButton = findViewById(R.id.save_button);
        cancelButton = findViewById(R.id.cancel_button);

        userId = getIntent().getIntExtra("userId", -1);
        if (userId == -1) {
            finish(); // no valid userId, close activity
            return;
        }

        loadUserInfo();

        profilePhotoEdit.setOnClickListener(v -> {
            // Open gallery to pick an image with persistent permission
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), PICK_IMAGE_REQUEST);
        });

        saveButton.setOnClickListener(v -> saveChanges());

        cancelButton.setOnClickListener(v -> finish());
    }

    private void loadUserInfo() {
        // Load username
        String username = dbHelper.getUsernameById(userId);
        if (username != null) {
            usernameEdit.setText(username);
        }

        // Load bio from DB (you can also load from SharedPreferences if you want)
        String bio = dbHelper.getBioById(userId);
        if (bio != null) {
            bioEdit.setText(bio);
        }

        // Load profile image URI from DB
        String imageUriString = dbHelper.getProfilePicUriById(userId);
        if (imageUriString != null && !imageUriString.isEmpty()) {
            Uri imageUri = Uri.parse(imageUriString);
            profilePhotoEdit.setImageURI(imageUri);
        } else {
            profilePhotoEdit.setImageResource(R.drawable.profileavatar);
        }
    }

    private void saveChanges() {
        String newUsername = usernameEdit.getText().toString().trim();
        String newBio = bioEdit.getText().toString().trim();

        // Update username in DB
        if (!newUsername.isEmpty()) {
            dbHelper.updateUsername(userId, newUsername);
        }

        // Update bio in DB
        dbHelper.updateBio(userId, newBio);

        // Update profile picture URI in DB if changed
        String imageUriString = null;
        if (selectedImageUri != null) {
            imageUriString = selectedImageUri.toString();
            dbHelper.updateProfileImageUri(userId, imageUriString);
        }

        // Return updated data to ProfilePage
        Intent resultIntent = new Intent();
        resultIntent.putExtra("username", newUsername);
        resultIntent.putExtra("bio", newBio);
        if (imageUriString != null) {
            resultIntent.putExtra("imageUri", imageUriString);
        }

        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();

            // Persist permission for the URI
            final int takeFlags = data.getFlags() &
                    (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            getContentResolver().takePersistableUriPermission(selectedImageUri, takeFlags);

            profilePhotoEdit.setImageURI(selectedImageUri);
        }
    }
}
