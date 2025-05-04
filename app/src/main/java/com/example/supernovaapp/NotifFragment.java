package com.example.supernovaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class NotifFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public NotifFragment() {
        // Required empty public constructor
    }

    public static NotifFragment newInstance(String param1, String param2) {
        NotifFragment fragment = new NotifFragment();
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
        View view = inflater.inflate(R.layout.fragment_notif, container, false);  // Make sure 'fragment_notif.xml' exists

        // Find the layout for Wukong (make sure it has android:id="@+id/wukong_layout" in fragment_notif.xml)
        View wukongLayout = view.findViewById(R.id.wukong_layout);

        // Set click listener to open GameView activity
        wukongLayout.setOnClickListener(this::onClick);

        return view;
    }

    private void onClick(View v) {
        Intent intent = new Intent(getActivity(), GameView.class);  // Make sure GameView activity is in the manifest
        startActivity(intent);
    }
}
