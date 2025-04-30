package com.example.supernovaapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CreateAccount extends AppCompatActivity {

    Button signup_button, login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_acount);

        login_button = findViewById(R.id.login_button);
        signup_button = findViewById(R.id.signup_button);

        login_button.setOnClickListener(v -> {
            Intent intent = new Intent(CreateAccount.this, LoginActivity.class);
            startActivity(intent);
        });

        signup_button.setOnClickListener(v -> {
            Intent intent = new Intent(CreateAccount.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
