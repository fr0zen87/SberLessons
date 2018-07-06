package com.example.lesson10.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.example.lesson10.entities.MyNote;

import java.util.List;

@Dao
public interface MyNoteDao {

    @Insert
    long insert(MyNote myNote);

    @Update
    long update(MyNote myNote);

    @Delete
    long delete(MyNote myNote);

    @Query("SELECT * FROM notes")
    LiveData<List<MyNote>> getNotes();

    @Query("SELECT * FROM notes")
    Cursor selectAll();
}
