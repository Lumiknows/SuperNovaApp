package com.example.supernovaapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText currentPasswordEdit, newPasswordEdit, confirmPasswordEdit;
    private Button changePasswordButton, cancelButton;

    private DBHelper dbHelper;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        // bind views
        currentPasswordEdit = findViewById(R.id.current_password);
        newPasswordEdit     = findViewById(R.id.new_password);
        confirmPasswordEdit = findViewById(R.id.confirm_password);
        changePasswordButton = findViewById(R.id.change_password_button);
        cancelButton         = findViewById(R.id.cancel_button);

        dbHelper = new DBHelper(this);

        // get and verify userId
        userId = getIntent().getIntExtra("userId", -1);
        if (userId == -1) {
            Toast.makeText(this, "Error: User not logged in", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Cancel just closes the screen
        cancelButton.setOnClickListener(v -> finish());

        changePasswordButton.setOnClickListener(v -> {
            String current = currentPasswordEdit.getText().toString().trim();
            String newPass = newPasswordEdit.getText().toString().trim();
            String confirm = confirmPasswordEdit.getText().toString().trim();

            // basic validations
            if (current.isEmpty() || newPass.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            // fetch stored password
            String existingPassword = dbHelper.getPasswordById(userId);
            if (!current.equals(existingPassword)) {
                Toast.makeText(this, "Current password is incorrect", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPass.equals(confirm)) {
                Toast.makeText(this, "New passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            if (newPass.length() < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            // update in DB
            boolean updated = dbHelper.updatePassword(userId, newPass);
            if (updated) {
                Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to update password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
