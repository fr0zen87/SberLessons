package com.example.lesson9.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.lesson9.db.NoteContract.*;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "notes.db";

    private static final String SQL_CREATE_NOTES_DB =
            "CREATE TABLE " + Note.TABLE_NAME + " (" +
                    Note._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Note.COLUMN_NAME + " TEXT," +
                    Note.COLUMN_DATE + " TEXT," +
                    Note.COLUMN_CONTENT + " TEXT)";

    private static final String SQL_DELETE_NOTES_DB =
            "DROP TABLE IF EXISTS " + Note.TABLE_NAME;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_NOTES_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_NOTES_DB);
        db.execSQL(SQL_CREATE_NOTES_DB);
    }
}
