package com.example.HealthGO.main_menu_screen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.HealthGO.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    Context context;
    List<FoodCard> list;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView Title;
        //private final TextView Description;
        private final ImageView imageView;
        private final ImageButton Favor;

        public ViewHolder(View view) {
            super(view);
            Title = itemView.findViewById(R.id.Title);
            //Description = itemView.findViewById(R.id.Description);
            imageView = itemView.findViewById(R.id.ThumbNail);
            Favor = itemView.findViewById(R.id.Favor);
        }

        public TextView getTitle() {return Title;}
        //public TextView getDescription() {return Description;}
        public ImageView getImageView() {return imageView;}
    }

    public FoodAdapter(Context context, List<FoodCard> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTitle().setText(list.get(position).getTitle());
        //holder.getDescription().setText(list.get(position).getDescription());
        Glide.with(context)
                .load(list.get(position).getImageSource())
                .into(holder.getImageView());

        holder.Favor.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                if (holder.Favor.getDrawable().getConstantState() == v.getResources().getDrawable(R.drawable.ic_favorite_grey).getConstantState()) {
                    holder.Favor.setImageResource(R.drawable.ic_favorite_red);
                    AddToFavoriteList(v);
                }
                else {
                    holder.Favor.setImageResource(R.drawable.ic_favorite_grey);
                }

            }
        });
    }

    private void AddToFavoriteList(View v) {
        
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
