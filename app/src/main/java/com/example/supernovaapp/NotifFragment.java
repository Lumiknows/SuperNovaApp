package com.example.supernovaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

public class NotifFragment extends Fragment {

    public NotifFragment() {
        // Required empty public constructor
    }

    public static NotifFragment newInstance(int userId) {
        NotifFragment fragment = new NotifFragment();
        Bundle args = new Bundle();
        args.putInt("userId", userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notif, container, false);

        view.findViewById(R.id.wukong_layout).setOnClickListener(this::onClick);
        view.findViewById(R.id.eldenring_layout).setOnClickListener(this::onClick);
        view.findViewById(R.id.residentevil_layout).setOnClickListener(this::onClick);
        view.findViewById(R.id.profile).setOnClickListener(this::onClick);

        return view;
    }

    private void onClick(View v) {
        int id = v.getId();
        Intent intent = null;

        if (id == R.id.wukong_layout) {
            intent = new Intent(getActivity(), GameView1.class);
        } else if (id == R.id.eldenring_layout) {
            intent = new Intent(getActivity(), GameView2.class);
        } else if (id == R.id.residentevil_layout) {
            intent = new Intent(getActivity(), GameView3.class);
        } else if (id == R.id.profile) {
            intent = new Intent(getActivity(), ProfilePage.class);
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
