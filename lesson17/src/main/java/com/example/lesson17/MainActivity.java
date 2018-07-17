package com.example.lesson17;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String FIO = "fio";

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = editText.getText().toString();
                if (data.equals("")) {
                    Toast.makeText(MainActivity.this, "Введите ФИО", Toast.LENGTH_SHORT).show();
                } else {
                    FioStorage.getInstance().getData().add(data);
                    Intent intent = new Intent(MainActivity.this, ListActivity.class)
                            .putExtra(FIO, data);
                    startActivity(intent);
                }
            }
        });
    }

    public static class FioStorage {

        private static final FioStorage instance = new FioStorage();
        private static List<String> data;

        public static FioStorage getInstance() {
            return instance;
        }

        private FioStorage() {
            data = new ArrayList<>();
        }

        public List<String> getData() {
            return data;
        }
    }
}
