package com.example.HealthGO.main_menu_screen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HealthGO.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FoodFragment extends Fragment implements RecyclerViewClickInterface {

    private RecyclerView recyclerView;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private FirebaseFirestore db;
    private List<FoodCard> list = new ArrayList<>();

    public FoodFragment() {
        // Required empty public constructor
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

    private void init(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        db = FirebaseFirestore.getInstance();
        db.collection("food")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String Title = document.getString("Title");
                            String Description = document.getString("Brief description");
                            String URL = document.getString("URL");
                            String Source = document.getString("Source");

                            list.add(new FoodCard(Title, Description, URL, Source));
                        }

                        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(view.getContext(), R.anim.anim_left_to_right);
                        recyclerView.setLayoutAnimation(layoutAnimationController);

                        FoodAdapter foodAdapter = new FoodAdapter(view.getContext(), list, this);
                        recyclerView.setAdapter(foodAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                    } else {
                        Log.e("FIRE STORE", "Error getting documents: ", task.getException());
                    }
                });
    }

    @Override
    public void onItemClick(int position) {
//        Toast.makeText(getContext(), "You click " + list.get(position).getSource(), Toast.LENGTH_SHORT).show();
        Intent openLinkIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).getSource()));
        startActivity(openLinkIntent);
    }

    @Override
    public void onLongItemClick(int position) {
        Toast.makeText(getContext(), "You long click " + position, Toast.LENGTH_SHORT).show();
    }
}