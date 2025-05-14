package com.example.supernovaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LibraryFragment extends Fragment {

    private RecyclerView libraryRecyclerView;
    private LibraryAdapter libraryAdapter;
    private List<LibraryItem> libraryItems;
    private TextView emptyLibraryMessage;

    public LibraryFragment() {}

    public static LibraryFragment newInstance(int userId) {
        LibraryFragment fragment = new LibraryFragment();
        Bundle args = new Bundle();
        args.putInt("userId", userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_library, container, false);

        libraryRecyclerView = rootView.findViewById(R.id.libraryRecyclerView);
        libraryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        emptyLibraryMessage = rootView.findViewById(R.id.emptyLibraryMessage);

        ImageButton profileBtn = rootView.findViewById(R.id.profile);

        profileBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ProfilePage.class);
            startActivity(intent);
        });

        // Sample data
        //libraryItems.add(new LibraryItem("Phantom Blade Zero", "S-Games", "48.2 hrs", R.drawable.metro));
        //libraryItems.add(new LibraryItem("Monster Hunter", "CapCom Co.", "8.9 hrs", R.drawable.monsterhunter));

        libraryItems = new ArrayList<>();
        DBHelper db = new DBHelper(getContext());
        int userId = getArguments().getInt("userId", -1);
        libraryItems = db.getLibraryByUserId(userId);

        updateLibraryUI();

        libraryAdapter = new LibraryAdapter(libraryItems);
        libraryRecyclerView.setAdapter(libraryAdapter);

        return rootView;
    }

    private void updateLibraryUI() {
        if (libraryItems.isEmpty()) {
            emptyLibraryMessage.setVisibility(View.VISIBLE);
            libraryRecyclerView.setVisibility(View.GONE);
        } else {
            emptyLibraryMessage.setVisibility(View.GONE);
            libraryRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}
