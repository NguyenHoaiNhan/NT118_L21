package com.example.HealthGO.map;

import android.os.Bundle;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.HealthGO.R;
import com.example.HealthGO.food.FoodAdapter;
import com.example.HealthGO.food.FoodCard;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowDetailRestaurant extends AppCompatActivity {
    private Button btn_order;
    private TextView tv_name, tv_phone, tv_servetime;
    private ListView lv_dish;
    private FirebaseFirestore db;
    private List<Restaurant> lsRestaurant;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_show_detail_restaurant);
        initComponent();
    }

    public void initComponent(){
        btn_order = (Button)findViewById(R.id.btn_order);
        tv_name = (TextView)findViewById(R.id.tv_restaurant_name);
        tv_phone = (TextView)findViewById(R.id.tv_res_phone);
        tv_servetime = (TextView)findViewById(R.id.tv_service_time);
        lv_dish = (ListView)findViewById(R.id.lv_detail);
    }

    public void showFood(String inputname){
        db = FirebaseFirestore.getInstance();
        db.collection("restaurant")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (inputname == document.getString("name")) {
                                String name = document.getString("name");
                                String phone = document.getString("phone");
                                String servetime = document.getString("servetime");
                                double lat = document.getDouble("lat");
                                double longi = document.getDouble("long");
                                List<String> lsDist = (List<String>) document.get("dish");
                                List<Integer> lsCost = (List<Integer>) document.get("cost");

                                lsRestaurant.add(new Restaurant(name, phone, servetime, lat, longi, lsDist, lsCost));
                            }
                        }


                    } else {
                        Log.e("FIRE STORE", "Error getting documents: ", task.getException());
                    }
                });
    }
}
