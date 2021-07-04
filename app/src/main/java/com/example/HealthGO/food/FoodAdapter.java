package com.example.HealthGO.food;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.HealthGO.R;
import com.example.HealthGO.main_menu_screen.RecyclerViewClickInterface;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    Context context;
    List<FoodCard> list;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView Title;
        private final TextView Rating_Point;
        private final ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.tv_food_name);
            Rating_Point = itemView.findViewById(R.id.tv_rate_point);
            imageView = itemView.findViewById(R.id.iv_icon_food);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    recyclerViewClickInterface.onLongItemClick(getAdapterPosition());
                    return true;
                }
            });
        }

        public TextView getTitle() {return Title;}
        public TextView getRating_Point() {return Rating_Point;}
        public ImageView getImageView() {return imageView;}
    }

    public FoodAdapter(ArrayList<FoodCard> ls){
        this.list = ls;
    }

    public FoodAdapter(Context context, List<FoodCard> list, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.context = context;
        this.list = list;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_food, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTitle().setText(list.get(position).getTitle());

        double rating =  list.get(position).getRating();
        rating = Math.round(rating * 10)/10;
        String st_rating = String.valueOf(rating);
        holder.getRating_Point().setText(st_rating + "/10");

        Glide.with(context)
                .load(list.get(position).getImageSource())
                .into(holder.getImageView());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
