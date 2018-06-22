package com.example.lesson6.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;

import com.example.lesson6.fragments.Fragment1;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Service1 extends IntentService {

    public Service1() {
        super("Service1");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            Random random = new Random();
            int color;
            int colorButton;
            Intent broadcastIntent = new Intent(Fragment1.BROADCAST_ACTION_1);
            for (int i = 0; i < 100; i++) {
                color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
                colorButton = random.nextInt(3) + 1;
                intent.putExtra(Fragment1.COLOR_RETURN, color);
                intent.putExtra(Fragment1.COLOR_BUTTON, colorButton);
                sendBroadcast(broadcastIntent);
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, Service1.class);
    }
}
