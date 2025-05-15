package com.example.supernovaapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private List<StoreItem> itemList;
    private Context context;
    private int userId;

    private List<StoreItem> fullItemList;

    public SearchAdapter(Context context, List<StoreItem> itemList, int userId) {
        this.context = context;
        this.itemList = itemList;
        this.userId = userId;

        fullItemList = new ArrayList<>(itemList);
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.store_item, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        StoreItem item = itemList.get(position);
        holder.gameTitle.setText(item.getTitle());
        holder.gameStudio.setText(item.getStudio());
        holder.gamePrice.setText(item.getPrice());
        holder.gameImage.setImageResource(item.getImageResId());

        int imageResId = itemList.get(position).getImageResId();

        holder.gameImage.setOnClickListener(v -> {
            if (imageResId == R.drawable.blackwukong) {
                Intent intent = new Intent(context, GameView1.class);
                intent.putExtra("userId", userId);
                Log.e("USER_ID_CHECK", "userId: " + userId);
                context.startActivity(intent);
            }
            else if (imageResId == R.drawable.ready_or_not) {
                Intent intent = new Intent(context, GameView4.class);
                intent.putExtra("userId", userId);
                Log.e("USER_ID_CHECK", "userId: " + userId);
                context.startActivity(intent);
            }
            else if (imageResId == R.drawable.crimson) {
                Intent intent = new Intent(context, GameView5.class);
                intent.putExtra("userId", userId);
                Log.e("USER_ID_CHECK", "userId: " + userId);
                context.startActivity(intent);
            }
            else if (imageResId == R.drawable.eldenring) {
                Intent intent = new Intent(context, GameView2.class);
                intent.putExtra("userId", userId);
                context.startActivity(intent);
            }
            else if (imageResId == R.drawable.residentevil) {
                Intent intent = new Intent(context, GameView3.class);
                intent.putExtra("userId", userId);
                context.startActivity(intent);
            }
            else {
                Toast.makeText(context, "Game not found.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void filter(String query) {
        query = query.toLowerCase().trim();
        itemList.clear();

        if (query.isEmpty()) {
            itemList.addAll(fullItemList);
        } else {
            for (StoreItem item : fullItemList) {
                if (item.getTitle().toLowerCase().contains(query) ||
                        item.getStudio().toLowerCase().contains(query)) {
                    itemList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        ImageView gameImage;
        TextView gameTitle;
        TextView gameStudio;
        TextView gamePrice;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            gameImage = itemView.findViewById(R.id.gameImage);
            gameTitle = itemView.findViewById(R.id.gameTitle);
            gameStudio = itemView.findViewById(R.id.gameStudio);
            gamePrice = itemView.findViewById(R.id.gamePrice);
        }
    }
}

