package com.example.HealthGO.gym;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.HealthGO.R;


public class GYMActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gym);
        LinearLayout linear_gym1 = findViewById(R.id.linear_gym1);
        LinearLayout linear_gym2 = findViewById(R.id.linear_gym2);
        LinearLayout linear_gym3 = findViewById(R.id.linear_gym3);
        LinearLayout linear_gym4 = findViewById(R.id.linear_gym4);
        LinearLayout linear_gym5 = findViewById(R.id.linear_gym5);
        LinearLayout linear_gym6 = findViewById(R.id.linear_gym6);
        LinearLayout linear_gym7 = findViewById(R.id.linear_gym7);




        linear_gym1.setOnClickListener(v -> startIntent("Video các bài tập YOGA cơ bản"));
        linear_gym2.setOnClickListener(v -> startIntent("Video các bài tập cơ ngực"));
        linear_gym3.setOnClickListener(v -> startIntent("Video các bài tập cơ bụng"));
        linear_gym4.setOnClickListener(v -> startIntent("Video các bài tập cơ mông"));
        linear_gym5.setOnClickListener(v -> startIntent("Video các bài tập thể dụng tốt cho sức khoẻ"));

    }

    private void startIntent(String query)
    {
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, query); // query contains search string
        startActivity(intent);

    }

}
