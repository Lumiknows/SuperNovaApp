package com.example.supernovaapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class NotifFragment extends Fragment {

    private int userId;
    private String username;
    private DBHelper db;

    public NotifFragment() {
        // Required empty public constructor
    }

    public static NotifFragment newInstance(int userId, String username) {
        NotifFragment fragment = new NotifFragment();
        Bundle args = new Bundle();
        args.putInt("userId", userId);
        args.putString("username", username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notif, container, false);

        // Get userId from arguments
        if (getArguments() != null) {
            userId = getArguments().getInt("userId", -1);
        }

        db = new DBHelper(getContext());
        username = db.getUsernameById(userId);

        // Set username in title
        TextView cartTitle = view.findViewById(R.id.notifTitle);
        cartTitle.setText(username + "'s Notification");

        // Set up profile picture and click
        ImageButton profileBtn = view.findViewById(R.id.profile);

        // ✅ Load profile picture like StoreFragment
        String imageUriString = db.getProfilePicUriById(userId);
        if (imageUriString != null && !imageUriString.isEmpty()) {
            Uri imageUri = Uri.parse(imageUriString);
            profileBtn.setImageURI(imageUri);
        } else {
            profileBtn.setImageResource(R.drawable.profileavatar);
        }

        // Profile click opens ProfilePage
        profileBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ProfilePage.class);
            intent.putExtra("userId", userId);
            intent.putExtra("username", username);
            startActivity(intent);
        });

        // Set game layout click listeners
        view.findViewById(R.id.wukong_layout).setOnClickListener(this::onClick);
        view.findViewById(R.id.eldenring_layout).setOnClickListener(this::onClick);
        view.findViewById(R.id.residentevil_layout).setOnClickListener(this::onClick);

        return view;
    }

    private void onClick(View v) {
        int id = v.getId();
        Intent intent = null;
        TextView textViewToUnbold = null;

        if (id == R.id.wukong_layout) {
            intent = new Intent(getActivity(), GameView1.class);
            textViewToUnbold = getView().findViewById(R.id.wukong_text);
        } else if (id == R.id.eldenring_layout) {
            intent = new Intent(getActivity(), GameView2.class);
            textViewToUnbold = getView().findViewById(R.id.eldenring_text);
        } else if (id == R.id.residentevil_layout) {
            intent = new Intent(getActivity(), GameView3.class);
            textViewToUnbold = getView().findViewById(R.id.resident_text);
        }

        if (textViewToUnbold != null) {
            textViewToUnbold.setTypeface(null, android.graphics.Typeface.NORMAL);
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
