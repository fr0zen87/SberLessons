package com.example.lesson6.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;

import com.example.lesson6.fragments.Fragment2;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Service2 extends IntentService {

    public Service2() {
        super("Service2");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            Random random = new Random();
            String text;
            int color;
            int button;
            Intent broadcastIntent = new Intent(Fragment2.BROADCAST_ACTION_2);
            for (int i = 1; i <= 100; i++) {
                color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
                text = "Iter: " + i;
                button = random.nextInt(9) + 1;
                broadcastIntent.putExtra(Fragment2.BUTTON, button);
                broadcastIntent.putExtra(Fragment2.TEXT_RETURN, text);
                broadcastIntent.putExtra(Fragment2.COLOR_RETURN, color);
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
        return new Intent(context, Service2.class);
    }
}
