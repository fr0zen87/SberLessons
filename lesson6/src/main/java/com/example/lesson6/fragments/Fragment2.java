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
import com.example.lesson6.services.Service2;

public class Fragment2 extends Fragment {

    public static final String BROADCAST_ACTION_2 = "com.example.lesson6.fragments.BROADCAST_ACTION_2";
    public static final String BUTTON = "button";
    public static final String TEXT_RETURN = "text return";
    public static final String COLOR_RETURN = "color return";

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;

    private BroadcastReceiver2 receiver2;
    private IntentFilter intentFilter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);

        button1 = view.findViewById(R.id.fr2_button1);
        button2 = view.findViewById(R.id.fr2_button2);
        button3 = view.findViewById(R.id.fr2_button3);
        button4 = view.findViewById(R.id.fr2_button4);
        button5 = view.findViewById(R.id.fr2_button5);
        button6 = view.findViewById(R.id.fr2_button6);
        button7 = view.findViewById(R.id.fr2_button7);
        button8 = view.findViewById(R.id.fr2_button8);
        button9 = view.findViewById(R.id.fr2_button9);

        receiver2 = new BroadcastReceiver2();
        intentFilter = new IntentFilter(BROADCAST_ACTION_2);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(receiver2, intentFilter);

        Intent intent = Service2.newIntent(getContext());
        getActivity().startService(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver2);
    }

    private class BroadcastReceiver2 extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int button = intent.getIntExtra(BUTTON, 1);
            int color = intent.getIntExtra(COLOR_RETURN, 1);
            String text = intent.getStringExtra(TEXT_RETURN);
            switch (button) {
                case 1: {
                }
                case 3: {
                }
                case 7: {
                    button1.setTextColor(color);
                    button1.setText(text);
                    button3.setTextColor(color);
                    button3.setText(text);
                    button7.setTextColor(color);
                    button7.setText(text);
                    break;
                }
                case 2: {
                }
                case 4: {
                }
                case 8: {
                    button2.setTextColor(color);
                    button2.setText(text);
                    button4.setTextColor(color);
                    button4.setText(text);
                    button8.setTextColor(color);
                    button8.setText(text);
                    break;
                }
                case 5: {
                }
                case 6: {
                }
                case 9: {
                    button5.setTextColor(color);
                    button5.setText(text);
                    button6.setTextColor(color);
                    button6.setText(text);
                    button9.setTextColor(color);
                    button9.setText(text);
                    break;
                }
            }
        }
    }
}
