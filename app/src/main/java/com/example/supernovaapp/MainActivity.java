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

        frameLayout = (FrameLayout) findViewById(R.id.framelayout);
        tabLayout = (TabLayout) findViewById(R.id.bottom_tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("Store").setIcon(R.drawable.store));
        tabLayout.addTab(tabLayout.newTab().setText("Library").setIcon(R.drawable.library));

        TabLayout.Tab profileTab = tabLayout.newTab().setIcon(R.drawable.defaultprof);
        tabLayout.addTab(profileTab);
        // Change the profile size to become slightly bigger than the other tab
        View customView = getLayoutInflater().inflate(R.layout.custom_profile_tab, null);
        profileTab.setCustomView(customView);

        tabLayout.addTab(tabLayout.newTab().setText("Community").setIcon(R.drawable.community));
        tabLayout.addTab(tabLayout.newTab().setText("Chat").setIcon(R.drawable.chat));

        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new StoreFragment())
                .addToBackStack(null)
                .commit();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;

                switch(tab.getPosition()) {
                    case 0:
                        fragment = new StoreFragment();
                        break;
                    case 1:
                        fragment = new LibraryFragment();
                        break;
                    case 2:
                        fragment = new ProfileFragment();
                        break;
                    case 3:
                        fragment = new CommunityFragment();
                        break;
                    case 4:
                        fragment = new ChatFragment();
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