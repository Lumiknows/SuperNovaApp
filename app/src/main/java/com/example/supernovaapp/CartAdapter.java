package com.example.supernovaapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItems;
    private OnRemoveListener onRemoveListener;

    // Interface for remove button action
    public interface OnRemoveListener {
        void onRemove(int position);
    }

    public CartAdapter(List<CartItem> cartItems, OnRemoveListener onRemoveListener) {
        this.cartItems = cartItems;
        this.onRemoveListener = onRemoveListener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        holder.gameTitle.setText(item.getTitle());
        holder.gameStudio.setText(item.getStudio());
        holder.gamePrice.setText(item.getPrice());
        holder.gameDiscount.setText(item.getDiscount());
        holder.gameImage.setImageResource(item.getImageResource());

        // To prevent crashing when removing all items in the cart
        holder.removeFromCartBtn.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                onRemoveListener.onRemove(adapterPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView gameImage;
        TextView gameTitle, gameStudio, gamePrice, gameDiscount;
        Button removeFromCartBtn;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            gameImage = itemView.findViewById(R.id.gameImage);
            gameTitle = itemView.findViewById(R.id.gameTitle);
            gameStudio = itemView.findViewById(R.id.gameStudio);
            gamePrice = itemView.findViewById(R.id.gamePrice);
            gameDiscount = itemView.findViewById(R.id.gameDiscount);
            removeFromCartBtn = itemView.findViewById(R.id.removeFromCartBtn);
        }
    }
}
