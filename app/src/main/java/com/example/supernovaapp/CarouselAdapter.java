package com.example.supernovaapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder> {
    private List<Integer> imageList;
    private Context context;
    private int userId;

    public CarouselAdapter(List<Integer> imageList, Context context, int userId) {
        this.imageList = imageList;
        this.context = context;
        this.userId = userId;
    }

    public static class CarouselViewHolder extends RecyclerView.ViewHolder {
        ImageButton carouselImgBtn;

        public CarouselViewHolder(@NonNull View itemView) {
            super(itemView);
            carouselImgBtn = itemView.findViewById(R.id.carouselImageBtn);
        }
    }

    @NonNull
    @Override
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carousel_item, parent, false);
        return new CarouselViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarouselViewHolder holder, int position) {
        int imageResId = imageList.get(position);
        holder.carouselImgBtn.setImageResource(imageResId);


        // Dili pani final huwat pa ko sa mga images sa uban
        holder.carouselImgBtn.setOnClickListener(v -> {
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
            else {
                Toast.makeText(context, "Game not found.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }
}
