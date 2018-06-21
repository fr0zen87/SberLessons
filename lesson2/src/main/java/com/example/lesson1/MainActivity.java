package com.example.lesson1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;

    private TextView textView;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
    }

    public void startActivity2(View view) {
        intent = new Intent(this, Activity2.class)
                .putExtra("data", textView.getText().toString());
        startActivity(intent);
    }

    public void startActivity3(View view) {
        intent = new Intent(this, Activity3.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            String dataString = intent.getStringExtra("data");
            textView.setText(dataString);
        }
    }
}
