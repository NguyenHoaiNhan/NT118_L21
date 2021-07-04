package com.example.HealthGO.main_menu_screen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HealthGO.R;
import com.example.HealthGO.food.FoodAdapter;
import com.example.HealthGO.food.FoodCard;
import com.example.HealthGO.food.SearchFoodActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FoodFragment extends Fragment implements RecyclerViewClickInterface {

    private RecyclerView recyclerView;
    private TextView et_require_searching;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private FirebaseFirestore db;
    private List<FoodCard> list = new ArrayList<>();

    public FoodFragment() {

    }

    public static FoodFragment newInstance(String param1, String param2) {
        FoodFragment fragment = new FoodFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        init(view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        list.clear();
    }

    private void init(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        et_require_searching = view.findViewById(R.id.et_require_searching);

        et_require_searching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchFoodActivity.class);
                startActivity(intent);
            }
        });

        db = FirebaseFirestore.getInstance();
        db.collection("food")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String Title = document.getString("Title");
                            double Rating = document.getDouble("Rating");
                            // URL => Image of the paper
                            String URL = document.getString("URL");
                            //Source => URL of the paper
                            String Source = document.getString("Source");
                            String id = document.getId();

                            list.add(new FoodCard(Title, URL, Source, Rating, id));
                        }

                        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(view.getContext(), R.anim.anim_left_to_right);
                        recyclerView.setLayoutAnimation(layoutAnimationController);

                        FoodAdapter foodAdapter = new FoodAdapter(view.getContext(), list, this);
                        recyclerView.setAdapter(foodAdapter);

                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                        recyclerView.setLayoutManager(gridLayoutManager);


                    } else {
                        Log.e("FIRE STORE", "Error getting documents: ", task.getException());
                    }
                });
    }

    @Override
    public void onItemClick(int position) {
        Intent openLinkIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).getSource()));
        startActivity(openLinkIntent);
    }

    @Override
    public void onLongItemClick(int position) {
        String FoodID = list.get(position).getId();
        String UserID = FirebaseAuth.getInstance().getUid();



        DocumentReference docRef = db.collection("user").document(UserID);
        docRef.update("favoritefood", FieldValue.arrayUnion(FoodID));

        Toast.makeText(getContext(), "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
    }
}