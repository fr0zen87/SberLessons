package com.example.lesson16;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "calculator.db";

    private static final String SQL_CREATE_DB =
            "CREATE TABLE calculator (id INTEGER PRIMARY KEY AUTOINCREMENT, result REAL)";

    private static final String SQL_DROP_DB = "DROP TABLE IF EXISTS calculator";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQL_DROP_DB);
        sqLiteDatabase.execSQL(SQL_CREATE_DB);
    }

    public void dropTables(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_DROP_DB);
    }
}
