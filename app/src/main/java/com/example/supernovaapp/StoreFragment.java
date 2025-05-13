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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StoreFragment newInstance(String param1, String param2) {
        StoreFragment fragment = new StoreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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

        // Carousel Adapters
        CarouselAdapter saleCarouselAdapter = new CarouselAdapter(sale_imageList, getContext());
        CarouselAdapter recCarouselAdapter = new CarouselAdapter(rec_imageList, getContext());

        profileBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ProfilePage.class);
            startActivity(intent);
        });

        // Items for sale carousel
        sale_imageList.add(R.drawable.ph0);
        sale_imageList.add(R.drawable.bloodborne);
        sale_imageList.add(R.drawable.crimsondesert);
        sale_imageList.add(R.drawable.blackwukong);
        sale_imageList.add(R.drawable.coolgame);

        // Items for recommended carousel
        rec_imageList.add(R.drawable.coolgame);
        rec_imageList.add(R.drawable.crimsondesert);
        rec_imageList.add(R.drawable.blackwukong);
        rec_imageList.add(R.drawable.phantomblade);
        rec_imageList.add(R.drawable.bloodborne);

        sale_viewPager2.setAdapter(saleCarouselAdapter);
        rec_viewPager2.setAdapter(recCarouselAdapter);
        return view;
    }
}