package com.example.lesson6.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.lesson6.fragments.Fragment4;

import java.util.concurrent.TimeUnit;

public class Service4 extends IntentService {

    public Service4() {
        super("Service4");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            int angle = 0;
            Intent broadcastIntent = new Intent(Fragment4.BROADCAST_ACTION_4);
            for (int i = 1; i <= 100; i++) {
                angle += 10;
                if (angle == 360) {
                    angle = 0;
                }
                broadcastIntent.putExtra(Fragment4.ANGLE, angle);
                sendBroadcast(broadcastIntent);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, Service4.class);
    }
}
