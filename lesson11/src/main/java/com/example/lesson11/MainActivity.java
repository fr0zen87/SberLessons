package com.example.lesson11;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private MyAidl myAidl;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            myAidl = MyAidl.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            myAidl = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = findViewById(R.id.editText);
        Button writeButton = findViewById(R.id.write_button);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    myAidl.write(editText.getText().toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        final TextView textView = findViewById(R.id.textView);
        Button readButton = findViewById(R.id.read_button);
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String s = myAidl.read();
                    textView.setText(s);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, MyService.class);
        intent.setAction(MyAidl.class.getName());
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(mConnection);
    }
}
