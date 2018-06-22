package com.example.lesson3;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    public static final String MESSAGE = "message";

    private TextView textView;

    private Messenger activityMessenger = new Messenger(new ResponseHandler());
    private Messenger myServiceMessenger;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myServiceMessenger = new Messenger(service);

            Message message = Message.obtain();
            message.replyTo = activityMessenger;
            try {
                myServiceMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myServiceMessenger = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textView = findViewById(R.id.textView);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = MyService.newIntent(this);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(serviceConnection);
        myServiceMessenger = null;
    }

    @SuppressLint("HandlerLeak")
    private class ResponseHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String dateTime = bundle.getString(MESSAGE);
            textView.setText(dateTime);
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, SecondActivity.class);
    }
}
