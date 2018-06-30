package com.example.lesson6.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lesson6.R;
import com.example.lesson6.services.Service1;

public class Fragment1 extends Fragment {

    public static final String BROADCAST_ACTION_1 = "com.example.lesson6.fragments.BROADCAST_ACTION_1";
    public static final String COLOR_RETURN = "color";
    public static final String COLOR_BUTTON = "color button";

    private Button button1;
    private Button button2;
    private Button button3;

    private BroadcastReceiver1 receiver1;
    private IntentFilter intentFilter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button1 = view.findViewById(R.id.fr1_button1);
        button2 = view.findViewById(R.id.fr1_button2);
        button3 = view.findViewById(R.id.fr1_button3);

        receiver1 = new BroadcastReceiver1();
        intentFilter = new IntentFilter(BROADCAST_ACTION_1);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(receiver1, intentFilter);

        Intent intent = Service1.newIntent(getContext());
        getActivity().startService(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver1);
    }

    private class BroadcastReceiver1 extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int color = intent.getIntExtra(COLOR_RETURN, 1);
            int colorButton = intent.getIntExtra(COLOR_BUTTON, 1);
            switch (colorButton) {
                case 1: {
                    button1.setTextColor(color);
                    break;
                }
                case 2: {
                    button2.setTextColor(color);
                    break;
                }
                case 3: {
                    button3.setTextColor(color);
                    break;
                }
            }
        }
    }
}
