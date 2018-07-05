package com.example.lesson9.provider;

import android.content.ContentUris;
import android.net.Uri;

public class NotesContract {

    static final String TABLE_NAME = "notes";
    static final String CONTENT_AUTHORITY = "com.example.lesson9.provider";
    static final Uri CONTENT_AUTHORITY_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    static final String CONTENT_ITEM_TYPE= "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;

    public static class Columns {
        public static final String _ID = "_id";
        public static final String NAME = "name";
        public static final String DATE = "date";
        public static final String CONTENT = "content";

        private Columns() {

        }
    }

    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);

    static Uri buildNotesUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    static long getNoteId(Uri uri) {
        return ContentUris.parseId(uri);
    }
}
