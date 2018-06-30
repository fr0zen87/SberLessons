package com.example.lesson6.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lesson6.R;
import com.example.lesson6.services.Service4;

public class Fragment4 extends Fragment {

    public static final String BROADCAST_ACTION_4 = "com.example.lesson6.fragments.BROADCAST_ACTION_4";
    public static final String ANGLE = "angle";

    private Button button1;
    private Button button2;

    private BroadcastReceiver4 receiver4;
    private IntentFilter intentFilter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment4, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button1 = view.findViewById(R.id.fr4_button1);
        button2 = view.findViewById(R.id.fr4_button2);

        receiver4 = new BroadcastReceiver4();
        intentFilter = new IntentFilter(BROADCAST_ACTION_4);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(receiver4, intentFilter);

        Intent intent = Service4.newIntent(getContext());
        getActivity().startService(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver4);
    }

    private class BroadcastReceiver4 extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int angle = intent.getIntExtra(ANGLE, 0);
            ConstraintLayout.LayoutParams params1 = (ConstraintLayout.LayoutParams) button1.getLayoutParams();
            ConstraintLayout.LayoutParams params2 = (ConstraintLayout.LayoutParams) button2.getLayoutParams();
            params1.circleAngle = angle;
            params2.circleAngle = angle;
            button1.setLayoutParams(params1);
            button2.setLayoutParams(params2);
        }
    }
}
