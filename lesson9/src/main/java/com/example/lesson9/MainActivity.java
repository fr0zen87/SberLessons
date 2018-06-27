package com.example.lesson9;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<MyNote> notes = new ArrayList<>();

    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initNotes();

        RecyclerView recyclerView = findViewById(R.id.list);
        adapter = new MyNotesAdapter(notes);
        recyclerView.setAdapter(adapter);
    }

    private void initNotes() {
        notes.add(new MyNote("name1", "date1", "content1"));
        notes.add(new MyNote("name2", "date2", "content2"));
        notes.add(new MyNote("name3", "date3", "content3"));
        notes.add(new MyNote("name4", "date4", "content4"));
        notes.add(new MyNote("name5", "date5", "content5"));
    }
}
