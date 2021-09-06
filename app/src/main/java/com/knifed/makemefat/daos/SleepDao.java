package com.knifed.makemefat.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.knifed.makemefat.entities.Sleep;
import com.knifed.makemefat.entities.Water;

import java.util.List;

@Dao
public interface SleepDao {
    @Query("SELECT * FROM sleep")
    List<Sleep> getAll();

    @Insert
    void insertSleep(Sleep sleep);
}
