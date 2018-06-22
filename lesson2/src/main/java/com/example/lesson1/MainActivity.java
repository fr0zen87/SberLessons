package com.example.lesson1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;
    public static final String DATA_STRING = "data";

    private TextView textView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
    }

    public void startActivity2(View view) {
        intent = Activity2.newIntent(this)
                .putExtra(DATA_STRING, textView.getText().toString());
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void startActivity3(View view) {
        intent = Activity3.newIntent(this);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                String dataString = data.getStringExtra(DATA_STRING);
                textView.setText(dataString);
            }
        }
    }
}
