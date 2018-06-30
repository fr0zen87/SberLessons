package com.example.lesson6.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lesson6.R;
import com.example.lesson6.services.Service3;

public class Fragment3 extends Fragment {

    public static final String BROADCAST_ACTION_3 = "com.example.lesson6.fragments.BROADCAST_ACTION_3";
    public static final String TEXT_RETURN = "text result";
    public static final String TEXT_BUTTON = "text button";

    private Button button1;
    private Button button2;
    private Button button3;

    private BroadcastReceiver3 receiver3;
    private IntentFilter intentFilter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button1 = view.findViewById(R.id.fr3_button1);
        button2 = view.findViewById(R.id.fr3_button2);
        button3 = view.findViewById(R.id.fr3_button3);

        receiver3 = new BroadcastReceiver3();
        intentFilter = new IntentFilter(BROADCAST_ACTION_3);

        button2.addTextChangedListener(textWatcher);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(receiver3, intentFilter);

        Intent intent = Service3.newIntent(getContext());
        getActivity().startService(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver3);
    }

    private class BroadcastReceiver3 extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String text = intent.getStringExtra(TEXT_RETURN);
            int colorButton = intent.getIntExtra(TEXT_BUTTON, 1);
            switch (colorButton) {
                case 1: {
                    button1.setText(text);
                    break;
                }
                case 2: {
                    button2.setText(text);
                    break;
                }
                case 3: {
                    button3.setText(text);
                    break;
                }
            }
        }
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            Button button = getActivity().findViewById(R.id.fr2_button5);
            if (button != null) {
                button.setText(button2.getText());
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}
