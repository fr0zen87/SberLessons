package com.example.lesson8.fragments.fragment2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lesson8.R;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Fragment2 extends Fragment {

    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);

        textView = view.findViewById(R.id.fr2_textView);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new MyTask().execute();
    }

    private class MyTask extends AsyncTask<Void, Integer, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            Random random = new Random();
            for (int i = 0; i < 10; i++) {
                publishProgress(random.nextInt(100));
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return random.nextInt(100);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            textView.setText(String.valueOf(integer));
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            textView.setText(String.valueOf(values[0]));
        }
    }
}
