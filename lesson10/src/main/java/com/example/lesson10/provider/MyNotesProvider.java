package com.example.lesson10.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.lesson10.db.ContentProviderDao;
import com.example.lesson10.db.MyNoteDatabase;
import com.example.lesson10.entities.MyNote;

public class MyNotesProvider extends ContentProvider {

    private ContentProviderDao dao;

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
        MyNoteDatabase db = MyNoteDatabase.getInstance(getContext());
        dao = db.contentProviderDao();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final int match = sUriMatcher.match(uri);
        switch(match){
            case NOTES:
                return dao.selectAll();
            case NOTES_ID:
                long id = NotesContract.getNoteId(uri);
                return dao.selectById(id);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
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
        switch(match) {
            case NOTES:
                long recordId = 0;
                if (values != null) {
                    recordId = dao.insert(Converter.convertValuesToNote(values));
                }
                if(recordId > 0) {
                    return NotesContract.buildNotesUri(recordId);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: "+ uri);
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);

        if(match != NOTES && match != NOTES_ID) {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        if(match == NOTES_ID) {
            long id = NotesContract.getNoteId(uri);
            return dao.deleteById(id);
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);

        if(match != NOTES && match != NOTES_ID) {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        if(match == NOTES_ID) {
            long id = NotesContract.getNoteId(uri);
            MyNote myNote = null;
            if (values != null) {
                myNote = Converter.convertValuesToNote(values);
                myNote.setId(id);
            }
            return dao.update(myNote);
        }
        return 0;
    }
}
