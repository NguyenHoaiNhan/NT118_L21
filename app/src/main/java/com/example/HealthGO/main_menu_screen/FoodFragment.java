package com.example.HealthGO.main_menu_screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HealthGO.R;

import java.util.ArrayList;
import java.util.List;

public class FoodFragment extends Fragment {

    private RecyclerView recyclerView;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

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

        List<FoodCard> list = new ArrayList<>();

        list.add(new FoodCard("Gà kho", "Cách chế biến gà kho", R.drawable.ll_avatar));
        list.add(new FoodCard("Thịt kho", "Cách chế biến thịt kho", R.drawable.ll_avatar));
        list.add(new FoodCard("Gà luộc", "Cách chế biến gà luộc", R.drawable.ll_avatar));

        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(view.getContext(), R.anim.anim_left_to_right);
        recyclerView.setLayoutAnimation(layoutAnimationController);

        FoodAdapter foodAdapter = new FoodAdapter(view.getContext(), list);
        recyclerView.setAdapter(foodAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }
}