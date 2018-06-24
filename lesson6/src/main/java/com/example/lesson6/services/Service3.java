package com.example.lesson6.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.lesson6.fragments.Fragment3;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Service3 extends IntentService {

    public Service3() {
        super("Service3");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            Random random = new Random();
            String text;
            int textButton;
            Intent broadcastIntent = new Intent(Fragment3.BROADCAST_ACTION_3);
            for (int i = 1; i <= 100; i++) {
                text = "Iteration: " + i;
                textButton = random.nextInt(3) + 1;
                broadcastIntent.putExtra(Fragment3.TEXT_BUTTON, textButton);
                broadcastIntent.putExtra(Fragment3.TEXT_RETURN, text);
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
        return new Intent(context, Service3.class);
    }
}
