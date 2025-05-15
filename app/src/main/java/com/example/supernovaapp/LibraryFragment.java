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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LibraryFragment extends Fragment {

    private RecyclerView libraryRecyclerView;
    private LibraryAdapter libraryAdapter;
    private List<LibraryItem> libraryItems;
    private TextView emptyLibraryMessage;

    private DBHelper db;
    private int userId;
    private String username;

    public LibraryFragment() {}

    public static LibraryFragment newInstance(int userId, String username) {
        LibraryFragment fragment = new LibraryFragment();
        Bundle args = new Bundle();
        args.putInt("userId", userId);
        args.putString("username", username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_library, container, false);

        // Get args from bundle
        if (getArguments() != null) {
            userId = getArguments().getInt("userId", -1);
            username = getArguments().getString("username", "user");
        }

        db = new DBHelper(getContext());

        // Setup views
        libraryRecyclerView = rootView.findViewById(R.id.libraryRecyclerView);
        libraryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        emptyLibraryMessage = rootView.findViewById(R.id.emptyLibraryMessage);

        TextView cartTitle = rootView.findViewById(R.id.libraryTitle);
        username = db.getUsernameById(userId); // Refresh from DB in case updated
        cartTitle.setText(username + "'s Library");

        ImageButton profileBtn = rootView.findViewById(R.id.profile);

        // Load and show profile picture like StoreFragment
        String imageUriString = db.getProfilePicUriById(userId);
        if (imageUriString != null && !imageUriString.isEmpty()) {
            Uri imageUri = Uri.parse(imageUriString);
            profileBtn.setImageURI(imageUri);
        } else {
            profileBtn.setImageResource(R.drawable.profileavatar);
        }

        profileBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ProfilePage.class);
            intent.putExtra("userId", userId);
            intent.putExtra("username", username);
            startActivity(intent);
        });

        // Load library items from DB
        libraryItems = db.getLibraryByUserId(userId);
        libraryAdapter = new LibraryAdapter(libraryItems, userId, username);
        libraryRecyclerView.setAdapter(libraryAdapter);

        updateLibraryUI();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Reload review states from SharedPreferences
        if (libraryAdapter != null) {
            libraryAdapter.notifyDataSetChanged(); // this will trigger onBindViewHolder again
        }
    }


    private void updateLibraryUI() {
        if (libraryItems == null || libraryItems.isEmpty()) {
            emptyLibraryMessage.setVisibility(View.VISIBLE);
            libraryRecyclerView.setVisibility(View.GONE);
        } else {
            emptyLibraryMessage.setVisibility(View.GONE);
            libraryRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}
