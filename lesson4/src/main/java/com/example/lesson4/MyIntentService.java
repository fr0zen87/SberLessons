package com.example.lesson4;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import java.util.concurrent.TimeUnit;

public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Intent broadcastIntent = new Intent(MainActivity.BROADCAST_ACTION);
        StateManager stateManager = StateManager.getInstance();
        boolean isChangeNeeded = intent.getBooleanExtra(MainActivity.CHANGE, false);
        if (isChangeNeeded) {
            stateManager.changeState();
            broadcastIntent.putExtra(MainActivity.STATE, stateManager.getState());
            sendBroadcast(broadcastIntent);
        } else {
            while (true) {
                stateManager.changeState();
                broadcastIntent.putExtra(MainActivity.STATE, stateManager.getState());
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
        return new Intent(context, MyIntentService.class);
    }
}
