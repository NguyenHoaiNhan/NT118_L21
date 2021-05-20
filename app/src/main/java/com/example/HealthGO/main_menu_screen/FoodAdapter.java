package com.example.HealthGO.main_menu_screen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HealthGO.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder> {

    Context context;
    List<FoodCard> list;

    public FoodAdapter(Context context, List<FoodCard> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cardview_food, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.Title.setText(list.get(position).getTitle());
        holder.Description.setText(list.get(position).getDescription());
        holder.imageView.setImageResource(list.get(position).getImageSource());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView Title;
        private TextView Description;
        private ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.Title);
            Description = itemView.findViewById(R.id.Description);
            imageView = itemView.findViewById(R.id.FoodImage);
        }
    }
}
