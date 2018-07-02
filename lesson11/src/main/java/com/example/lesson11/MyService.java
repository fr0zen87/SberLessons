package com.example.lesson11;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

public class MyService extends Service {

    private static final String DATA = "data";

    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);
        return new MyAidl.Stub() {
            @Override
            public String read() throws RemoteException {
                return sharedPreferences.getString(DATA, "str");
            }

            @Override
            public void write(String s) throws RemoteException {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(DATA, s);
                editor.apply();
            }
        };
    }
}
