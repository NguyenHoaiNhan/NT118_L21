package com.example.HealthGO.map;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    private String title;
    private Restaurant restaurant;
    final String TAG = "detail";

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_show_detail_restaurant);
        Bundle bundle = getIntent().getExtras();
        title = bundle.getString("title");
        initComponent();
        showFood();
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = tv_phone.getText().toString();
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + phone));
                startActivity(callIntent);
            }
        });
    }

    public void initComponent(){
        btn_order = (Button)findViewById(R.id.btn_order);
        tv_name = (TextView)findViewById(R.id.tv_restaurant_name);
        tv_phone = (TextView)findViewById(R.id.tv_res_phone);
        tv_servetime = (TextView)findViewById(R.id.tv_service_time);
        lv_dish = (ListView)findViewById(R.id.lv_detail);
    }

    public void showFood(){
        Log.d(TAG, "get data");
        ArrayList<String> list = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        db.collection("restaurant")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            if (title.equals(document.getString("name"))) {
                                String name = document.getString("name");
                                String phone = document.getString("phone");
                                String servetime = document.getString("servetime");
                                double lat = document.getDouble("lat");
                                double longi = document.getDouble("long");
                                List<String> lsDist = (List<String>) document.get("dish");
                                List<String> lsCost = (List<String>) document.get("cost");

                                restaurant = new Restaurant(name, phone, servetime, lat, longi, lsDist, lsCost);
                                Log.d(TAG, "name:" + name + "\tphone:" + phone + "\ttime:" + servetime);
                            }

                        }

                        tv_name.setText(restaurant.getName());
                        tv_phone.setText(restaurant.getPhone());
                        tv_servetime.setText("Phục vụ: " + restaurant.getServetime());

                        for(int i = 0; i < restaurant.getLsDish().size(); i++){
                            String dish = restaurant.getLsDish().get(i);
                            String cost = restaurant.getLsCost().get(i);
                            String row = dish + "\tcó giá:\t" + cost + "/Phần";
                            list.add(row);
                            Log.d(TAG, row);
                        }

                        ArrayAdapter adapter = new ArrayAdapter(ShowDetailRestaurant.this,
                                android.R.layout.simple_list_item_1,
                                list);

                        lv_dish.setAdapter(adapter);

                    } else {
                        Log.d(TAG, "get data failed");
                        Log.e("FIRE STORE", "Error getting documents: ", task.getException());
                    }
                });
    }
}
