package com.example.supernovaapp;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int userId = getIntent().getIntExtra("userId", -1);

        frameLayout = (FrameLayout) findViewById(R.id.framelayout);
        tabLayout = (TabLayout) findViewById(R.id.bottom_tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("Store").setIcon(R.drawable.store));
        tabLayout.addTab(tabLayout.newTab().setText("Library").setIcon(R.drawable.library));
        tabLayout.addTab(tabLayout.newTab().setText("Cart").setIcon(R.drawable.cart));
        tabLayout.addTab(tabLayout.newTab().setText("News").setIcon(R.drawable.notif_bell));

        getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayout, StoreFragment.newInstance(userId)).addToBackStack(null).commit();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;

                switch(tab.getPosition()) {
                    case 0:
                        fragment = StoreFragment.newInstance(userId);
                        break;
                    case 1:
                        fragment = new LibraryFragment();
                        break;
                    case 2:
                        fragment = CartFragment.newInstance(userId);
                        break;
                    case 3:
                        fragment = new NotifFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}