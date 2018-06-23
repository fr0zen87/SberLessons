package com.example.lesson4;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String BROADCAST_ACTION = "com.example.lesson4.MyBroadcastReceiver";
    public static final String CHANGE = "change";
    public static final String STATE = "state";

    private TextView textView;

    private MyBroadcastReceiver receiver;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        Button startServiceButton = findViewById(R.id.startService);
        Button changeStateButton = findViewById(R.id.changeState);

        startServiceButton.setOnClickListener(listener);
        changeStateButton.setOnClickListener(listener);

        receiver = new MyBroadcastReceiver();
        intentFilter = new IntentFilter(BROADCAST_ACTION);
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

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.startService: {
                    intent = MyService.newIntent(MainActivity.this)
                            .putExtra(CHANGE, false);
                    startService(intent);
                }
                case R.id.changeState: {
                    intent = MyService.newIntent(MainActivity.this)
                            .putExtra(CHANGE, true);
                    startService(intent);
                }
            }
        }
    };

    private class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra(STATE);
            textView.setText(data);
        }
    }
}
