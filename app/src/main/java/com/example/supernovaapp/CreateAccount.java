package com.example.supernovaapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateAccount extends AppCompatActivity {

    Button signup_button, login_button;
    EditText username, password, confirm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        login_button = findViewById(R.id.login_button);
        signup_button = findViewById(R.id.signup_button);
        username = findViewById(R.id.create_username);
        password = findViewById(R.id.create_password);
        confirm_password = findViewById(R.id.confirm_password);

        login_button.setOnClickListener(v -> {
            Intent intent = new Intent(CreateAccount.this, LoginActivity.class);
            startActivity(intent);
        });

        signup_button.setOnClickListener(v -> {
            String newUser = username.getText().toString().trim();
            String newPass = password.getText().toString().trim();
            String confirmPass = confirm_password.getText().toString().trim();

            DBHelper db = new DBHelper(this);

            if (newUser.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show();
            } else if (db.checkUserIfExists(newUser)) {
                Toast.makeText(this, "Username already taken. Please choose another.", Toast.LENGTH_SHORT).show();
            } else if (newPass.length() < 8) {
                Toast.makeText(this, "Password must be at least 8 characters.", Toast.LENGTH_SHORT).show();
            } else if (!confirmPass.equals(newPass)) {
                Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
            } else {
                boolean success = db.insertUser(newUser, newPass);
                if (success) {
                    Toast.makeText(this, "Account created! Please log in.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateAccount.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Signup failed. Try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
