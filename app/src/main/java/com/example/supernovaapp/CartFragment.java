package com.example.supernovaapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItemList;

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Handle any initialization if needed (param1, param2)
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_cart, container, false);

        // Initialize the RecyclerView
        cartRecyclerView = rootView.findViewById(R.id.cartRecyclerView);
        cartItemList = new ArrayList<>();

        // Sample data
        cartItemList.add(new CartItem("Ready or Not", "VOID Interactive", "$12.99", "30%", R.drawable.ready_or_not));
        cartItemList.add(new CartItem("Crimson Desert", "Pearl Abyss", "$14.99", "20%", R.drawable.crimson));

        // Set up the adapter and layout manager
        cartAdapter = new CartAdapter(cartItemList, new CartAdapter.OnRemoveListener() {
            @Override
            public void onRemove(int position) {
                // Handle item removal
                cartItemList.remove(position);
                cartAdapter.notifyItemRemoved(position);
                Toast.makeText(getContext(), "Item removed from cart", Toast.LENGTH_SHORT).show();
            }
        });

        // Set the layout manager for RecyclerView
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartRecyclerView.setAdapter(cartAdapter);

        return rootView;
    }
}
