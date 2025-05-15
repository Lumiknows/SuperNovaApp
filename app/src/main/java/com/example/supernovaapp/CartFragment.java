package com.example.supernovaapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

    private Button buyButton;
    private FrameLayout checkoutOverlay;
    private TextView checkoutTotal;
    private Button confirmCheckout;
    private Button cancelCheckout;
    private DBHelper db;
    private int userId;

    // ✅ NEW: Checkout game list container
    private LinearLayout checkoutGameList;

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance(int userId, String username) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putInt("userId", userId);
        args.putString("username", username); // ✅ Pass the username
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cart, container, false);

        // Initialize views
        cartRecyclerView = rootView.findViewById(R.id.cartRecyclerView);
        emptyCartMessage = rootView.findViewById(R.id.emptyCartMessage);
        buyButton = rootView.findViewById(R.id.buyButton);
        checkoutOverlay = rootView.findViewById(R.id.checkoutOverlay);
        checkoutTotal = rootView.findViewById(R.id.checkoutTotal);
        confirmCheckout = rootView.findViewById(R.id.confirmCheckout);
        cancelCheckout = rootView.findViewById(R.id.cancelCheckout);
        checkoutGameList = rootView.findViewById(R.id.checkoutGameList);

        // ✅ Initialize DB and get userId from arguments
        db = new DBHelper(getContext());
        userId = getArguments().getInt("userId", -1);

        // ✅ Set cart title with username
        TextView cartTitle = rootView.findViewById(R.id.cartTitle);
        String username = db.getUsernameById(userId);
        cartTitle.setText(username + "'s Cart");

        // Load cart items
        cartItemList = new ArrayList<>();
        cartItemList = db.getCartItemByUserId(userId);
        updateCartUI();

        cartAdapter = new CartAdapter(cartItemList, position -> {
            if (position >= 0 && position < cartItemList.size()) {
                int cartItemId = cartItemList.get(position).getId();
                boolean deleted = db.deleteCartItemById(cartItemId);

                if (deleted) {
                    cartItemList.remove(position);
                    cartAdapter.notifyItemRemoved(position);
                    Toast.makeText(getContext(), "Item removed from cart", Toast.LENGTH_SHORT).show();
                    updateCartUI();
                } else {
                    Toast.makeText(getContext(), "Failed to remove item", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartRecyclerView.setAdapter(cartAdapter);

        // Buy Button click
        buyButton.setOnClickListener(v -> {
            checkoutGameList.removeAllViews(); // Clear previous game titles
            double totalPrice = 0;

            for (CartItem item : cartItemList) {
                LinearLayout gameLayout = new LinearLayout(getContext());
                gameLayout.setOrientation(LinearLayout.HORIZONTAL);
                gameLayout.setPadding(0, 4, 0, 4);

                TextView gameTitle = new TextView(getContext());
                gameTitle.setText(item.getTitle());
                gameTitle.setTextColor(Color.WHITE);
                gameTitle.setTextSize(13);
                gameTitle.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                gameLayout.addView(gameTitle);

                TextView gamePrice = new TextView(getContext());
                gamePrice.setText("₱" + String.format("%.2f", item.getPriceAsDouble()));
                gamePrice.setTextColor(Color.WHITE);
                gamePrice.setTextSize(13);
                gamePrice.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                gameLayout.addView(gamePrice);

                checkoutGameList.addView(gameLayout);

                totalPrice += item.getPriceAsDouble();
            }

            checkoutTotal.setText("Total: ₱" + String.format("%.2f", totalPrice));
            checkoutOverlay.setVisibility(View.VISIBLE);
        });

        // Confirm Checkout
        confirmCheckout.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Purchase confirmed!", Toast.LENGTH_SHORT).show();
            checkoutOverlay.setVisibility(View.GONE);

            String hrs = "0.0 hrs";

            for (CartItem item : cartItemList) {
                boolean addedToLibrary = db.insertToLibrary(userId, item.title, item.studio, hrs, item.getImageResource());

                if (addedToLibrary) {
                    boolean deletedFromCart = db.deleteCartItemById(item.getId());

                    if (deletedFromCart) {
                        Toast.makeText(getContext(), item.getTitle() + " added to library", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Failed to remove " + item.getTitle() + " from cart", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to add " + item.getTitle() + " to library", Toast.LENGTH_SHORT).show();
                }
            }

            cartItemList.clear();
            cartAdapter.notifyDataSetChanged();
            updateCartUI();
        });

        // Cancel Checkout
        cancelCheckout.setOnClickListener(v -> {
            checkoutOverlay.setVisibility(View.GONE);
        });

        // Profile button
        ImageButton profileBtn = rootView.findViewById(R.id.profile);
        profileBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ProfilePage.class);
            intent.putExtra("userId", userId);
            intent.putExtra("username", username);
            startActivity(intent);
        });

        return rootView;
    }

    private void updateCartUI() {
        if (cartItemList.isEmpty()) {
            emptyCartMessage.setVisibility(View.VISIBLE);
            cartRecyclerView.setVisibility(View.GONE);
            buyButton.setEnabled(false);
        } else {
            emptyCartMessage.setVisibility(View.GONE);
            cartRecyclerView.setVisibility(View.VISIBLE);
            buyButton.setEnabled(true);
        }
    }
}
