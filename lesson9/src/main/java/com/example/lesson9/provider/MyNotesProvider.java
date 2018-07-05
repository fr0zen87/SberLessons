package com.example.lesson9.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.lesson9.db.DbHelper;

public class MyNotesProvider extends ContentProvider {

    private DbHelper dbHelper;

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
        dbHelper = new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final int match = sUriMatcher.match(uri);
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        switch(match){
            case NOTES:
                queryBuilder.setTables(NotesContract.TABLE_NAME);
                break;
            case NOTES_ID:
                queryBuilder.setTables(NotesContract.TABLE_NAME);
                long id = NotesContract.getNoteId(uri);
                queryBuilder.appendWhere(NotesContract.Columns._ID + " = " + id);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
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
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        final SQLiteDatabase db;
        Uri returnUri;
        long recordId;

        switch(match){
            case NOTES:
                db = dbHelper.getWritableDatabase();
                recordId = db.insert(NotesContract.TABLE_NAME, null, values);
                if(recordId > 0){
                    returnUri = NotesContract.buildNotesUri(recordId);
                }
                else{
                    throw new SQLException("Failed to insert: " + uri.toString());
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: "+ uri);
        }
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectionCriteria = "";

        if(match != NOTES && match != NOTES_ID) {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        if(match == NOTES_ID) {
            long id = NotesContract.getNoteId(uri);
            selectionCriteria = NotesContract.Columns._ID + " = " + id;
        }
        return db.delete(NotesContract.TABLE_NAME, selectionCriteria, selectionArgs);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectionCriteria = "";

        if(match != NOTES && match != NOTES_ID) {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        if(match == NOTES_ID) {
            long id = NotesContract.getNoteId(uri);
            selectionCriteria = NotesContract.Columns._ID + " = " + id;
        }
        return db.update(NotesContract.TABLE_NAME, values, selectionCriteria, selectionArgs);
    }
}
