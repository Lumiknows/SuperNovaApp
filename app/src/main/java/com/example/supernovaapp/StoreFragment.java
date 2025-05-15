package com.example.supernovaapp;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoreFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StoreFragment() {
        // Required empty public constructor
    }
    public static StoreFragment newInstance(int userId) {
        StoreFragment fragment = new StoreFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store, container, false);

        ImageButton profileBtn = view.findViewById(R.id.profile);

        // Carousel View Pager
        ViewPager2 sale_viewPager2 = view.findViewById(R.id.sale_carousel);
        ViewPager2 rec_viewPager2 = view.findViewById(R.id.recommended_carousel);

        // Recycler view for extra game list
        RecyclerView recyclerView = view.findViewById(R.id.storeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // List Array for Carousel Items
        List<Integer> sale_imageList = new ArrayList<>();
        List<Integer> rec_imageList = new ArrayList<>();

        // Store list for extra games
        List<StoreItem> store_itemList = new ArrayList<>();

        // Get username from login activity
        int userId = getArguments().getInt("userId");

        // Carousel Adapters
        CarouselAdapter saleCarouselAdapter = new CarouselAdapter(sale_imageList, getContext(), userId);
        CarouselAdapter recCarouselAdapter = new CarouselAdapter(rec_imageList, getContext(), userId);

        // Store Adapter
        StoreAdapter storeAdapter = new StoreAdapter(getContext(), store_itemList, userId);

        profileBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ProfilePage.class);
            intent.putExtra("userId", userId);  // Pass the userId!
            startActivity(intent);
        });

        // Items for sale carousel
        sale_imageList.add(R.drawable.eldenring);
        sale_imageList.add(R.drawable.ready_or_not);
        sale_imageList.add(R.drawable.metro);
        sale_imageList.add(R.drawable.crimson);
        sale_imageList.add(R.drawable.gta6);
        sale_imageList.add(R.drawable.lethal);
        sale_imageList.add(R.drawable.mortal);
        sale_imageList.add(R.drawable.bloodborne2);

        // Items for recommended carousel
        rec_imageList.add(R.drawable.blackwukong);
        rec_imageList.add(R.drawable.helltaker);
        rec_imageList.add(R.drawable.residentevil);
        rec_imageList.add(R.drawable.phantomblade);
        rec_imageList.add(R.drawable.ark2);
        rec_imageList.add(R.drawable.subverse);
        rec_imageList.add(R.drawable.dragon2);
        rec_imageList.add(R.drawable.ghost);
        rec_imageList.add(R.drawable.repo);

        // Extra games
        store_itemList.add(new StoreItem("Elden Ring", "Studio: FromSoftware", "Price: ₱2399.00", R.drawable.eldenring));
        store_itemList.add(new StoreItem("Read or Not", "Studio: VOID Interactive", "Price: ₱1000.00", R.drawable.ready_or_not));
        store_itemList.add(new StoreItem("Resident Evil", "Studio: Capcom Co.", "Price: ₱1979.00", R.drawable.residentevil));
        store_itemList.add(new StoreItem("Subverse", "Studio: Studio FOW", "Price: ₱1680.25", R.drawable.subverse));
        store_itemList.add(new StoreItem("Crimson Desert", "Studio: Pearl Abyss", "Price: ₱3239.00", R.drawable.crimson));
        store_itemList.add(new StoreItem("GTA VI", "Studio: Rockstar Games", "Price: TBD", R.drawable.gta6));
        store_itemList.add(new StoreItem("Black Myth Wukong", "Studio: Game Science", "Price: ₱2599.00", R.drawable.blackwukong));
        store_itemList.add(new StoreItem("Ark 2", "Studio: Studio Wildcard", "Price: ₱2790.10", R.drawable.ark2));
        store_itemList.add(new StoreItem("Bloodborne", "Studio: FromSoftware", "Price: ₱2230.15", R.drawable.bloodborne2));
        store_itemList.add(new StoreItem("Dragon's Dogma 2", "Studio: Capcom", "Price: TBD", R.drawable.dragon2));
        store_itemList.add(new StoreItem("Ghost Recon Breakpoint", "Studio: Ubisoft", "Price: ₱3345.99", R.drawable.ghost));
        store_itemList.add(new StoreItem("Helltaker", "Studio: Vanripper", "Price: ₱280.40", R.drawable.helltaker));
        store_itemList.add(new StoreItem("Lethal Company", "Studio: Lethal Team", "Price: ₱290.40", R.drawable.lethal));
        store_itemList.add(new StoreItem("Mortal Kombat 11", "Studio: NetherRealm Studios", "Price: ₱3346.99", R.drawable.mortal));

        sale_viewPager2.setAdapter(saleCarouselAdapter);
        rec_viewPager2.setAdapter(recCarouselAdapter);
        recyclerView.setAdapter(storeAdapter);

        return view;
    }
}