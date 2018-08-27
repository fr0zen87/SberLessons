package com.example.lesson25;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        List<String> data = intent.getStringArrayListExtra("data");
        int position = intent.getIntExtra("position", 0);

        ViewPager viewPager = findViewById(R.id.pager);
        DetailsAdapter adapter = new DetailsAdapter(getSupportFragmentManager(), data);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
    }
}
