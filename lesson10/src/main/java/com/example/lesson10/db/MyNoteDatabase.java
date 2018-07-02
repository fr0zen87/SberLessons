package com.example.lesson10.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.lesson10.entities.MyNote;

@Database(entities = {MyNote.class}, version = 1)
public abstract class MyNoteDatabase extends RoomDatabase {

    private static MyNoteDatabase instance;

    public static MyNoteDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (MyNoteDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context, MyNoteDatabase.class, "notes_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return instance;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(instance).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private MyNoteDao dao;

        public PopulateDbAsync(MyNoteDatabase db) {
            dao = db.myNoteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            dao.insert(new MyNote("note 1", "date 1", "content 1"));
            dao.insert(new MyNote("note 2", "date 2", "content 2"));
            dao.insert(new MyNote("note 3", "date 3", "content 3"));
            dao.insert(new MyNote("note 4", "date 4", "content 4"));
            dao.insert(new MyNote("note 5", "date 5", "content 5"));
            return null;
        }
    }

    public abstract MyNoteDao myNoteDao();
}
