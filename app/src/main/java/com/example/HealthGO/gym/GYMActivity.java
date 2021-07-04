package com.example.HealthGO.gym;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HealthGO.R;
import com.example.HealthGO.food.FoodAdapter;
import com.example.HealthGO.food.FoodCard;
import com.example.HealthGO.main_menu_screen.RecyclerViewClickInterface;
import com.example.HealthGO.workout.Gym;
import com.example.HealthGO.workout.gymAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class GYMActivity extends AppCompatActivity implements RecyclerViewClickInterface {
    private RecyclerView rv_gym;
    private ArrayList<Gym> gymList = new ArrayList<>();
    private FirebaseFirestore db;
    private final String TAG = "gym";

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.fragment_gym);
        rv_gym = (RecyclerView)findViewById(R.id.lv_gym);
        getGymData();
        if(gymList.size() == 0){
            Log.d(TAG, "No date list");
        } else{
            Log.d(TAG, "have: " + gymList.size());
        }
    }



    public void getGymData(){
        ArrayList<Gym> temp = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        db.collection("gym")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String id = document.getString("id");
                            String title = document.getString("name");
                            String source = document.getString("source");
                            String imglink = document.getString("img");

                            temp.add(new Gym(title, imglink, source, id));
                        }

                        gymList.clear();
                        for(Gym i : temp){
                            gymList.add(i);
                        }

                        show(gymList);

                    } else {
                        Log.e("FIRE STORE", "Error getting documents: ", task.getException());
                    }
                });
    }

    public void show(ArrayList<Gym> ls){
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(GYMActivity.this, R.anim.anim_left_to_right);
        rv_gym.setLayoutAnimation(layoutAnimationController);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_gym.setLayoutManager(linearLayoutManager);

        gymAdapter adapter = new gymAdapter(GYMActivity.this, ls, this);
        rv_gym.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        String url = gymList.get(position).getSource();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        intent.setPackage("com.google.android.youtube");
        startActivity(intent);
    }

    @Override
    public void onLongItemClick(int position) {
        Toast.makeText(this, "you long click", Toast.LENGTH_SHORT).show();
    }
}
