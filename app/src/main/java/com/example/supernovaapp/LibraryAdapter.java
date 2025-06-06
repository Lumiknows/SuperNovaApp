package com.example.supernovaapp;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder> {

    private List<LibraryItem> itemList;
    private int userId;
    private String username;

    // Updated constructor to accept userId and username
    public LibraryAdapter(List<LibraryItem> itemList, int userId, String username) {
        this.itemList = itemList;
        this.userId = userId;
        this.username = username;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView gameImage;
        TextView gameTitle, gameStudio, gameHrs;
        Button reviewBtn;
        ImageButton downloadBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            gameImage = itemView.findViewById(R.id.gameImage);
            gameTitle = itemView.findViewById(R.id.gameTitle);
            gameStudio = itemView.findViewById(R.id.gameStudio);
            gameHrs = itemView.findViewById(R.id.hrs);
            reviewBtn = itemView.findViewById(R.id.review);
            downloadBtn = itemView.findViewById(R.id.download);
        }
    }

    @Override
    public LibraryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LibraryAdapter.ViewHolder holder, int position) {
        LibraryItem item = itemList.get(position);
        holder.gameImage.setImageResource(item.getImageResource());
        holder.gameTitle.setText(item.getTitle());
        holder.gameStudio.setText(item.getStudio());
        holder.gameHrs.setText(item.getHrs());

        android.content.Context context = holder.itemView.getContext();
        android.content.SharedPreferences sharedPreferences = context.getSharedPreferences("GameReviewPrefs", android.content.Context.MODE_PRIVATE);

        // Check for review status
        String reviewKey = "review_" + item.getTitle();
        String savedReview = sharedPreferences.getString(reviewKey, null);

        if (savedReview != null) {
            holder.reviewBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A6E964")));
            holder.reviewBtn.setText("Reviewed");
        } else {
            holder.reviewBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#252525")));
            holder.reviewBtn.setText("Review");
        }

        // Check download status
        String downloadKey = "downloaded_" + item.getTitle();
        boolean isDownloaded = sharedPreferences.getBoolean(downloadKey, false);

        if (isDownloaded) {
            holder.downloadBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A6E964")));
            holder.downloadBtn.setImageResource(R.drawable.ic_check);
            holder.downloadBtn.setEnabled(false);
        } else {
            holder.downloadBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#252525")));
            holder.downloadBtn.setImageResource(R.drawable.download);
            holder.downloadBtn.setEnabled(true);
        }

        // Handle review button click with userId and username passed
        holder.reviewBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), Review.class);
            intent.putExtra("gameTitle", item.getTitle());
            intent.putExtra("gameImage", item.getImageResource());
            intent.putExtra("gameStudio", item.getStudio());
            intent.putExtra("gameHrs", item.getHrs());
            intent.putExtra("userId", userId);
            intent.putExtra("username", username);
            v.getContext().startActivity(intent);
        });

        // Handle download button click
        holder.downloadBtn.setOnClickListener(v -> {
            sharedPreferences.edit().putBoolean(downloadKey, true).apply();

            holder.downloadBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
            holder.downloadBtn.setImageResource(R.drawable.ic_check);
            holder.downloadBtn.setEnabled(false);

            Intent intent = new Intent(v.getContext(), DownloadPopupActivity.class);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
