package com.example.supernovaapp;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
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

        frameLayout = findViewById(R.id.framelayout);
        tabLayout = findViewById(R.id.bottom_tab_layout);

        // ✅ Apply icon color selector
        tabLayout.setTabIconTint(getResources().getColorStateList(R.color.tab_icon_color));

        tabLayout.addTab(tabLayout.newTab().setText("").setIcon(R.drawable.store));
        tabLayout.addTab(tabLayout.newTab().setText("").setIcon(R.drawable.library));
        tabLayout.addTab(tabLayout.newTab().setText("").setIcon(R.drawable.cart));
        tabLayout.addTab(tabLayout.newTab().setText("").setIcon(R.drawable.notif_bell));

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framelayout, StoreFragment.newInstance(userId))
                .addToBackStack(null)
                .commit();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;

                switch (tab.getPosition()) {
                    case 0:
                        fragment = StoreFragment.newInstance(userId);
                        break;
                    case 1:
                        fragment = LibraryFragment.newInstance(userId);
                        break;
                    case 2:
                        fragment = CartFragment.newInstance(userId);
                        break;
                    case 3:
                        fragment = NotifFragment.newInstance(userId);
                        break;
                }

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayout, fragment)
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
