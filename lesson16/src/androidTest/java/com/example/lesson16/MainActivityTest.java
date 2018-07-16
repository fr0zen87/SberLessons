package com.example.lesson16;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MainActivityTest {

    private DbHelper dbHelper;
    private SQLiteDatabase db;
    private MainActivity mainActivity;

    @Before
    public void setUp() throws Exception {
        dbHelper = new DbHelper(InstrumentationRegistry.getTargetContext());
        db = dbHelper.getWritableDatabase();
        mainActivity = new MainActivity();
    }

    @After
    public void tearDown() throws Exception {
        dbHelper.dropTables(db);
        db = null;
    }

    @Test
    public void diffTest() {
        double diff = mainActivity.diff(5, 2);
        ContentValues contentValues = new ContentValues();
        contentValues.put("result", diff);
        long row = db.insert("calculator", null, contentValues);
        Cursor cursor = db.query("calculator", null, "id = ?",
                new String[]{String.valueOf(row)}, null, null, null);
        double actual = cursor.getDouble(cursor.getColumnIndex("result"));
        assertEquals(3, actual, 0.1);
    }
}