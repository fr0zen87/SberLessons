package com.example.lesson16;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("result", sum(2, 5));
        db.insert("calculator", null, contentValues);
        contentValues.put("result", diff(2, 5));
        db.insert("calculator", null, contentValues);
        contentValues.put("result", mult(2, 5));
        db.insert("calculator", null, contentValues);
        contentValues.put("result", div(2, 5));
        db.insert("calculator", null, contentValues);

        db.close();
    }

    private double sum(double a, double b) {
        return a + b;
    }

    public double diff(double a, double b) {
        return a - b;
    }

    public double mult(double a, double b) {
        return a * b;
    }

    public double div(double a, double b) {
        return a / b;
    }
}
