package com.example.lesson10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lesson10.entities.MyNote;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mContentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        mNameEditText = findViewById(R.id.name_edit);
        mContentEditText = findViewById(R.id.content_edit);

        final Button button = findViewById(R.id.add_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mNameEditText.getText()) || TextUtils.isEmpty(mContentEditText.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
                    String name = mNameEditText.getText().toString();
                    String date = format.format(new Date());
                    String content = mContentEditText.getText().toString();
                    MyNote myNote = new MyNote(name, date, content);
                    replyIntent.putExtra(MainActivity.MY_NOTE, myNote);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
