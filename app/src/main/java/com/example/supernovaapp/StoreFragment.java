package com.example.supernovaapp;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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

        // List Array for Carousel Items
        List<Integer> sale_imageList = new ArrayList<>();
        List<Integer> rec_imageList = new ArrayList<>();

        // Get username from login activity
        int userId = getArguments().getInt("userId");

        // Carousel Adapters
        CarouselAdapter saleCarouselAdapter = new CarouselAdapter(sale_imageList, getContext(), userId);
        CarouselAdapter recCarouselAdapter = new CarouselAdapter(rec_imageList, getContext(), userId);

        profileBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ProfilePage.class);
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

        sale_viewPager2.setAdapter(saleCarouselAdapter);
        rec_viewPager2.setAdapter(recCarouselAdapter);

        return view;
    }
}