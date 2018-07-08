package com.example.lesson10.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.lesson10.entities.MyNote;

import java.util.List;

@Dao
public interface MyNoteDao {

    @Insert
    void insert(MyNote myNote);

    @Update
    void update(MyNote myNote);

    @Delete
    void delete(MyNote myNote);

    @Query("SELECT * FROM notes")
    LiveData<List<MyNote>> getNotes();
}
