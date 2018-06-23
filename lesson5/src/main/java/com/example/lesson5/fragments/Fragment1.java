package com.example.lesson5.fragments;

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
import android.widget.EditText;
import android.widget.TextView;

import com.example.lesson5.MyIntentService;
import com.example.lesson5.R;

public class Fragment1 extends Fragment {

    public static final String BROADCAST_ACTION = "com.example.lesson5.fragments.MyBroadcastReceiver";
    public static final String BROADCAST_DATA = "data";

    private EditText editText;

    private MyBroadcastReceiver receiver;
    private IntentFilter intentFilter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);

        receiver = new MyBroadcastReceiver();
        intentFilter = new IntentFilter(BROADCAST_ACTION);

        editText = view.findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TextView textView = getActivity().findViewById(R.id.textView);
                if (textView != null) {
                    textView.setText(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(receiver, intentFilter);

        Intent intent = new Intent(getContext(), MyIntentService.class);
        getActivity().startService(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver);
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra(BROADCAST_DATA);
            editText.setText(data);
        }
    }
}
