package com.example.supernovaapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

public class DownloadPopupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_popup);

        // Automatically close popup after 3 seconds (simulate download)
        new Handler().postDelayed(() -> finish(), 3000);
    }
}
