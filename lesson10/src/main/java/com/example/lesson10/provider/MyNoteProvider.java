package com.example.lesson10.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.lesson10.db.MyNoteDatabase;
import com.example.lesson10.entities.MyNote;

public class MyNoteProvider extends ContentProvider {

    MyNoteDatabase db;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static final int NOTES = 100;
    public static final int NOTES_ID = 101;

    private static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(NotesContract.CONTENT_AUTHORITY, NotesContract.TABLE_NAME, NOTES);
        matcher.addURI(NotesContract.CONTENT_AUTHORITY, NotesContract.TABLE_NAME + "/#", NOTES_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        db = MyNoteDatabase.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch(match){
            case NOTES:
                return NotesContract.CONTENT_TYPE;
            case NOTES_ID:
                return NotesContract.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI: "+ uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        long recordId;

        switch(match) {
            case NOTES:
                MyNote myNote = Converter.convertValuesToMyNote(values);
                recordId = db.myNoteDao().insert(myNote);
                if(recordId > 0){
                    returnUri = NotesContract.buildNotesUri(recordId);
                }
                else{
                    throw new android.database.SQLException("Failed to insert: " + uri.toString());
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);

        if(match != NOTES && match != NOTES_ID) {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        MyNote myNote = null;
        if(match == NOTES_ID) {
            long taskId = NotesContract.getNotesId(uri);
            myNote = new MyNote((int) taskId);
        }
        return (int) db.myNoteDao().delete(myNote);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
