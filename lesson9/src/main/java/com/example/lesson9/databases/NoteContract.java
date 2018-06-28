package com.example.lesson9.databases;

import android.provider.BaseColumns;

public final class NoteContract {

    private NoteContract() {
    }

    public static class Note implements BaseColumns {
        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_CONTENT = "content";
    }
}
