package com.example.lesson5;

import android.app.IntentService;
import android.content.Intent;

import com.example.lesson5.fragments.Fragment1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss", Locale.getDefault());
        String data;
        Intent broadcastIntent = new Intent(Fragment1.BROADCAST_ACTION);
        for (int i = 0; i < 100; i++) {
            data = simpleDateFormat.format(new Date());
            broadcastIntent.putExtra(Fragment1.BROADCAST_DATA, data);
            sendBroadcast(broadcastIntent);
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
