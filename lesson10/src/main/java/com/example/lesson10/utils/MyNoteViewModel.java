package com.example.lesson10.utils;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.lesson10.entities.MyNote;

import java.util.List;

public class MyNoteViewModel extends AndroidViewModel {

    private MyNoteRepository mRepository;
    private LiveData<List<MyNote>> mAllNotes;

    public MyNoteViewModel(@NonNull Application application) {
        super(application);
        mRepository = new MyNoteRepository(application);
        mAllNotes = mRepository.getAllNotes();
    }

    public LiveData<List<MyNote>> getAllNotes() {
        return mAllNotes;
    }

    public void insert(MyNote myNote) {
        mRepository.insert(myNote);
    }

    public void update(MyNote myNote) {
        mRepository.update(myNote);
    }

    public void delete(MyNote myNote) {
        mRepository.delete(myNote);
    }
}
