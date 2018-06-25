package com.example.lesson8;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.lesson8.fragments.Fragment1;
import com.example.lesson8.fragments.Fragment2;
import com.example.lesson8.fragments.Fragment3;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.frameLayout1, new Fragment1()).commit();
        fragmentManager.beginTransaction().add(R.id.frameLayout2, new Fragment2()).commit();
        fragmentManager.beginTransaction().add(R.id.frameLayout3, new Fragment3()).commit();
    }
}
