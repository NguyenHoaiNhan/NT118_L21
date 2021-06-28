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
        private final TextView Description;
        private final ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.Title);
            Description = itemView.findViewById(R.id.Description);
            imageView = itemView.findViewById(R.id.FoodImage);

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
                    return false;
                }
            });
        }

        public TextView getTitle() {return Title;}
        public TextView getDescription() {return Description;}
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
        holder.getDescription().setText(list.get(position).getDescription());
        Glide.with(context)
                .load(list.get(position).getImageSource())
                .into(holder.getImageView());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
