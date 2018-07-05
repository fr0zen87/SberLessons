package com.example.lesson10.db;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.example.lesson10.entities.MyNote;

public interface ContentProviderDao {

    @Query("SELECT * FROM notes")
    Cursor selectAll();

    @Query("SELECT * FROM notes WHERE id = :id")
    Cursor selectById(long id);

    @Query("DELETE FROM notes WHERE id = :id")
    int deleteById(long id);

    @Insert
    long insert(MyNote myNote);

    @Update
    int update(MyNote myNote);
}
