package com.example.supernovaapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfilePage extends AppCompatActivity {

    private ImageView backButton;  // Declare back button
    private ImageView profileMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);  // Make sure profile_page.xml is correct

        // Initialize the back button
        backButton = findViewById(R.id.back_button);  // Ensure this ID is correct in XML

        // Set the back button to close the activity when clicked
        backButton.setOnClickListener(v -> finish());

        // Porfile Menu
        profileMenu = findViewById(R.id.profile_menu);

        profileMenu.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(this, view);
            popupMenu.getMenuInflater().inflate(R.menu.profile_popup_menu, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();

                if (id == R.id.logout) {
                    startActivity(new Intent(this, LoginActivity.class));
                    return true;
                }
                else if (id == R.id.setting) {
                    Toast.makeText(this, "Setting clicked", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            });
            popupMenu.show();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();  // This will perform the default back button behavior
    }
}
