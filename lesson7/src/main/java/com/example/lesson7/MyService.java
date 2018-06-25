package com.example.lesson7;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.lesson7.entities.Phone;

import java.util.concurrent.TimeUnit;

public class MyService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Phone phone;
                Intent broadcastIntent = new Intent(MainActivity.BROADCAST_ACTION);
                for (int i = 1; i <= 20; i++) {
                    phone = new Phone("name " + i, "company " + i, R.drawable.iphone);
                    broadcastIntent.putExtra(MainActivity.PHONE, phone);
                    sendBroadcast(broadcastIntent);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                stopSelf();
            }
        }).start();
        return START_NOT_STICKY;
    }
}