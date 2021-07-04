package com.example.HealthGO.workout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.HealthGO.R;
import com.example.HealthGO.food.FoodCard;
import com.example.HealthGO.food.FoodSuggestionAdapter;
import com.example.HealthGO.main_menu_screen.RecyclerViewClickInterface;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.List;

public class gymAdapter extends RecyclerView.Adapter<gymAdapter.GymViewHolder>{
     Context context;
     List<Gym> gymList;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public gymAdapter(Context context, List<Gym> gymList, RecyclerViewClickInterface recyclerViewClickInterface){
        this.context = context;
        this.gymList = gymList;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @Override
    public GymViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gym_item, parent, false);
        return new gymAdapter.GymViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull GymViewHolder holder, int position) {
        holder.getTv_title().setText(gymList.get(position).getName());
        Glide.with(context)
                .load(gymList.get(position).getImgURL())
                .into(holder.getIv_img());
    }

    @Override
    public int getItemCount() {
        if(gymList != null){
            return gymList.size();
        }
        return 0;
    }

    public class GymViewHolder extends RecyclerView.ViewHolder{
        private final TextView tv_title;
        private final ImageView iv_img;

        public GymViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.title_gym);
            iv_img = itemView.findViewById(R.id.iv_gym);

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

        public TextView getTv_title() {
            return tv_title;
        }

        public ImageView getIv_img() {
            return iv_img;
        }
    }
}
