package com.example.lesson1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class Activity2 extends AppCompatActivity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        editText = findViewById(R.id.editText);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String data = getIntent().getStringExtra(MainActivity.DATA_STRING);
        editText.setText(data);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(MainActivity.DATA_STRING, editText.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, Activity2.class);
    }
}
