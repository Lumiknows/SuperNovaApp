package com.example.supernovaapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder> {

    private List<LibraryItem> itemList;

    public LibraryAdapter(List<LibraryItem> itemList) {
        this.itemList = itemList;
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

        // Placeholder click listeners
        holder.reviewBtn.setOnClickListener(v ->
                Toast.makeText(v.getContext(), "Review clicked", Toast.LENGTH_SHORT).show()
        );

        holder.downloadBtn.setOnClickListener(v ->
                Toast.makeText(v.getContext(), "Download clicked", Toast.LENGTH_SHORT).show()
        );
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
