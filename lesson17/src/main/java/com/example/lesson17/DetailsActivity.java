package com.example.lesson17;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView textView = findViewById(R.id.details_text_view);
        textView.setText(getIntent().getStringExtra(MainActivity.FIO));
    }
}
