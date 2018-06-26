package com.example.lesson8;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.lesson8.fragments.fragment1.Fragment1;
import com.example.lesson8.fragments.fragment2.Fragment2;
import com.example.lesson8.fragments.fragment3.Fragment3;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.frameLayout1, new Fragment1()).commit();
        fragmentManager.beginTransaction().add(R.id.frameLayout2, new Fragment2()).commit();
        fragmentManager.beginTransaction().add(R.id.frameLayout3, new Fragment3()).commit();
    }
}
