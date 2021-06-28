package com.example.HealthGO.food;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HealthGO.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchFoodActivity extends AppCompatActivity {

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

        etInputSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        || event.getAction() == KeyEvent.KEYCODE_ENTER){

                    String input = etInputSearch.getText().toString();
                    findFood(input);
                }
                return true;
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
                            originList.add(new FoodCard(Title, Description, ImgUrl));
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

        adapter = new FoodSuggestionAdapter(SearchFoodActivity.this, ls);
        rv_suggestion_food.setAdapter(adapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rv_suggestion_food.addItemDecoration(itemDecoration);

        Log.d(TAG, "showResult: Successful");
    }
}
