package com.example.lesson7;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.lesson7.adapters.DataAdapter;
import com.example.lesson7.entities.Phone;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String BROADCAST_ACTION = "com.example.lesson7.MyBroadcastReceiver";
    public static final String PHONES = "phones";

    private List<Phone> phones = new ArrayList<>();
    private DataAdapter adapter;

    private MyBroadcastReceiver receiver;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receiver = new MyBroadcastReceiver();
        intentFilter = new IntentFilter(BROADCAST_ACTION);

        RecyclerView recyclerView = findViewById(R.id.list);
        adapter = new DataAdapter(phones);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, intentFilter);

        startService(MyService.getIntent(this));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<Phone> phones = intent.getParcelableArrayListExtra(PHONES);
            adapter.onNewPhones(phones);
        }
    }
}