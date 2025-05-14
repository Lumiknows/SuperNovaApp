package com.example.supernovaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LibraryFragment extends Fragment {

    private RecyclerView libraryRecyclerView;
    private LibraryAdapter libraryAdapter;
    private List<LibraryItem> libraryItems;

    public LibraryFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_library, container, false);

        libraryRecyclerView = rootView.findViewById(R.id.libraryRecyclerView);
        libraryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ImageButton profileBtn = rootView.findViewById(R.id.profile);

        profileBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ProfilePage.class);
            startActivity(intent);
        });

        // Sample data
        libraryItems = new ArrayList<>();
        libraryItems.add(new LibraryItem("Black Myth: Wukong", "Game Science", "10.3 hrs", R.drawable.wukong2));
        libraryItems.add(new LibraryItem("Phantom Blade Zero", "S-Games", "48.2 hrs", R.drawable.metro));
        libraryItems.add(new LibraryItem("Crimson Desert", "Pearl Abyss", "0.0 hrs", R.drawable.crimson));
        libraryItems.add(new LibraryItem("Monster Hunter", "CapCom Co.", "8.9 hrs", R.drawable.monsterhunter));

        libraryAdapter = new LibraryAdapter(libraryItems);
        libraryRecyclerView.setAdapter(libraryAdapter);

        return rootView;
    }
}
