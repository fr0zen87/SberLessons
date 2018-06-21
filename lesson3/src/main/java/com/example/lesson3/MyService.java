package com.example.lesson3;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MyService extends Service {

    private ExecutorService executorService;
    private Messenger messenger = new Messenger(new IncomingHandler());

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        executorService = Executors.newFixedThreadPool(1);
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        Runnable job = new Runnable() {
            @Override
            public void run() {
                Log.v("MyService", "Service working");
                stopSelf();
            }
        };
        executorService.execute(job);
        return START_NOT_STICKY;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, MyService.class);
    }

    private class IncomingHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            for (int i = 1; i <= 5; i++) {
                Date date = new Date();
                Message message = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putString("date", date.toString());
                message.setData(bundle);
                try {
                    messenger.send(message);
                    TimeUnit.SECONDS.sleep(5);
                } catch (RemoteException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
