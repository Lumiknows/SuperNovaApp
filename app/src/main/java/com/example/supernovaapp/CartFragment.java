package com.example.supernovaapp;

import static android.content.Intent.getIntent;
import static android.content.Intent.getIntentOld;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
    private TextView emptyCartMessage;

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance(int userId) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putInt("userId", userId);
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

        // Empty Cart Message
        emptyCartMessage = rootView.findViewById(R.id.emptyCartMessage);

        DBHelper db = new DBHelper(getContext());
        int userId = getArguments().getInt("userId", -1);
        Log.e("USER_ID_CHECK", "userId: " + userId);
        cartItemList = db.getCartItemByUserId(userId);

        if (cartItemList.isEmpty()) {
            emptyCartMessage.setVisibility(View.VISIBLE);
            cartRecyclerView.setVisibility(View.GONE);
        }
        else {
            emptyCartMessage.setVisibility(View.GONE);
            cartRecyclerView.setVisibility(View.VISIBLE);
        }

        // Set up the adapter and layout manager
        cartAdapter = new CartAdapter(cartItemList, new CartAdapter.OnRemoveListener() {
            @Override
            public void onRemove(int position) {
                if (position >= 0 && position < cartItemList.size()) {
                    DBHelper db = new DBHelper(getContext());
                    int cartItemId = cartItemList.get(position).getId();

                    boolean deleted = db.deleteCartItemById(cartItemId);

                    if (deleted) {
                        cartItemList.remove(position);
                        cartAdapter.notifyItemRemoved(position);
                        Toast.makeText(getContext(), "Item removed from cart", Toast.LENGTH_SHORT).show();

                        if (cartItemList.isEmpty()) {
                            emptyCartMessage.setVisibility(View.VISIBLE);
                            cartRecyclerView.setVisibility(View.GONE);
                        }
                    } else {
                        Toast.makeText(getContext(), "Failed to remove item", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Set the layout manager for RecyclerView
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartRecyclerView.setAdapter(cartAdapter);

        return rootView;
    }
}
