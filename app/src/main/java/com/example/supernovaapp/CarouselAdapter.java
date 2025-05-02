package com.example.supernovaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder> {
    private List<Integer> imageList;
    private Context context;

    public CarouselAdapter(List<Integer> imageList, Context context) {
        this.imageList = imageList;
        this.context = context;
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
        holder.carouselImgBtn.setImageResource(imageList.get(position));

        holder.carouselImgBtn.setOnClickListener(v -> {
            // To be continue. HAHAHHAHA
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }
}
