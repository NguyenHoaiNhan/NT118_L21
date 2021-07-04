package com.example.HealthGO.food;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HealthGO.MainActivity;
import com.example.HealthGO.R;
import com.example.HealthGO.main_menu_screen.BottomNavigation;
import com.example.HealthGO.main_menu_screen.FoodFragment;
import com.example.HealthGO.main_menu_screen.RecyclerViewClickInterface;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchFoodActivity extends AppCompatActivity implements RecyclerViewClickInterface{

    public static final String TAG = "SearchFoodActivity";
    private EditText etInputSearch;
    private RecyclerView rv_suggestion_food;
    private FoodSuggestionAdapter adapter;
    private ArrayList<FoodCard> suggestionList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_search_food);
        initComponents();

        findFood("");

        etInputSearch.requestFocus();

        etInputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = etInputSearch.getText().toString();
                findFood(input);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    public void initComponents(){
        Log.d(TAG, "initComponents: Initializing");
        rv_suggestion_food = (RecyclerView)findViewById(R.id.rv_suggestion_food);
        etInputSearch = (EditText)findViewById(R.id.et_search_food);
        suggestionList = new ArrayList<>();
        Log.d(TAG, "initComponent: Successful");
    }

    public void findFood(String name){
        Log.d(TAG, "getFoodData: Initializing");

        ArrayList<FoodCard> originList = new ArrayList<>();

        db = FirebaseFirestore.getInstance();
        db.collection("food")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String Title = document.getString("Title");
                            String Description = document.getString("Brief description");
                            String ImgUrl = document.getString("URL");
                            String Source = document.getString("Source");
                            String Id = document.getString("id");
                            originList.add(new FoodCard(Id, Title, Description, ImgUrl, Source));
                        }

                        suggestionList.clear();
                        for(FoodCard food : originList){
                            if(food.getTitle().toLowerCase().contains(name.toLowerCase())){
                                suggestionList.add(food);
                            }

                        }

                        showResult(suggestionList);

                    } else {
                        Log.d(TAG, "getFoodData: ", task.getException());
                    }
                });

    }

    public void showResult(ArrayList<FoodCard> ls){
        Log.d(TAG, "showResult: Initializing");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_suggestion_food.setLayoutManager(linearLayoutManager);

        adapter = new FoodSuggestionAdapter(SearchFoodActivity.this, ls, this);
        rv_suggestion_food.setAdapter(adapter);

        Log.d(TAG, "showResult: Successful");
    }

    @Override
    public void onItemClick(int position) {
        Intent openLinkIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(suggestionList.get(position).getSource()));
        startActivity(openLinkIntent);
    }

    @Override
    public void onLongItemClick(int position) {

    }
}
