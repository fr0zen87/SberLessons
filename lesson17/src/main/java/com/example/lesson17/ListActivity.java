package com.example.lesson17;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

public class ListActivity extends AppCompatActivity implements ListAdapter.MyOnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        ListAdapter adapter = new ListAdapter(MainActivity.FioStorage.getInstance().getData(), this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void listItemClicked(String fio) {
        Intent intent = new Intent(this, DetailsActivity.class)
                .putExtra(MainActivity.FIO, fio);
        startActivity(intent);
    }
}
