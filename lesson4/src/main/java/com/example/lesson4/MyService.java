package com.example.lesson4;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MyService extends Service {

    private ExecutorService executor;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        executor = Executors.newFixedThreadPool(2);
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        Runnable job = new Runnable() {
            @Override
            public void run() {
                Intent broadcastIntent = new Intent(MainActivity.BROADCAST_ACTION);
                StateManager stateManager = StateManager.getInstance();
                boolean isChangeNeeded = intent.getBooleanExtra(MainActivity.CHANGE, false);
                if (isChangeNeeded) {
                    stateManager.changeState();
                    broadcastIntent.putExtra(MainActivity.STATE, stateManager.getState());
                    sendBroadcast(broadcastIntent);
                } else {
                    for (int i = 0; i < 100; i++) {
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
                stopSelf();
            }
        };
        executor.execute(job);
        return START_NOT_STICKY;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, MyService.class);
    }
}
