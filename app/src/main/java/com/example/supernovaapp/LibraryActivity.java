package com.example.supernovaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class LibraryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        ImageButton storeButton = findViewById(R.id.store_button);
        ImageButton backButton = findViewById(R.id.back_button);

        View.OnClickListener goToStoreListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibraryActivity.this, StoreActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        };

        // Since same man sila ug function gi usa nalang nako ang listener para dili kaayo taas ang code
        storeButton.setOnClickListener(goToStoreListener);
        backButton.setOnClickListener(goToStoreListener);
    }
}
