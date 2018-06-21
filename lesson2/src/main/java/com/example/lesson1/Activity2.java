package com.example.lesson1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Activity2 extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        textView = findViewById(R.id.showText);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String data = getIntent().getStringExtra("data");
        textView.setText(data);
    }
}
