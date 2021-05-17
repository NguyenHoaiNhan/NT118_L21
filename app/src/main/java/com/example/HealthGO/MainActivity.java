package com.example.HealthGO;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;

import com.example.HealthGO.startup_screen.LoginAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    FloatingActionButton FaceBook, Google, Twitter;
    float v = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        init();
        animation();
    }

    private void init() {
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.viewPager);
        FaceBook = findViewById(R.id.fab_facebook);
        Google = findViewById(R.id.fab_google);
        Twitter = findViewById(R.id.fab_twitter);

        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Register"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(), this, tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setupWithViewPager(viewPager);

    }

    private void animation() {
        FaceBook.setTranslationY(300);
        Google.setTranslationY(300);
        Twitter.setTranslationY(300);
        tabLayout.setTranslationY(300);

        FaceBook.setAlpha(v);
        Google.setAlpha(v);
        Twitter.setAlpha(v);
        tabLayout.setAlpha(v);

        FaceBook.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        Google.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        Twitter.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
    }


}
