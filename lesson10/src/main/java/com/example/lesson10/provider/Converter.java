package com.example.lesson10.provider;

import android.content.ContentValues;

import com.example.lesson10.entities.MyNote;

public class Converter {

    public static MyNote convertValuesToNote(ContentValues values) {
        return new MyNote(values.getAsString(NotesContract.Columns.NAME),
                values.getAsString(NotesContract.Columns.DATE),
                values.getAsString(NotesContract.Columns.CONTENT));
    }
}
