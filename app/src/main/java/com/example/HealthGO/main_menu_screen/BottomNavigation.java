package com.example.HealthGO.main_menu_screen;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.HealthGO.R;

public class BottomNavigation extends AppCompatActivity {
    MeowBottomNavigation bottomNavigation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_bottom_navigation);

        bottomNavigation = (MeowBottomNavigation) findViewById(R.id.bottom_navigation);

        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_food));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_map));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_workout));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_person));

        FoodFragment foodFragment =new FoodFragment();
        MapFragment mapFragment =new MapFragment();
        WorkoutFragment workoutFragment=new WorkoutFragment();
        PersonFragment personFragment=new PersonFragment();
        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment = null;
                switch (item.getId()) {
                    case 1:
                        fragment =foodFragment;
                        break;
                    case 2:
                        fragment = mapFragment;
                        break;
                    case 3:
                        fragment = workoutFragment;
                        break;
                    case 4:
                        fragment = personFragment;
                        break;
                }
                loadFragment(fragment);
            }
        });
        bottomNavigation.setCount(1, "10");
        bottomNavigation.show(1, true);

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(), "You selected " + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(), "You reselected " + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }
}