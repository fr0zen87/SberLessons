package com.example.lesson8.fragments.fragment3;

import android.content.Context;
import android.os.Handler;
import android.support.v4.content.AsyncTaskLoader;

import java.util.concurrent.TimeUnit;

public class MyLoader3 extends AsyncTaskLoader<Void> {

    private Handler handler;

    public MyLoader3(Context context, Handler handler) {
        super(context);
        this.handler = handler;
    }

    @Override
    public Void loadInBackground() {
        for (int i = 1; i <= 10; i++) {
            handler.sendEmptyMessage(i);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
