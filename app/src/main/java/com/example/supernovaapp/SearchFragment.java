package com.example.supernovaapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DBHelper dbHelper;

    public SearchFragment() {
        // Required empty public constructor
    }
    public static SearchFragment newInstance(int userId) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putInt("userId", userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        
        dbHelper = new DBHelper(getContext());
        
        ImageButton profileBtn = view.findViewById(R.id.profile);
        
        RecyclerView recyclerView = view.findViewById(R.id.searchRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<StoreItem> search_itemList = new ArrayList<>();

        // Get username from login activity
        int userId = getArguments().getInt("userId");

        EditText searchBar = view.findViewById(R.id.search_bar);

        profileBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ProfilePage.class);
            intent.putExtra("userId", userId);  // Pass the userId!
            startActivity(intent);
        });

        String imageUriString = dbHelper.getProfilePicUriById(userId);
        if (imageUriString != null && !imageUriString.isEmpty()) {
            Uri imageUri = Uri.parse(imageUriString);
            profileBtn.setImageURI(imageUri);
        } else {
            // Use default profile image if none saved
            profileBtn.setImageResource(R.drawable.profileavatar);
        }

        search_itemList.add(new StoreItem("Elden Ring", "Studio: FromSoftware", "Price: ₱2399.00", R.drawable.eldenring));
        search_itemList.add(new StoreItem("Read or Not", "Studio: VOID Interactive", "Price: ₱1000.00", R.drawable.ready_or_not));
        search_itemList.add(new StoreItem("Resident Evil", "Studio: Capcom Co.", "Price: ₱1979.00", R.drawable.residentevil));
        search_itemList.add(new StoreItem("Subverse", "Studio: Studio FOW", "Price: ₱1680.25", R.drawable.subverse));
        search_itemList.add(new StoreItem("Crimson Desert", "Studio: Pearl Abyss", "Price: ₱3239.00", R.drawable.crimson));
        search_itemList.add(new StoreItem("GTA VI", "Studio: Rockstar Games", "Price: TBD", R.drawable.gta6));
        search_itemList.add(new StoreItem("Black Myth Wukong", "Studio: Game Science", "Price: ₱2599.00", R.drawable.blackwukong));
        search_itemList.add(new StoreItem("Ark 2", "Studio: Studio Wildcard", "Price: ₱2790.10", R.drawable.ark2));
        search_itemList.add(new StoreItem("Bloodborne", "Studio: FromSoftware", "Price: ₱2230.15", R.drawable.bloodborne2));
        search_itemList.add(new StoreItem("Dragon's Dogma 2", "Studio: Capcom", "Price: TBD", R.drawable.dragon2));
        search_itemList.add(new StoreItem("Ghost Recon Breakpoint", "Studio: Ubisoft", "Price: ₱3345.99", R.drawable.ghost));
        search_itemList.add(new StoreItem("Helltaker", "Studio: Vanripper", "Price: ₱280.40", R.drawable.helltaker));
        search_itemList.add(new StoreItem("Lethal Company", "Studio: Lethal Team", "Price: ₱290.40", R.drawable.lethal));
        search_itemList.add(new StoreItem("Mortal Kombat 11", "Studio: NetherRealm Studios", "Price: ₱3346.99", R.drawable.mortal));

        SearchAdapter searchAdapter = new SearchAdapter(getContext(), search_itemList, userId);
        recyclerView.setAdapter(searchAdapter);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchAdapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        return view;
    }
}
