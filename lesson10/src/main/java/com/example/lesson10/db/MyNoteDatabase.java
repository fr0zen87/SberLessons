package com.example.lesson10.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.lesson10.entities.MyNote;

@Database(entities = {MyNote.class}, version = 1)
public abstract class MyNoteDatabase extends RoomDatabase {

    private static MyNoteDatabase instance;

    public static MyNoteDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (MyNoteDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context, MyNoteDatabase.class, "notes_database")
                            .build();
                }
            }
        }
        return instance;
    }

    public abstract MyNoteDao myNoteDao();

    public abstract ContentProviderDao contentProviderDao();
}
