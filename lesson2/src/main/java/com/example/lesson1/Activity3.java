package com.example.lesson1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class Activity3 extends AppCompatActivity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        editText = findViewById(R.id.editText);
    }

    public void send(View view) {
        Intent intent = new Intent()
                .putExtra("data", editText.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}
