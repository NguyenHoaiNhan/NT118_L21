package com.example.HealthGO.person;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HealthGO.R;
import com.example.HealthGO.food.FoodAdapter;
import com.example.HealthGO.food.FoodCard;
import com.example.HealthGO.food.FoodSuggestionAdapter;
import com.example.HealthGO.food.SearchFoodActivity;
import com.example.HealthGO.main_menu_screen.RecyclerViewClickInterface;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowDetailFavoriteFood extends AppCompatActivity implements RecyclerViewClickInterface {
    private RecyclerView rv_list_food;
    private FirebaseFirestore db;
    private String currentID;
    private final String TAG = "detailfavorite";
    private FoodSuggestionAdapter adapter;
    ArrayList<String> lsFoodID = new ArrayList<>();
    ArrayList<FoodCard> originList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_show_detail_favorite_food);
        currentID = FirebaseAuth.getInstance().getUid();
        rv_list_food = (RecyclerView)findViewById(R.id.rv_favorite_food_list);
        getFavoriteFood();
        showFoodList();
    }

    private void getFavoriteFood(){
        db = FirebaseFirestore.getInstance();
        db.collection("user")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if(currentID.equals(document.getString("ID"))){
                                ArrayList<String> lstemp = (ArrayList<String>)document.get("favoritefood");
                                if(lstemp.size() != 0){
                                    for(int i = lstemp.size() - 1; i >= 0; i--){
                                        lsFoodID.add(lstemp.get(i));
                                    }
                                }
                            }
                        }
                    } else {
                        Log.e("FIRE STORE", "Error getting documents: ", task.getException());
                    }
                });
    }



    private void showFoodList(){
        db = FirebaseFirestore.getInstance();
        db.collection("food")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            for(String i : lsFoodID){
                                if(i.equals(document.getString("id"))){
                                    String Title = document.getString("Title");
                                    String Description = document.getString("Brief description");
                                    String ImgUrl = document.getString("URL");
                                    String Source = document.getString("Source");
                                    FoodCard temp = new FoodCard(Title, Description, ImgUrl, Source);
                                    temp.setId(i);
                                    originList.add(temp);
                                }
                            }
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                        rv_list_food.setLayoutManager(linearLayoutManager);

                        adapter = new FoodSuggestionAdapter(ShowDetailFavoriteFood.this, originList, this);
                        rv_list_food.setAdapter(adapter);

                    } else {
                        Log.d(TAG, "getFoodData: ", task.getException());
                    }
                });
    }

    @Override
    public void onItemClick(int position) {
        Intent openLinkIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(originList.get(position).getSource()));
        startActivity(openLinkIntent);
    }

    @Override
    public void onLongItemClick(int position) {
        String FoodID = originList.get(position).getId();
        String UserID = FirebaseAuth.getInstance().getUid();

        DocumentReference docRef = db.collection("user").document(UserID);
        docRef.update("favoritefood", FieldValue.arrayRemove(FoodID));

        Toast.makeText(this, "Đã xóa khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();

        recreate();
    }
}
