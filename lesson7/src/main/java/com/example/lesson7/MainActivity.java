package com.example.lesson7;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.lesson7.adapters.DataAdapter;
import com.example.lesson7.adapters.DataAdapter2;
import com.example.lesson7.adapters.DataAdapter3;
import com.example.lesson7.entities.Phone;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String BROADCAST_ACTION = "com.example.lesson7.MyBroadcastReceiver";
    public static final String PHONE = "phone";

    private List<Phone> phones = new ArrayList<>();
    private RecyclerView recyclerView;
    private DataAdapter adapter;
    private DataAdapter2 adapter2;
    private DataAdapter3 adapter3;

    private MyBroadcastReceiver receiver;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receiver = new MyBroadcastReceiver();
        intentFilter = new IntentFilter(BROADCAST_ACTION);

        recyclerView = findViewById(R.id.list);
        adapter = new DataAdapter(phones);
        adapter2 = new DataAdapter2(phones);
        adapter3 = new DataAdapter3(phones);
        recyclerView.setAdapter(adapter);
        recyclerView.setAdapter(adapter2);
        recyclerView.setAdapter(adapter3);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Phone phone = intent.getParcelableExtra(PHONE);
            phones.add(phone);
            adapter.notifyDataSetChanged();
            adapter2.notifyDataSetChanged();
            adapter3.notifyDataSetChanged();
        }
    }
}