package com.example.lesson10.utils;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.lesson10.db.MyNoteDao;
import com.example.lesson10.db.MyNoteDatabase;
import com.example.lesson10.entities.MyNote;

import java.util.List;

public class MyNoteRepository {

    private MyNoteDao mMyNoteDao;
    private LiveData<List<MyNote>> mAllNotes;

    public MyNoteRepository(Application application) {
        MyNoteDatabase db = MyNoteDatabase.getInstance(application.getApplicationContext());
        mMyNoteDao = db.myNoteDao();
        mAllNotes = mMyNoteDao.getNotes();
    }

    public LiveData<List<MyNote>> getAllNotes() {
        return mAllNotes;
    }

    public void insert(MyNote myNote) {
        new InsertAsyncTask(mMyNoteDao).execute(myNote);
    }

    public void update(MyNote myNote) {
        new UpdateAsyncTask(mMyNoteDao).execute(myNote);
    }

    public void delete(MyNote myNote) {
        new DeleteAsyncTask(mMyNoteDao).execute(myNote);
    }

    private static class InsertAsyncTask extends AsyncTask<MyNote, Void, Void> {

        private MyNoteDao mAsyncTaskDao;

        InsertAsyncTask(MyNoteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MyNote... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<MyNote, Void, Void> {

        private MyNoteDao mAsyncTaskDao;

        UpdateAsyncTask(MyNoteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MyNote... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<MyNote, Void, Void> {

        private MyNoteDao mAsyncTaskDao;

        DeleteAsyncTask(MyNoteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MyNote... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
